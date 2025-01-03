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
    
}

