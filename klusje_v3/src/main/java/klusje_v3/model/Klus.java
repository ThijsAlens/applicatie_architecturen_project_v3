package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "klus")
public class Klus {
	
	// klasse die overeenkomt met de klus tabel

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KLUS_ID")
    private int klusId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "KLANT_USERNAME", referencedColumnName = "USERNAME")
    private Person klant;

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

    @ManyToOne
    @JoinColumn(name = "KLUSJESMAN_USERNAME", referencedColumnName = "USERNAME")
    private Person klusjesman;

    @Min(0)
    @Max(10)
    @Column(name = "RATING")
    private Integer rating;

	public int getKlusId() {
		return klusId;
	}

	public void setKlusId(int klusId) {
		this.klusId = klusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getKlant() {
		return klant;
	}

	public void setKlant(Person klant) {
		this.klant = klant;
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

	public Person getKlusjesman() {
		return klusjesman;
	}

	public void setKlusjesman(Person klusjesman) {
		this.klusjesman = klusjesman;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Klus() {
		super();
	}

	public Klus(@NotNull String name, @NotNull Person klant, @NotNull @Min(0) Integer prijs, String beschrijving) {
		super();
		this.name = name;
		this.klant = klant;
		this.prijs = prijs;
		this.beschrijving = beschrijving;
		this.status = StatusEnum.BESCHIKBAAR;
		this.klusjesman = null;
		this.rating = null;
	}
    
}

