package klusje_v3.controllers;

import klusje_v3.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index() {
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
	
	@PostMapping("/rest_stuff_post")
	public String check_login(HttpSession ses, HttpServletRequest req) {
		RestTemplate rest = new RestTemplate();
		String person = req.getParameter("person").toString();
		Person res = rest.getForObject("http://localhost:8080/person/"+person, Person.class);
		ses.setAttribute("checked_person", res.getFunctie());
		return "rest_stuff";
	}
}
