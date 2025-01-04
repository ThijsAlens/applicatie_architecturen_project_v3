package klusje_v3.controllers;

import klusje_v3.service.*;


import klusje_v3.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	
	
	//curl -X DELETE http://localhost:8080/restDelete/6
	@DeleteMapping("/restDelete/{klusId}")
	public void restDeleteKlus(@PathVariable("klusId") int klusId) {
		Klus k = klusService.getKlusById(klusId);
		if (k.getStatus() == StatusEnum.BESCHIKBAAR || k.getStatus() == StatusEnum.GEBODEN) {
			klusService.deleteKlusById(klusId);
		}
		else
			System.out.println("MAG NI: FOUTE STATUS");
	}
	
	
	
	// curl -X PUT http://localhost:8080/restWijstoe/2/stijn
	@PutMapping("/restWijstoe/{klusId}/{username}")
	public void restWijsToe(@PathVariable int klusId,@PathVariable String username ) {
		Klus k = klusService.getKlusById(klusId);
		Person klusser = personService.getPersonByUsername(username);
		if (k.getStatus() == StatusEnum.GEBODEN) {
	        k.setKlusjesman(klusser);
	        k.setStatus(StatusEnum.TOEGEWEZEN);
	        klusService.updateKlus(k);
		}
		else
			System.out.println("MAG NI: FOUTE STATUS");
		
	}

	
	
	
	
	/*
	 
curl -X POST "http://localhost:8080/restNieuweKlus" -H "Content-Type: application/json" -d '{
    "name": "Klus voor Thijs",
    "klant": {
        "username": "thijs"
    },
    "prijs": 100,
    "beschrijving": "nieuwe computer kopen",
    "status": "BESCHIKBAAR",
    "klusjesman": {
        "username": "stijn"
    }
}'

 
	 */
	@PostMapping("/restNieuweKlus")
	public void restNieuweKlus(@RequestBody Klus k) {
		    System.out.println("Received Klus object:");
		    System.out.println("KLUS_ID: " + k.getKlusId());
		    System.out.println("Name: " + k.getName());
		    System.out.println("KLANT_USERNAME: " + k.getKlant().getUsername());
		    
		    
		    klusService.addKlus(k);
	}
	

	//curl -X PUT http://localhost:8080/restBeoordeel/2/10
	@PutMapping("/restBeoordeel/{klusId}/{rating}")
	public void restBeoordeel(@PathVariable int klusId,@PathVariable int rating) {
		Klus k = klusService.getKlusById(klusId);
		if(k.getStatus()==StatusEnum.UITGEVOERD) {
			k.setRating(rating);
			k.setStatus(StatusEnum.BEOORDEELD);
			klusService.updateKlus(k);
		}
		else
			System.out.println("MAG NI: FOUTE STATUS");
	}
	
	
	
}
