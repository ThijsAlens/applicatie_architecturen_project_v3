package klusje_v3.service;

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

}
