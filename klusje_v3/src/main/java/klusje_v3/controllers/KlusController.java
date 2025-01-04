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
		// helper function that gets the info of the current person logged in
		// [0] = username ; [1] = functie
		ArrayList<String> res = new ArrayList<String>();
		res.add(SecurityContextHolder.getContext().getAuthentication().getName());
		res.add(personService.getPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFunctie());
		return res;
	}
	
	@PostMapping("/nieuw_klusje")
	public String nieuw_klusje(HttpServletRequest req, HttpSession ses) {
		String name = req.getParameter("name").toString();
		String beschrijving = req.getParameter("beschrijving").toString();
		int prijs = Integer.parseInt(req.getParameter("prijs").toString());
		String klantUsername = getUserInfo().get(0);
		System.out.println("name = " + name + " beschrijving = " + beschrijving + " prijs = " + prijs + " klantUsername = " + klantUsername);
		Klus klus = new Klus(name, personService.getPersonByUsername(klantUsername), prijs, beschrijving);
		
		klusService.addKlus(klus);
		ses.setAttribute("nieuw_klusje_status", "nieuw klusje aangemaakt");
		
		return "redirect:/klant/index";
	}
}
