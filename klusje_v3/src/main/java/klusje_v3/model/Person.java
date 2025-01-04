package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="person")
public class Person {
	
	// klasse die overeenkomt met de person tabel
	
	@Id
	@NotBlank
    @Column(name = "USERNAME")
    private String username;

    @NotBlank
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "VOORNAAM")
    private String voornaam;

    @Column(name = "ACHTERNAAM")
    private String achternaam;
    
    @Column(name = "FUNCTIE")
    private String functie;
    
    @Column(name = "ENABLED")
    private boolean enabled;

	public Person() {
		super();
	}

	public Person(@NotBlank String username, @NotBlank String password, String voornaam, String achternaam,
			String functie, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.functie = functie;
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getFunctie() {
		return functie;
	}

	public void setFunctie(String functie) {
		this.functie = functie;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
