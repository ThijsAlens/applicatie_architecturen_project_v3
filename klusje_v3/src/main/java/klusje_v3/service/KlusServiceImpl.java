package klusje_v3.service;

import klusje_v3.model.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klusje_v3.model.Klus;
import klusje_v3.repository.KlusRepository;

@Service
public class KlusServiceImpl  implements KlusService{
	
	@Autowired
	private KlusRepository repo;

	@Override
	public void addKlus(Klus klus) {
		repo.save(klus);
	}

	public ArrayList<Klus> getAllKlussen() {
		return (ArrayList<Klus>) repo.findAll();
	}

	public ArrayList<Klus> getAllKlussenByKlantUsername(String username) {
		ArrayList<Klus> klussen = getAllKlussen();
		ArrayList<Klus> gefilterdeKlussen = new ArrayList<Klus>();
		for (Klus klus : klussen) {
			System.out.println("username klant gevraagt= " + username);
			System.out.println("username klant in klus= " + klus.getKlant().getUsername());
			if (klus.getKlant().getUsername().equals(username))
				gefilterdeKlussen.add(klus);
		}
		return gefilterdeKlussen;
	}

	public Float getRatingByKlusjesmanUsername(String klusjesmanUsername) {
		ArrayList<Klus> klussen = getAllKlussen();
		float sum = 0;
		int count = 0;
		for (Klus klus : klussen) {
			if (klus.getStatus() != StatusEnum.BEOORDEELD)
				continue;
			if (klus.getKlusjesman().getUsername().equals(klusjesmanUsername)) {
				sum += klus.getRating();
				count++;
			}
		}
		return (count == 0 ? -1 : (sum/count));
	}

	public Klus getKlusById(int klusId) {
		return repo.findById(klusId).get();
	}

	public void updateKlus(Klus k) {
		repo.save(k);
	}

	public ArrayList<Klus> getAllAplicableKlussen() {
		// get all klussen where klusjesmannen can still bied on
		ArrayList<Klus> klussen = getAllKlussen();
		ArrayList<Klus> gefilterdeKlussen = new ArrayList<Klus>();
		for (Klus klus : klussen) {
			if (klus.getStatus() == StatusEnum.BESCHIKBAAR | klus.getStatus() == StatusEnum.GEBODEN) {
				gefilterdeKlussen.add(klus);
			}
		}
		return gefilterdeKlussen;
	}

	public ArrayList<Klus> getAllToegewezenKlussenByKlusjesmanUsername(String username) {
		ArrayList<Klus> klussen = getAllKlussen();
		ArrayList<Klus> gefilterdeKlussen = new ArrayList<Klus>();
		for (Klus klus : klussen) {
			if (klus.getStatus() != StatusEnum.TOEGEWEZEN) {
				continue;
			}
			if (klus.getKlusjesman().getUsername().equals(username)) {
				gefilterdeKlussen.add(klus);
			}
		}
		return gefilterdeKlussen;
	}

	public void deleteKlusById(int klusId) {
		repo.deleteById(klusId);
	}

}
