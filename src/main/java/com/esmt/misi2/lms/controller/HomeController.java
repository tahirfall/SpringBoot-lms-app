package com.esmt.misi2.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping({"", "/", "index"})
	public String routePrincipal() {
		return "redirect:/books/list-books";
	}
	
}
