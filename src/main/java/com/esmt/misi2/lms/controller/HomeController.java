package com.esmt.misi2.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/home")
	public String routePrincipal(Model model) {
		model.addAttribute("title", "Home");
		model.addAttribute("welcomeMessage", "Bienvenue sur notre bibliothèque");
		model.addAttribute("Paragraph1", "La bibliothèque vous offre plusieurs services comme l'accès au catalogue, la demande et suivie de prets, la consultation des emprunts, la réservation de livres,...");
		model.addAttribute("Paragraph2", "Un livre non rendu après la date limite fixé aentraine une pénalité pour le lecteur qui peut aller jusqu'à la suspension définitve de son compte. Veillez bien donc à rendre les livres à temps pour la satisfaction de tout le monde.");
		model.addAttribute("Paragraph3", "Compte Lecteur");
		model.addAttribute("Paragraph4", "Catalogue");
		model.addAttribute("Paragraph5", "Des question, besoin d'aide ?");
		model.addAttribute("Paragraph6", "Cliquer ici pour demander demander à un bibliothécaire de t'aider");

		return "home";
	}

}



