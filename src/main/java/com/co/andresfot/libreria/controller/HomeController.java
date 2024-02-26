package com.co.andresfot.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping({"", "/", "index"})
	public String rutaPrincipal() {
		return "redirect:/libros/lista-libros";
	}
	
}