package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "klus")
public class Klus {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KLUS_ID")
    private Integer klusId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "KLANT_USERNAME")
    private String klantUsername;

    @NotNull
    @Min(0)
    @Column(name = "PRIJS")
    private Integer prijs;

    @Column(name = "BESCHRIJVING")
    private String beschrijving;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusEnum status;

    @Column(name = "KLUSJESMAN_USERNAME")
    private String klusjesmanUsername;

    @Column(name = "RATING")
    private Integer rating;
    
    public Klus() {
    	
    }
    
    public Klus(String name, String klantUsername, int prijs, String beschrijving) {
		this.name = name;
		this.klantUsername = klantUsername;
		this.prijs = prijs;
		this.beschrijving = beschrijving;
		this.status = StatusEnum.BESCHIKBAAR;
		this.klusjesmanUsername = null;
		this.rating = null;
	}

	public Integer getKlusId() {
		return klusId;
	}

	public void setKlusId(Integer klusId) {
		this.klusId = klusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKlantUsername() {
		return klantUsername;
	}

	public void setKlantUsername(String klantUsername) {
		this.klantUsername = klantUsername;
	}

	public Integer getPrijs() {
		return prijs;
	}

	public void setPrijs(Integer prijs) {
		this.prijs = prijs;
	}

	public String getBeschrijving() {
		return beschrijving;
	}

	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getKlusjesmanUsername() {
		return klusjesmanUsername;
	}

	public void setKlusjesmanUsername(String klusjesmanUsername) {
		this.klusjesmanUsername = klusjesmanUsername;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
    
    
    
}

