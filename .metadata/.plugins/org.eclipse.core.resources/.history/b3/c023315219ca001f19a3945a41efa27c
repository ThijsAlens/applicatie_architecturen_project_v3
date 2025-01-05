package klusje_v3.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import klusje_v3.model.Klus;
import klusje_v3.model.Person;
import klusje_v3.repository.*;

public class BiedingenServiceImpl implements BiedingenService {
	
	@Autowired
	BiedingenRepository repo;
	
	public ArrayList<String> getGebodenKlusjesmannenUsernameByKlus(Klus k) {
		ArrayList<Integer> id = new ArrayList<Integer>();
		id.add(k.getKlusId());
		return (ArrayList<String>) repo.findAllById(id);
		
	}
}
