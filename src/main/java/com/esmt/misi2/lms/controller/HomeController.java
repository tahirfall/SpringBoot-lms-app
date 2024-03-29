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
		model.addAttribute("Paragraph5", "Des question, besoin d'aide ?");
		model.addAttribute("Paragraph6", "");

		return "home";
	}

}



