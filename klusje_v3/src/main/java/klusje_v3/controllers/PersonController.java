package klusje_v3.controllers;

import klusje_v3.model.*;
import klusje_v3.service.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.ServletException;
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
	private AuthoritiesServiceImpl authService; 
	
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
		return res;
	}
	
	@PostMapping("/register_post")
	public String register_post(Model mod, HttpSession ses, HttpServletRequest req,  @ModelAttribute("new_person") @Valid Person customer, Errors e) {
		ses.invalidate();
		// Check for validation errors
		if (e.hasErrors()) {
			return "register";  
		} else {
			String unencryptedPassword = customer.getPassword();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encryptedPassword = passwordEncoder.encode(customer.getPassword());
			customer.setPassword(encryptedPassword);  
			
			personService.addPerson(customer);
			System.out.println();
			System.out.println();
			System.out.println(customer);
			System.out.println(customer.getFunctie());
			authService.addRole(customer, customer.getFunctie());
			
			System.out.println("credentials: " + customer.getUsername() + " ; " + unencryptedPassword);
			
			// log in automaticially
			try {
				req.login(customer.getUsername(), unencryptedPassword);
			} catch (ServletException e1) {
				e1.printStackTrace();
			}
			return "redirect:/";
		}
	}
	
	/*
	 * =====================================================================
	 * klanten-zijde:
	 * 		correct weergeven van de klantenpagina op basis van de db
	 * 		correct opvangen van aanpassingen door de klant
	 * =====================================================================
	 */
	
	@GetMapping("/klant/index")
	public String klant_index(HttpSession ses) {
		// methode dat de html voor de klant-pagina dynamisch genereert
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
				// geen klusjesmannen is al gekozen, laat de klant de klus verwijderen
				// form action: /klant_index_beschikbaar_delete
				// req.parameter: key -> klusId
				html.append("<td>");
				int key = klus.getKlusId();
				html.append("<form action=\"/klant_index_beschikbaar_delete\" method=\"post\">");
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key + "\">");
				html.append("<input type=\"submit\" value=\"Verwijder klus\">");
				html.append("</form>");
				break;
				
			case GEBODEN:
				// geen klusjesmannen is al gekozen, laat de klant de klus verwijderen
				// form action: /klant_index_beschikbaar_delete
				// req.parameter: key -> klusId
				html.append("<td>");
				int key7 = klus.getKlusId();
				html.append("<form action=\"/klant_index_beschikbaar_delete\" method=\"post\">");
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key7 + "\">");
				html.append("<input type=\"submit\" value=\"Verwijder klus\">");
				html.append("</form>");
				
				// een of meerdere klusjesmannen hebben geboden, laat de klant er een kiezen uit een dropdownmenu
				// form action: /klant_index_geboden_update
				// req.parameter: geselecteerde_klusjesman -> klusjesmanUsername
				// req.parameter: key -> klusId
				
				html.append("<form action=\"/klant_index_geboden_update\" method=\"post\">");
				html.append("<label for=\"klusjesmannen\">Selecteer een klusjesman\t</label>");
				html.append("<select name=\"geselecteerde_klusjesman\" id=\"geselecteerde_klusjesman\">");
				ArrayList<String> klusjesmannenUsernames = biedingenService.getGebodenKlusjesmannenUsernameByKlus(klus);
				for (String klusjesmanUsername : klusjesmannenUsernames) {
					String key1 = klusjesmanUsername;
					String rating = klusService.getRatingByKlusjesmanUsername(klusjesmanUsername) == -1 ? "nog geen" : klusService.getRatingByKlusjesmanUsername(klusjesmanUsername).toString();
					String value = rating == "nog geen" ? klusjesmanUsername + " (nog geen rating)" : klusjesmanUsername + " (rating van " + rating + " op 10)";
					html.append("<option value=\"" + key1 + "\">" + value + "</option>");
				}
				html.append("</select>");
				int key2 = klus.getKlusId();
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key2 + "\">");
				html.append("<input type=\"submit\" value=\"Wijs toe\">");
				html.append("</form></td>");
				break;
				
			case TOEGEWEZEN:
				// de klant heeft een klusjesman toegewezen, deze voert de klus nu uit
				html.append("<td>");
				html.append("Toegewezen aan " + klus.getKlusjesman().getUsername());
				html.append("</td>");
				break;
				
			case UITGEVOERD:
				// klusjesman heeft de klus uitgevoerd, laat de klant de klusjesman beoordelen
				// form action: /klant_index_uitgevoerd_update
				// req.parameter: rating -> rating
				// req.parameter: key -> klusId
				html.append("<td>Klus is uitgevoerd:");
				int key3 = klus.getKlusId();
				html.append("<form action=\"/klant_index_uitgevoerd_update\" method=\"post\">");
				html.append("<input type=\"text\" id=\"rating\" name=\"rating\" placeholder=\"rating\">");
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key3 + "\">");
				html.append("<input type=\"submit\" value=\"Beoordeel klus\">");
				html.append("</form></td>");
				break;
				
			case BEOORDEELD:
				// klus is klaar en moet niet meer getoond worden
				break;
			}
			html.append("</tr>");			
		}
		html.append("</table>");
		
		// check of er klusjes in de tabel staan, indien niet, geef dan een mooiere boodschap mee
		String regex = "^<table><tr><td>Klus ID</td><td>Naam van de klus</td><td>Prijs</td><td>Beschrijving</td><td>status</td><td>Extra info</td></tr>(<tr></tr>)*</table>$";
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(html.toString()).matches()) {
			// nothing is found for the klusjesman to do
			html = new StringBuilder();
			html.append("<p>Geen info gevonden voor u.</p>");
		}
		
		ses.setAttribute("klant_index_HTML", html.toString());
		return "/klant/index";
	}

	@PostMapping("/klant_index_beschikbaar_delete")
	public String klant_index_beschikbaar_delete(HttpServletRequest req) {
		// methode die de actie "/klant_index_beschikbaar_delete" verwerkt (opgeroepen uit PersonController.klant_index)
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		biedingenService.removeBiedingenByKlusId(klusId);
		klusService.deleteKlusById(klusId);
		return "redirect:/klant/index";
	}
	
	@PostMapping ("/klant_index_geboden_update")
	public String klant_index_geboden_update(HttpServletRequest req, HttpSession ses) {
		// methode die de actie "/klant_index_geboden_update" verwerkt (opgeroepen uit PersonController.klant_index)
		String selectedKlusjesmanUsername = req.getParameter("geselecteerde_klusjesman").toString();
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		Klus k = klusService.getKlusById(klusId);
		k.setKlusjesman(personService.getPersonByUsername(selectedKlusjesmanUsername));
		k.setStatus(StatusEnum.TOEGEWEZEN);
		klusService.updateKlus(k);
		biedingenService.removeBiedingenByKlusId(k.getKlusId());
		return "redirect:/klant/index";
	}
	
	@PostMapping ("/klant_index_uitgevoerd_update")
	public String klant_index_uitgevoerd_update(HttpServletRequest req) {
		// methode die de actie "/klant_index_uitgevoerd_update" verwerkt (opgeroepen uit PersonController.klant_index)
		int rating = Integer.parseInt(req.getParameter("rating").toString());
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		Klus k = klusService.getKlusById(klusId);
		k.setRating(rating);
		k.setStatus(StatusEnum.BEOORDEELD);
		klusService.updateKlus(k);
		return "redirect:/klant/index";
	}
	
	/*
	 * =====================================================================
	 * klusjesman-zijde:
	 * 		correct weergeven van de klusjesmanpagina op basis van de db
	 * 		correct opvangen van aanpassingen door de klusjesman
	 * =====================================================================
	 */
	
	@GetMapping("/klusjesman/index")
	public String klusjesman_index(HttpSession ses) {
		// methode dat de html voor de klant-pagina dynamisch genereert
		StringBuilder html = new StringBuilder();
		
		ArrayList<Klus> possibleKlussen = klusService.getAllAplicableKlussen();
		ArrayList<Klus> toegewezenKlussen = klusService.getAllToegewezenKlussenByKlusjesmanUsername(getUserInfo().get(0));
		ArrayList<Klus> klussen = new ArrayList<Klus>(possibleKlussen);
		klussen.addAll(toegewezenKlussen);
		
		html.append("<table>");
		html.append("<tr><td>Klus ID</td><td>Naam van de klus</td><td>Prijs</td><td>Beschrijving</td><td>status</td><td>Extra info</td></tr>");
		
		for (Klus klus : klussen) {
			html.append("<tr>");
			if (klus.getStatus() != StatusEnum.BEOORDEELD)
				// standaard info voor een klus
				html.append("<td>" + klus.getKlusId() + "</td><td>" + klus.getName() + "</td><td>" + klus.getPrijs() + "</td><td>" + klus.getBeschrijving() + "</td><td>" + klus.getStatus() + "</td>");
			
			switch (klus.getStatus()) {
			case BESCHIKBAAR:
			case GEBODEN:
				// klant heeft een klus gemaakt, laat de klusjesman hierop bieden. Als deze al geboden heeft, kan de klusjesman zijn bieding intrekken
				// form action: /klusjesman_index_geboden_update
				// req.parameter: key -> klusId
				if (biedingenService.getGebodenKlusjesmannenUsernameByKlus(klus).contains(getUserInfo().get(0))) {
					// klusjesman has already applied for the klus, give the possibility to remove his bieding
					html.append("<td>");
					int key1 = klus.getKlusId();
					html.append("<form action=\"/klusjesman_index_geboden_update\" method=\"post\">");
					html.append("<input type=\"submit\" value=\"trek uw bod in\">");
					html.append("<input type=\"hidden\" name=\"key\" value=\"" + key1 + "\">");
					html.append("</form></td>");
					break;
				} else {
					html.append("<td>");
					int key1 = klus.getKlusId();
					html.append("<form action=\"/klusjesman_index_geboden_update\" method=\"post\">");
					html.append("<input type=\"submit\" value=\"Appliceer op deze klus\">");
					html.append("<input type=\"hidden\" name=\"key\" value=\"" + key1 + "\">");
					html.append("</form></td>");
					break;
				}
				
			case TOEGEWEZEN:
				// klant heeft het klusje toegewezen aan de klusjesman, laat de klusjesman uitvoeren
				// form action: /klusjesman_index_uitgevoerd_update
				// req.parameter: key -> klusId
				html.append("<td>");
				int key2 = klus.getKlusId();
				html.append("<form action=\"/klusjesman_index_uitgevoerd_update\" method=\"post\">");
				html.append("<input type=\"submit\" value=\"Ik heb deze klus uitgevoerd\">");
				html.append("<input type=\"hidden\" name=\"key\" value=\"" + key2 + "\">");
				html.append("</form></td>");
				break;
			
			case UITGEVOERD:
			case BEOORDEELD:
				// klusje is klaar
				break;
			}
			html.append("</tr>");			
		}
		html.append("</table>");
		
		// check of er klusjes in de tabel staan, indien niet, geef dan een mooiere boodschap mee
		String regex = "^<table><tr><td>Klus ID</td><td>Naam van de klus</td><td>Prijs</td><td>Beschrijving</td><td>status</td><td>Extra info</td></tr>(<tr></tr>)*</table>$";
        Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(html.toString()).matches()) {
			// nothing is found for the klusjesman to do
			html = new StringBuilder();
			html.append("<p>Geen klusjes gevonden voor u.</p>");
		}
		ses.setAttribute("klusjesman_index_HTML", html.toString());
		return "/klusjesman/index";
	}
	
	@PostMapping("/klusjesman_index_geboden_update")
	public String klusjesman_index_geboden_update(HttpServletRequest req) {
		// methode die de actie "/klusjesman_index_geboden_update" verwerkt (opgeroepen uit PersonController.klant_index)
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		Person klusjesman = personService.getPersonByUsername(getUserInfo().get(0));
		Klus k = klusService.getKlusById(klusId);
		
		if (biedingenService.getGebodenKlusjesmannenUsernameByKlus(k).contains(klusjesman.getUsername())) {
			// klusjesman heeft reeds geboden, verwijder hem van de bieding
			biedingenService.removeBiedingenByKlusIdAndKlusjesmanUsername(k.getKlusId(), klusjesman.getUsername());
			if (biedingenService.getGebodenKlusjesmannenUsernameByKlus(k).size() == 0) {
				// als de verwijderde klusjesman de laatste was die geappliceert had, verander de status terug naar beschikbaar
				k.setStatus(StatusEnum.BESCHIKBAAR);
				klusService.updateKlus(k);
			}
		} else {
		k.setStatus(StatusEnum.GEBODEN);
		klusService.updateKlus(k);
		
		Biedingen bieding = new Biedingen(klusjesman, k);
		biedingenService.addBieding(bieding);
		}
		return "redirect:/klusjesman/index";
	}
	
	@PostMapping("klusjesman_index_uitgevoerd_update")
	public String klusjesman_index_uitgevoerd_update(HttpServletRequest req) {
		// methode die de actie "/klusjesman_index_uitgevoerd_update" verwerkt (opgeroepen uit PersonController.klant_index)
		int klusId = Integer.parseInt(req.getParameter("key").toString());
		Klus k = klusService.getKlusById(klusId);
		
		k.setStatus(StatusEnum.UITGEVOERD);
		
		klusService.updateKlus(k);
		return "redirect:/klusjesman/index";
	}
}
