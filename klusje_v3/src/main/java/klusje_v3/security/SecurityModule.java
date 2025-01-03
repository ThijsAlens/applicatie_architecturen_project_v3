package klusje_v3.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityModule {
	@Autowired
	DataSource dataSource;
	
	@Bean
	public SecurityFilterChain beveilig(HttpSecurity http) throws Exception {
	http
	.authorizeHttpRequests(authorize -> authorize
		.anyRequest().permitAll()
	)
	.formLogin(form -> form 
		.loginPage("/login").permitAll()
		//.defaultSuccessUrl("/", true)
	)
	.logout(logout -> logout
		.logoutSuccessUrl("/").permitAll()
	)
	.csrf().disable();
	return http.build();
	}
	
	@Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("SELECT username, password, enabled FROM person WHERE username = ?")
            .authoritiesByUsernameQuery("SELECT username, auth FROM authorities WHERE username = ?")
            .passwordEncoder(NoOpPasswordEncoder.getInstance());  // No password encoder for plain text passwords
    }
}
