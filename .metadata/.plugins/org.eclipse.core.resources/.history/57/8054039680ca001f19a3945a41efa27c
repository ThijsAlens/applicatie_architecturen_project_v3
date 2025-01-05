package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

public class Biedingen {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank
    @Column(name = "ID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "KLUSJESMAN_USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
    private Person klusjesman;

    @ManyToOne
    @JoinColumn(name = "KLUS_ID", referencedColumnName = "KLUS_ID", insertable = false, updatable = false)
    private Klus klus;

	public Biedingen() {
		super();
	}

	public Biedingen( Person klusjesman, Klus klus) {
		super();

		this.klusjesman = klusjesman;
		this.klus = klus;
	}

	public Person getKlusjesman() {
		return klusjesman;
	}

	public void setKlusjesman(Person klusjesman) {
		this.klusjesman = klusjesman;
	}

	public Klus getKlus() {
		return klus;
	}

	public void setKlus(Klus klus) {
		this.klus = klus;
	}
    

}
