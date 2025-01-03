package klusje_v3.service;

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
		for (Klus klus : klussen) {
			if (klus.getKlantUsername().equals(username))
				klussen.remove(klus);
		}
		return klussen;
	}

}
