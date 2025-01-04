package klusje_v3.controllers;

import klusje_v3.model.*;
import klusje_v3.service.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Autowired
	private PersonServiceImpl personService;
	
	@Autowired
	private KlusServiceImpl klusService;
	
	@Autowired
	private BiedingenServiceImpl biedingenService;
	
	/*
	 * =====================================================================
	 * algemene functies
	 * 		functie voor het gemakkelijk lezen van gegevens
	 * 		opvangen van nieuwe registraties
	 * =====================================================================
	 */
	
	public ArrayList<String> getUserInfo() {
		// helper function that gets the info of the current person logged in
		// [0] = username ; [1] = functie
		ArrayList<String> res = new ArrayList<String>();
		res.add(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			res.add(personService.getPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFunctie());
		} catch (Exception e) {
			System.out.println("error bij het ophalen van de rol van de ingelogde gebruiker");
			res.add("NOT_LOGGED_IN");
		}
		System.out.println("username = " + res.get(0) + "\tfunctie = " + res.get(1));
		return res;
	}
	
	@PostMapping("/register_post")
	public String register_post(Model mod, HttpSession ses, HttpServletRequest req, @ModelAttribute("new_person") @Valid Person costumer, Errors e) {
		if (e.hasErrors())
			return "register";
		else
			return "redirect:/";
	}
	
	@GetMapping("/klant/index")
	public String klant_index(HttpSession ses) {
		StringBuilder html = new StringBuilder();
		
		ArrayList<Klus> klussen = klusService.getAllKlussenByKlantUsername(getUserInfo().get(0));
		
		if (klussen.size() == 0) {
			html.append("<p>U heeft nog geen klussen gemaakt.</p>");
			return "/klant/index";
		}
		
		html.append("<table>");
		html.append("<tr><td>Klus ID</td><td>Naam van de klus</td><td>Prijs</td><td>Beschrijving</td><td>status</td><td>Extra info</td></tr>");
		for (Klus klus : klussen) {
			html.append("<tr>");
			if (klus.getStatus() != StatusEnum.BEOORDEELD)
				// standard info for a klus
				html.append("<td>" + klus.getKlusId() + "</td><td>" + klus.getName() + "</td><td>" + klus.getPrijs() + "</td><td>" + klus.getBeschrijving() + "</td><td>" + klus.getStatus() + "</td>");
			
			switch (klus.getStatus()) {
			case BESCHIKBAAR:
				// no klusjesmannen yet
				html.append("<td>Nog geen klusjesmannen gevonden</td>");
				break;
				
			case GEBODEN:
				// klusjesmannen have geboden, let the klant assign one
				// form action: /klant_index_geboden_update
				// req.parameter: geselecteerde_klusjesman -> klusjesmanUsername
				// req.parameter: key -> klusId
				html.append("<td>");
				html.append("<form> action=\"/klant_index_geboden_update\" method=\"post\"");
				html.append("<label for=\"klusjesmannen\">Selecteer een klusjesman</label>");
				html.append("<select name=\"geselecteerde_klusjesman\" id=\"geselecteerde_klusjesman\">");
				ArrayList<String> klusjesmannenUsernames = biedingenService.getGebodenKlusjesmannenUsernameByKlus(klus);
				for (String klusjesmanUsername : klusjesmannenUsernames) {
					String key = klusjesmanUsername;
					String rating = klusService.getRatingByKlusjesmanUsername(klusjesmanUsername) == -1 ? "nog geen" : klusService.getRatingByKlusjesmanUsername(klusjesmanUsername).toString();
					String value = klusjesmanUsername + " (rating = " + rating;
					html.append("<option value=\"" + key + "\">" + value + "</option>");
				}
				html.append("</select>");
				int key = klus.getKlusId();
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key + "\">");
				html.append("</form></td>");
				break;
				
			case TOEGEWEZEN:
				html.append("<td>");
				html.append("Toegewezen aan " + klus.getKlusjesmanUsername());
				html.append("</td>");
				break;
				
			case UITGEVOERD:
				// klusjesman has uitgevoerd, let the klant rate
				// form action: /klant_index_uitgevoerd_update
				// req.parameter: rating -> rating
				// req.parameter: key -> klusId
				html.append("<td>Klus is uitgevoerd:");
				int key2 = klus.getKlusId();
				html.append("<form> action=\"/klant_index_uitgevoerd_update\" method=\"post\"");
				html.append("<input type=\"text\" id=\"rating\" name=\"rating\" placeholder=\"rating\">");
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key2 + "\">");
				html.append("</form></td>");
				break;
				
			case BEOORDEELD:
				// klus is done
				break;
			}
			html.append("</tr>");			
		}
		html.append("</table>");
		ses.setAttribute("klant_index_HTML", html.toString());
		return "/klant/index";
	}
	
	
	
	@PostMapping ("/nieuw_klusje")
	public String nieuw_klusje(HttpServletRequest req, HttpSession ses) {
		Klus k = new Klus(req.getParameter("name").toString(), getUserInfo().get(0), Integer.parseInt(req.getParameter("prijs").toString()), req.getParameter("beschrijving").toString());
		klusService.addKlus(k);
		ses.setAttribute("nieuw_klusje_status", "nieuw klusje aangemaakt");
		return "redirect:/klant/index";
	}
	
	@PostMapping ("/klant_index_geboden_update")
	public String klant_index_geboden_update(HttpServletRequest req, HttpSession ses) {
		String selectedKlusjesmanUsername = req.getParameter("geselecteerde_klusjesman").toString();
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		Klus k = klusService.getKlusById(klusId);
		k.setKlusjesmanUsername(selectedKlusjesmanUsername);
		klusService.updateKlus(k);
		return "redirect:/klant/index";
	}
	
	@PostMapping ("/klant_index_uitgevoerd_update")
	public String klant_index_uitgevoerd_update() {
		return "";
	}
}
