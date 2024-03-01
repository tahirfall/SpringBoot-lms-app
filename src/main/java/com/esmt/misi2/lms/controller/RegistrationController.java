package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.entity.UserRole;
import com.esmt.misi2.lms.model.entity.UserModel;
import com.esmt.misi2.lms.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "INSCRIPTION");
        model.addAttribute("user", new UserModel());
        return "registration";
    }

    @PostMapping
    public String saveUSer(@Valid UserModel user, BindingResult result, Model model,
                           SessionStatus status, RedirectAttributes flash) {

        if(result.hasErrors()) {
            model.addAttribute("title", "Inscription");
            model.addAttribute("user", user);
            return "registration"; //html
        }
        // Encrypt the password before saving it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        // Set default role (ROLE_USER)
        user.setRole(UserRole.ROLE_USER);
        user.setEnabled(true);

        userService.save(user);
        status.setComplete();
        flash.addFlashAttribute("success", "User registered successfully");

        return "redirect:/register/success";
    }

    @GetMapping("/success")
    public String registrationSuccess(Model model) {
        model.addAttribute("message", "User registered successfully");
        return "registration-success";
    }
}


