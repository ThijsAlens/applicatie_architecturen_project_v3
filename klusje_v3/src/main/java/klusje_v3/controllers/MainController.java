package klusje_v3.controllers;

import klusje_v3.model.*;

import java.util.ArrayList;

import klusje_v3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//main2
@Controller
public class MainController {
	
	@Autowired
	private PersonServiceImpl personService;
	
	@Autowired
	private KlusServiceImpl klusService;
	
	public String create_header_html(HttpSession ses) {
		// methode die de html dynamisch opmaakt voor de header
		StringBuilder html = new StringBuilder();
		html.append("<form action=\"/naam_aanpassing\" method=\"post\">");
		html.append("<p>");
		
		if (getUserInfo().get(1).equals("NOT_LOGGED_IN")) {
			html.append("U bent op dit moment niet ingelogd.");
			html.append("</p></form>");
			return html.toString();
		}
		else {
			html.append("U bent op dit moment ingelogd als " + getUserInfo().get(1) + " met username " + getUserInfo().get(0) + ". ");
			html.append("Uw naam is " + personService.getPersonByUsername(getUserInfo().get(0)).getVoornaam() + " " + personService.getPersonByUsername(getUserInfo().get(0)).getAchternaam() + ". ");
			html.append("U kan uw naam aanpassen: ");
			html.append("<input type=\"text\" id=\"nieuwe_voornaam\" name=\"nieuwe_voornaam\" placeholder=\"nieuwe voornaam\">");
			html.append("<input type=\"text\" id=\"nieuwe_achternaam\" name=\"nieuwe_achternaam\" placeholder=\"nieuwe achternaam\">");
			html.append("<input type=\"submit\" value=\"Pas een of beide aan\"> ");
			if (getUserInfo().get(1).equals("KLUSJESMAN")) {
				html.append(klusService.getRatingByKlusjesmanUsername(getUserInfo().get(0)) != -1 ? "Uw rating is " + klusService.getRatingByKlusjesmanUsername(getUserInfo().get(0)) + "." : "U hebt nog geen rating.");
			}
			html.append("</p></form>");
			return html.toString();
		}
	}
	
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
	
	@GetMapping("/")
	public String index(HttpSession ses) {
		ses.setAttribute("header_html", create_header_html(ses));
		if (getUserInfo().get(1).equals("KLANT")) {
			return "forward:/klant/index";
		} else if (getUserInfo().get(1).equals("KLUSJESMAN")) {
			return "forward:/klusjesman/index";
		}
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register(Model m) {
		Person p = new Person();
		p.setEnabled(true);
		m.addAttribute("new_person", p);
		return "register";
	}
	
	@PostMapping("/naam_aanpassing")
	public String naam_aanpassing(HttpServletRequest req) {
		// methode die de actie "/naam_aanpassing" verwerkt (opgeroepen uit MainController.create_header_html)
		String nieuweVoornaam = req.getParameter("nieuwe_voornaam").toString();
		String nieuweAchternaam = req.getParameter("nieuwe_achternaam").toString();
		Person p = personService.getPersonByUsername(getUserInfo().get(0));
		
		if (!(req.getParameter("nieuwe_voornaam").equals(""))) {
			p.setVoornaam(nieuweVoornaam);
			personService.updatePerson(p);
		}
		if (!(req.getParameter("nieuwe_achternaam").equals(""))) {
			p.setAchternaam(nieuweAchternaam);
			personService.updatePerson(p);
		}
		return "redirect:/";
	}
	
}
