package klusje_v3.service;

import klusje_v3.model.*;
import klusje_v3.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository repo;
	
	public Person getPersonByUsername(String username) {
		return repo.findById(username).get();
	}
}