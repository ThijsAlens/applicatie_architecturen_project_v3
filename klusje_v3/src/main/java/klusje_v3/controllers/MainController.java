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
	private static PersonService personService;
	
	/*
	 * =======
	 * helper functions
	 * =======
	 */
	
	public static ArrayList<String> getUserInfo() {
		// [0] = username ; [1] = functie
		ArrayList<String> res = new ArrayList<String>();
		res.add(SecurityContextHolder.getContext().getAuthentication().getName());
		res.add(personService.getPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFunctie());
		return res;
	}
	
	@GetMapping("/")
	public String index() {
		if (getUserInfo().get(1) == "KLANT") {
			return "/klant/index";
		} else if (getUserInfo().get(1) == "KLUSJESMAN") {
			return "/klusjesman/index";
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
}
