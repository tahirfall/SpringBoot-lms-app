package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/home")
    public String adminDashboard(Model model) {
        Long numberOfBooks = adminDashboardService.getNumberOfBooks();
        int numberOfSimpleUsers = adminDashboardService.getNumberOfSimpleUser();
        Long numberOfLoans = adminDashboardService.getNumberOfLoans();
        int numberOfAvailableBook = adminDashboardService.getNumberAvailableBook();
        Long numberOfUsers = adminDashboardService.getNumberOfUsers();
        Long numberOfLoanRequests = adminDashboardService.getNumberOfLoanRequests();

        // Ajoutez les données au modèle pour les rendre disponibles dans la vue
        model.addAttribute("numberOfBooks", numberOfBooks);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfSimpleUsers", numberOfSimpleUsers);
        model.addAttribute("numberOfLoans", numberOfLoans);
        model.addAttribute("numberOfLoanRequests", numberOfLoanRequests);
        model.addAttribute("numberOfAvailableBook", numberOfAvailableBook);

        return "home"; // Le nom de votre vue pour le tableau de bord d'administration
    }
}
