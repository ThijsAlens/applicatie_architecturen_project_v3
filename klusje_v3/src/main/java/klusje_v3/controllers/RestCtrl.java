package klusje_v3.controllers;

import klusje_v3.service.*;
import klusje_v3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrl {
	
	@Autowired
	private PersonServiceImpl personService;
	
	@GetMapping("/person/{username}")
	public String getPersonById(@PathVariable String username) {
		return personService.getPersonByUsername(username).getUsername();
	}
}
