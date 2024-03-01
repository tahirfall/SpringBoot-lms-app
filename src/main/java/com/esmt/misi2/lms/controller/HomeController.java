package com.esmt.misi2.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/home")
	public String routePrincipal(Model model) {
		model.addAttribute("welcomeMessage", "Welcome to the Library Management System of ESMT");
		model.addAttribute("libraryImage", "../src/main/resources/static/images/home.png");
		return "home";
	}

}



