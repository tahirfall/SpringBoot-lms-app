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

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        int numberOfBooks = adminDashboardService.getNumberOfBooks();
        int numberOfUsers = adminDashboardService.getNumberOfUser();
        int numberOfLoans = adminDashboardService.getNumberOfLoans();
        int numberOfAvailableBook = adminDashboardService.getNumberAvailableBook();

        // Ajoutez les données au modèle pour les rendre disponibles dans la vue
        model.addAttribute("numberOfBooks", numberOfBooks);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfLoans", numberOfLoans);
        model.addAttribute("numberOfAvailableBook", numberOfAvailableBook);

        return "admin/dashboard"; // Le nom de votre vue pour le tableau de bord d'administration
    }
}
