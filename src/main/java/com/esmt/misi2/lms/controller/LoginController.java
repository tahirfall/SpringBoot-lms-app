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
			flash.addFlashAttribute("info", "Connected.");
			return "redirect:/home";
		}

		if (error != null) {
			model.addAttribute("error", "Login error: Incorrect username or password, "
					+ "Please try again!!");
		}

		if (logout != null) {
			model.addAttribute("success", "You have successfully logged out!");
		}

		return "login";
	}

}
