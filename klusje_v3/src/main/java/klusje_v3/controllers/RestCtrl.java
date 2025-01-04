package klusje_v3.controllers;

import klusje_v3.service.*;


import klusje_v3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
public class RestCtrl {
	
	@Autowired
	private PersonServiceImpl personService;
	
	@Autowired 
	private KlusServiceImpl klusService;
	
	@GetMapping("/person/{username}")
	public String getPersonById(@PathVariable String username) {
		return personService.getPersonByUsername(username).getUsername();
	}
	
	@DeleteMapping("/restDelete/{klusId}")
	public void restDeleteKlus(@PathVariable int KlusId) {
		//deleteKlus in klusseervice
		
		
	}
	
	@PutMapping("/restWijstoe/{klusId}/{username}")
	public void restWijsToe(@PathVariable int KlusId,@PathVariable String username ) {
		// wijs toe in klusservice
		
	}
	
	
	@PostMapping("/restNieuweKlus/{name}/{klant_username}/{prijs}/{beschrijving}")
	public void restNieuweKlus(@PathVariable String name,@PathVariable String klant_username,@PathVariable int prijs,@PathVariable String beschrijving) {
		
		Person klant = personService.getPersonByUsername(klant_username);
		Klus k = new Klus(name,klant,prijs,beschrijving);
		klusService.addKlus(k);
		
		
	}
	
	
	@PutMapping("/restBeoordeel/{klusId}/{rating}")
	public void restBeoordeel(@PathVariable int KlusId,@PathVariable int rating) {
		//beoordeel in klusservice
	}
	
	
	
}
