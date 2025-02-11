package klusje_v3.controllers;

import klusje_v3.service.*;


import klusje_v3.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCtrl {
	
	@Autowired
	private PersonServiceImpl personService;
	
	@Autowired 
	private KlusServiceImpl klusService;
	
	@Autowired
	private BiedingenServiceImpl biedingenService;

	//curl -X DELETE http://localhost:8080/rest/Delete/6
	@DeleteMapping("/rest/Delete/{klusId}")
	public void restDeleteKlus(@PathVariable("klusId") int klusId) {
		// methode die een klus verwijdert via de rest-interface (opgeroepen uit terminal via bovenstaande curl)
		Klus k = klusService.getKlusById(klusId);
		if (k.getStatus() == StatusEnum.BESCHIKBAAR | k.getStatus() == StatusEnum.GEBODEN) {
			klusService.deleteKlusById(klusId);
		}
		else
			System.out.println("MAG NI: FOUTE STATUS");
	}
	
	// curl -X PUT http://localhost:8080/rest/Wijstoe/2/stijn
	@PutMapping("/rest/Wijstoe/{klusId}/{username}")
	public void restWijsToe(@PathVariable int klusId,@PathVariable String username ) {
		// methode die een klusjesman toewijst aan een klus via de rest-interface (opgeroepen uit terminal via bovenstaande curl)
		Klus k = klusService.getKlusById(klusId);
		Person klusser = personService.getPersonByUsername(username);
		if (k.getStatus() == StatusEnum.GEBODEN & biedingenService.getGebodenKlusjesmannenUsernameByKlus(k).contains(klusser.getUsername())) {
	        k.setKlusjesman(klusser);
	        k.setStatus(StatusEnum.TOEGEWEZEN);
	        klusService.updateKlus(k);
		}
		else
			System.out.println("MAG NIET: FOUTE STATUS OF KLUSJESMAN DAT NIET GEAPPLICEERD HEEFT");
		
	}

	/*
	 
curl -X POST "http://localhost:8080/rest/NieuweKlus" -H "Content-Type: application/json" -d '{
    "name": "Klus voor Thijs",
    "klant": {
        "username": "thijs"
    },
    "prijs": 100,
    "beschrijving": "nieuwe computer kopen",
    "status": "BESCHIKBAAR"
}'

	 */
	@PostMapping("/rest/NieuweKlus")
	public void restNieuweKlus(@RequestBody Klus k) {
		// methode die een nieuwe klus maakt via de rest-interface (opgeroepen uit terminal via bovenstaande curl)
		klusService.addKlus(k);
	}
	

	//curl -X PUT http://localhost:8080/rest/Beoordeel/2/10
	@PutMapping("/rest/Beoordeel/{klusId}/{rating}")
	public void restBeoordeel(@PathVariable int klusId,@PathVariable int rating) {
		// methode die een rating geeft aan een uitgevoerde klus via de rest-interface (opgeroepen uit terminal via bovenstaande curl)
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
