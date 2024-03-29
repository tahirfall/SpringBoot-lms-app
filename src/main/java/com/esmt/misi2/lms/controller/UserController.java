package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.ILoanService;
import com.esmt.misi2.lms.model.service.IUserService;
import com.esmt.misi2.lms.util.paginator.PageRender;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	ILoanService loanService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/list-users")
	public String listUsers(@RequestParam(name = "page", defaultValue = "0") int page, 
			Model model) {
		
		Pageable pageable = PageRequest.of(page, 5);
		
		Page<UserModel> users = userService.findAll(pageable);
		
		PageRender<UserModel> pageRender = new PageRender<>("/users/list-users", users);
		
		model.addAttribute("title", "liste des utilisateurs");
		model.addAttribute("users", users);
		model.addAttribute("page", pageRender);
		
		return "users/list-users";
	}
	
	@GetMapping("/create-user")
	public String createUser(Model model) {
		
		UserModel user = new UserModel();
		
		model.addAttribute("title", "Ajout d'un nouvel utilisateur");
		model.addAttribute("user", user);
		
		return "users/new-user";
	}
	
	@PostMapping("/create-user")
	public String saveUser(@Valid UserModel user, BindingResult result, Model model,
						   SessionStatus status, RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Ajout d'un nouvel utilisateur");
			model.addAttribute("user", user);
			return "users/new-user"; //html
		}
		// Encrypt the password before saving it
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);

		// Set default role (ROLE_USER)
		//user.setRole(UserRole.ROLE_USER);
		user.setRole(user.getRole());
		user.setEnabled(true);

		userService.save(user);
		status.setComplete();
		flash.addFlashAttribute("success", "L'utilisateur est enregistré avec succès");
		
		return "redirect:/users/list-users";
	}


	@GetMapping("/edit-user/{id}")
	public String editUser(@PathVariable(value = "id") Long id, Model model,
			RedirectAttributes flash) {
		
		UserModel user = null;
		
		if(id > 0 ) {
			user = userService.findOne(id);
			
			if(user == null) {
				flash.addFlashAttribute("error", "L'utilisateur n'existe pas!!");
				return "redirect:/users/list-users";
			}
		}else {
			return "redirect:/users/list-users";
		}
		
		model.addAttribute("title", "Modification de l'utilisateur...");
		model.addAttribute("user", user);
		
		return "users/new-user";
	}


	@GetMapping("/detail/{id}")
	public String detailUser(@PathVariable(value = "id") Long id, Model model) {
		
		UserModel user = userService.fetchByIdWithLoans(id);
		
		if(user == null) {
			return "redirect:/users/list-users";
		}
		
		model.addAttribute("user", user);
		//model.addAttribute("loan", loan);
		model.addAttribute("title", "Détail de l'utilisateur: " + user.getName());
		
		return "users/detail";
	}
	
	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			userService.delete(id);
			flash.addFlashAttribute("success", "Utilisateur supprimé avec succès");
		}
		
		return "redirect:/users/list-users";
	}
	
}


