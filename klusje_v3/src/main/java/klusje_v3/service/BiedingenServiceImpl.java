package klusje_v3.service;

import klusje_v3.model.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klusje_v3.repository.*;

@Service
public class BiedingenServiceImpl implements BiedingenService {
	
	@Autowired
	private BiedingenRepository repo;
	
	public ArrayList<Biedingen> getAllBiedingen() {
		return (ArrayList<Biedingen>) repo.findAll();
	}
	
	public ArrayList<String> getGebodenKlusjesmannenUsernameByKlus(Klus k) {
		ArrayList<Biedingen> allBiedingen = getAllBiedingen();
		ArrayList<String> gebodenKlusjesmannen = new ArrayList<String>();
		for (Biedingen bieding : allBiedingen) {
			if (bieding.getKlusId() == k.getKlusId()) {
				gebodenKlusjesmannen.add(bieding.getKlusjesmanUsername());
			}
		}
		return gebodenKlusjesmannen;
	}
}
