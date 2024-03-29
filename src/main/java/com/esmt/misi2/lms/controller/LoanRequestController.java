package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.dao.ILoanDao;
import com.esmt.misi2.lms.model.entity.*;
import com.esmt.misi2.lms.model.service.IBookService;
import com.esmt.misi2.lms.model.service.ILoanRequestService;
import com.esmt.misi2.lms.model.service.ILoanService;
import com.esmt.misi2.lms.model.service.IUserService;
import com.esmt.misi2.lms.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/loanrequests")
public class LoanRequestController {

    @Autowired
    private ILoanRequestService loanRequestService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoanService loanService;

    @PostMapping("/submit")
    public String submitLoanRequest(@RequestParam("bookId") Long bookId, RedirectAttributes redirectAttributes) {
        // Récupérer l'ID du livre
        Book book = bookService.findOne(bookId);

        if (book == null) {
            // Gérer le cas où le livre n'existe pas
            return "redirect:/books/list-books";
        }

        // Récupérer l'utilisateur actuellement connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserModel user = userService.findByUsername(username);

        if (user == null) {
            // Gérer le cas où l'utilisateur n'existe pas
            return "redirect:/books/list-books";
        }

        // Créer une nouvelle demande d'emprunt
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBook(book);
        loanRequest.setUser(user);
        loanRequest.setStatus(RequestStatus.PENDING);

        // Enregistrer la demande d'emprunt
        loanRequestService.save(loanRequest);

        // Ajouter un message flash pour indiquer que la demande a été soumise avec succès
        redirectAttributes.addFlashAttribute("success", "Loan request submitted successfully!");

        return "redirect:/books/list-books";
    }

    @GetMapping("/list-requests")
    public String listRequests(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 20);
        Page<LoanRequest> requests = loanRequestService.findAllPaginable(pageable);
        PageRender<LoanRequest> pageRender = new PageRender<>("/loan/request/list-requests", requests);
        model.addAttribute("title", "List of Loan Requests");
        model.addAttribute("requests", requests);
        model.addAttribute("page", pageRender);
        return "loanrequests/list-requests";
    }


    @GetMapping("/user-requests")
    public String getUserLoanRequests(Model model, Principal principal) {
        String username = principal.getName();

        UserModel user = userService.findByUsername(username);
        if (user == null) {
            return "redirect:/error"; // Redirection vers une page d'erreur
        }
        List<LoanRequest> userRequests = loanRequestService.findByUser(user);

        model.addAttribute("userRequests", userRequests);
        model.addAttribute("title", "Requests list of: " + user.getName());
        return "loanrequests/user-requests";
    }

    @GetMapping("/view-loan/{id}")
    public String viewLoan(@PathVariable("id") Long requestId, Model model) {
        LoanRequest request = loanRequestService.findOne(requestId);
        if (request != null && request.getStatus() == RequestStatus.ACCEPTED) {
            Loan loan = loanService.findByUserAndBook(request.getUser(), request.getBook()); // Supposez que la demande acceptée a un prêt associé
            model.addAttribute("loan", loan);
            return "loanrequests/view-loan";
        } else {
            // Gérer le cas où la demande n'existe pas ou n'est pas acceptée
            return "redirect:/loanrequests/user-requests";
        }
    }

    @PostMapping("/return-loan/{id}")
    public String returnLoan(@PathVariable("id") Long loanId) {
        // Récupérer le prêt par son identifiant
        Loan loan = loanService.findOne(loanId);

        // Vérifier si le prêt existe et n'est pas déjà retourné
        if (loan != null && !loan.getReturned()) {
            loan.setReturned(true);
            loanService.save(loan); // Enregistrer les modifications

            return "redirect:/loanrequests/user-requests";
        } else {
            // Gérer le cas où le prêt n'existe pas ou est déjà retourné
            return "redirect:/error"; // Rediriger vers une page d'erreur appropriée
        }
    }

}
