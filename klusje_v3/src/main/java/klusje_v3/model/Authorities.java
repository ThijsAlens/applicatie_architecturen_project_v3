package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "authorities")
public class Authorities {
	
	// klasse die overeenkomt met de authorities tabel
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    @Column(name = "ID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    private Person username;
    
    @NotBlank
    @Column(name = "AUTH")
    private String auth;

	public Authorities() {
		super();
	}

	public Authorities(@NotBlank Person username, @NotBlank String auth) {
		this.username = username;
		this.auth = auth;
	}

	public Person getUsername() {
		return username;
	}

	public void setUsername(Person username) {
		this.username = username;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
    
	
	
    
    
}
