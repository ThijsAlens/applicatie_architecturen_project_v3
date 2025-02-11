package klusje_v3.controllers;

import klusje_v3.model.*;
import klusje_v3.service.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class KlusController {
	
	@Autowired
	private KlusServiceImpl klusService;
	
	@Autowired
	private PersonServiceImpl personService;
	
	public ArrayList<String> getUserInfo() {
		// helper-functie die de data over de ingelogde user op een makkelijke manier weergeeft
		// [0] = username ; [1] = functie
		
		ArrayList<String> res = new ArrayList<String>();
		res.add(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			res.add(personService.getPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFunctie());
		} catch (Exception e) {
			System.out.println("error bij het ophalen van de rol van de ingelogde gebruiker (geen gebruiker met gebruikersnaam " + res.get(0) + "gevonden in de db)");
			res.add("NOT_LOGGED_IN");
		}
		return res;
	}
	
	@PostMapping("/nieuw_klusje")
	// methode die de actie "/nieuw_klusje" verwerkt (opgeroepen uit resources.templates.klant/index) 
	public String nieuw_klusje(HttpServletRequest req, HttpSession ses) {
		String name = req.getParameter("name").toString();
		String beschrijving = req.getParameter("beschrijving").toString();
		int prijs = Integer.parseInt(req.getParameter("prijs").toString());
		String klantUsername = getUserInfo().get(0);

		Klus klus = new Klus(name, personService.getPersonByUsername(klantUsername), prijs, beschrijving);
		klusService.addKlus(klus);
		ses.setAttribute("nieuw_klusje_status", "nieuw klusje aangemaakt");
		
		return "redirect:/klant/index";
	}
}
