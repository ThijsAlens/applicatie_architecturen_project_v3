package klusje_v3.service;

import org.springframework.beans.factory.annotation.Autowired;

import klusje_v3.repository.KlusRepository;

public class KlusServiceImpl  implements KlusService{
	
	@Autowired
	private KlusRepository repo;

}
