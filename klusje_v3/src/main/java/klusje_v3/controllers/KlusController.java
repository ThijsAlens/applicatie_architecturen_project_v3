package klusje_v3.controllers;

import klusje_v3.controllers.MainController;
import klusje_v3.model.*;
import klusje_v3.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class KlusController {
	
	@Autowired
	private KlusService klusService;
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/nieuw_klusje")
	public String nieuw_klusje(HttpServletRequest req, HttpSession ses) {
		String name = req.getParameter("name").toString();
		String beschrijving = req.getParameter("beschrijving").toString();
		int prijs = Integer.parseInt(req.getParameter("prijs").toString());
		Person user = personService.getPersonByUsername(MainController.getUserInfo().get(1));
		
		Klus klus = new Klus(name, user, prijs, beschrijving);
		
		klusService.addKlus(klus);
		
		return "redirect:/klant/index";
	}
}
