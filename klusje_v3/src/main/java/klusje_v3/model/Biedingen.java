package klusje_v3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Biedingen {
	
	@Id
	@Column(name = "ID")
	private int id;

	@ManyToOne
    @JoinColumn(name = "KLUS_ID")
    private int klusId;

    @ManyToOne
    @JoinColumn(name = "KLUSJESMAN_USERNAME")
    private String klusjesmanUsername;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKlusId() {
		return klusId;
	}

	public void setKlusId(int klusId) {
		this.klusId = klusId;
	}

	public String getKlusjesmanUsername() {
		return klusjesmanUsername;
	}

	public void setKlusjesmanUsername(String klusjesmanUsername) {
		this.klusjesmanUsername = klusjesmanUsername;
	}

	public Biedingen() {
		super();
	}

	public Biedingen(int id, int klusId, String klusjesmanUsername) {
		super();
		this.id = id;
		this.klusId = klusId;
		this.klusjesmanUsername = klusjesmanUsername;
	}

}
