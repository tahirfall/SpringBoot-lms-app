package com.esmt.misi2.lms.controller;

import java.time.LocalDate;
import java.util.List;

import com.esmt.misi2.lms.model.entity.*;
import com.esmt.misi2.lms.model.service.*;
import com.esmt.misi2.lms.util.paginator.PageRender;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loans")
@SessionAttributes("loan")
public class LoanController {

	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBookService bookService;

	@Autowired
	private ILoanRequestService loanRequestService;

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/list-loans")
	public String listLoans(@RequestParam(name = "page", defaultValue = "0") int page,
			Model model) {
		
		
		Pageable pageable = PageRequest.of(page, 8);
		
		Page<Loan> loans = loanService.findAllPaginable(pageable);
		
		PageRender<Loan> pageRender = new PageRender<>("/loans/list-loans", loans);
		
		model.addAttribute("title", "list of loans");
		model.addAttribute("loans", loans);
		model.addAttribute("page", pageRender);
		
		
		return "loans/list-loans";
	}
	
	@GetMapping("/create-loan")
	public String createLoan(Model model) {
		
		Loan loan = new Loan();
		List<UserModel> users = userService.findAll();
		List<Book> books = bookService.findAll();
		
		model.addAttribute("title", "create new loan");
		model.addAttribute("loan", loan);
		model.addAttribute("users", users);
		model.addAttribute("books", books);
		
		return "loans/new-loan";
	}

	@PostMapping("/create-loan")
	public String saveLoan(@ModelAttribute("loan") Loan loan, BindingResult result, Model model,
						   SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			List<UserModel> users = userService.findAll();
			List<Book> books = bookService.findAll();
			model.addAttribute("title", "Accept a loan request");
			model.addAttribute("users", users);
			model.addAttribute("books", books);
			return "loans/new-loan";
		}

		loan.setReturned(loan.getReturned());
		loanService.save(loan);

		// Supprimez la demande associée
		LoanRequest loanRequest = loanRequestService.findByUserAndBook(loan.getUser(), loan.getBook());
		if (loanRequest != null) {
			loanRequest.setStatus(RequestStatus.ACCEPTED);
			loanRequestService.save(loanRequest);
		}

		status.setComplete();
		flash.addFlashAttribute("success", "loan request accepted!!");

		return "redirect:/loanrequests/list-requests";
	}



	@GetMapping("/edit-loan/{id}")
	public String editLoan(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Loan loan = null;
		List<UserModel> users = userService.findAll();
		List<Book> books = bookService.findAll();

		if (id > 0) {
			loan = loanService.findOne(id);

			if (loan == null) {
				flash.addFlashAttribute("error", "The loan does not exist!!");
				return "redirect:/loans/list-loans";
			}
		} else {
			return "redirect:/loans/list-loans";
		}
		
		model.addAttribute("title", "edit loan");
		model.addAttribute("loan", loan);
		model.addAttribute("users", users);
		model.addAttribute("books", books);
		

		return "loans/new-loan";
	}
	
	@GetMapping("/detail/{id}")
	public String detailLoan(@PathVariable(value = "id") Long id, Model model) {
		
		Loan loan = loanService.findLoanByIdWithBooks(id);
		
		if(loan == null) {
			return "redirect:/list-users";
		}
		
		model.addAttribute("title", "Detail of the loan book ");
		model.addAttribute("loan", loan);
		
		return "loans/detail";
	}
	
	@GetMapping("/delete-loan/{id}")
	public String deleteLoan(@PathVariable Long id, RedirectAttributes flash) {
		
		if (id > 0) {
			loanService.delete(id);
			flash.addFlashAttribute("success", "loan deleted successfully!!!");
		}
		
		return "redirect:/loans/list-loans";
	}

	@PostMapping("/approve-request/{id}")
	public String approveRequest(@PathVariable Long id, Model model) {

		LoanRequest loanRequest = loanRequestService.findOne(id);
		if (loanRequest != null) {
			// Pré-remplissez les informations du prêt avec celles de la demande approuvée
			Loan loan = new Loan();
			loan.setUser(loanRequest.getUser());
			loan.setBook(loanRequest.getBook());
			loan.setReturnDate(loan.getReturnDate()); // Définissez la date de retour par défaut

			model.addAttribute("title", "Create a new loan");
			model.addAttribute("loan", loan);
			return "loans/new-loan";
		} else {
			// Gérez le cas où la demande de prêt n'est pas trouvée
			return "redirect:/loanrequests/list-requests";
		}
	}


	@PostMapping("/reject-request/{id}")
	public String rejectRequest(@PathVariable Long id, RedirectAttributes flash) {
		LoanRequest loanRequest = loanRequestService.findOne(id);
		if (loanRequest != null) {
			loanRequest.setStatus(RequestStatus.REJECTED);
			loanRequestService.save(loanRequest);
			flash.addFlashAttribute("success", "Request rejected successfully!");
		} else {
			flash.addFlashAttribute("error", "Request not found!");
		}
		return "redirect:/loanrequests/list-requests";

	}


}

