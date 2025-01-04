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
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@Autowired
	private PersonServiceImpl personService;
	
	public ArrayList<String> getUserInfo() {
		// helper function that gets the info of the current person logged in
		// [0] = username ; [1] = functie
		ArrayList<String> res = new ArrayList<String>();
		res.add(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			res.add(personService.getPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFunctie());
		} catch (Exception e) {
			System.out.println("error bij het ophalen van de rol van de ingelogde gebruiker (geen gebruiker met gebruikersnaam " + res.get(0) + "gevonden in de db)");
			res.add("NOT_LOGGED_IN");
		}
		System.out.println("username = " + res.get(0) + "\tfunctie = " + res.get(1));
		return res;
	}
	
	@GetMapping("/")
	public String index() {
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
	
	@GetMapping("/rest_stuff")
	public String rest_stuff() {
		return "rest_stuff";
	}
	
	@GetMapping("/register")
	public String register(Model m) {
		Person p = new Person();
		p.setEnabled(true);
		m.addAttribute("new_person", p);
		return "register";
	}
	
	@PostMapping("/rest_stuff_post")
	public String check_login(HttpSession ses, HttpServletRequest req) {
		RestTemplate rest = new RestTemplate();
		String person = req.getParameter("person").toString();
		Person res = rest.getForObject("http://localhost:8080/person/"+person, Person.class);
		ses.setAttribute("checked_person", res.getFunctie());
		return "redirect:/rest_stuff";
	}
	
	
	@PostMapping("/rest_add_klus")
	public String addDel(Model model, HttpServletRequest req, HttpSession ses) {
	        int id = Integer.parseInt(req.getParameter("id"));
	        String name = req.getParameter("name");
	        String description = req.getParameter("description");
	        int price = Integer.parseInt(req.getParameter("price"));
	        Person klant = personService.getPersonByUsername(getUserInfo().get(0));
	        Klus klus = new Klus(name, klant, price, description);
	        klus.setStatus(StatusEnum.BESCHIKBAAR);
	        RestTemplate rest = new RestTemplate();
	        String url = "http://localhost:8080/klus/restNieuweKlus";
	        rest.postForObject(url, klus, Klus.class);
	        return "redirect:/";
	        
	}
}
