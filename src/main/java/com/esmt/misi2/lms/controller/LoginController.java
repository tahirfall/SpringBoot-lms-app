package com.esmt.misi2.lms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping({"/login", "/"})
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {

		if (principal != null) {
			flash.addFlashAttribute("info", "Connecté.");
			return "redirect:/home";
		}

		if (error != null) {
			model.addAttribute("error", "Erreur de connexion: Nom d'utilisateur ou mot de passe est incorrecte, "
					+ "Please try again!!");
		}

		if (logout != null) {
			model.addAttribute("success", "Déconnexion réussie avec succès!");
		}

		return "login";
	}

}
