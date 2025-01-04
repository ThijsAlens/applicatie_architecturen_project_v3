package klusje_v3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klusje_v3.model.Authorities;
import klusje_v3.model.Person;
import klusje_v3.repository.*;


@Service
public class AuthoritiesServiceImpl implements AuthoritiesService{
	
	
	@Autowired
	private AuthoritiesRepository repo;
	
	@Override
	public void addRole(Person person, String role) {
	    Authorities authority = new Authorities();
	    authority.setUsername(person.getUsername());  
	    authority.setAuth(role); 

	    repo.save(authority);  
	}
	


}
