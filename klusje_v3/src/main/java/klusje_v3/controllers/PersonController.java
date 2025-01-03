package klusje_v3.controllers;

import klusje_v3.model.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PersonController {
	
	@GetMapping("/klant/index")
	public String klant_index() {
		return "/klant/index";
	}
	
	@PostMapping("/register_post")
	public String register_post(Model mod, HttpSession ses, HttpServletRequest req, @ModelAttribute("new_person") @Valid Person costumer, Errors e) {
		if (e.hasErrors())
			return "register";
		else
			return "redirect:/";
	}
	
	
}
