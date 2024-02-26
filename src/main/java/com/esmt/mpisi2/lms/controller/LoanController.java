package com.esmt.mpisi2.lms.controller;

import java.util.List;

import com.esmt.mpisi2.lms.model.entity.Book;
import com.esmt.mpisi2.lms.model.entity.Loan;
import com.esmt.mpisi2.lms.model.entity.User;
import com.esmt.mpisi2.lms.model.service.IBookService;
import com.esmt.mpisi2.lms.model.service.ILoanService;
import com.esmt.mpisi2.lms.model.service.IUserService;
import com.esmt.mpisi2.lms.util.paginator.PageRender;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
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
		List<User> users = userService.findAll();
		List<Book> books = bookService.findAll();
		
		model.addAttribute("title", "create new loan");
		model.addAttribute("loan", loan);
		model.addAttribute("users", users);
		model.addAttribute("books", books);
		
		return "loans/new-loan";
	}
	
	@PostMapping("/create-loan")
	public String saveLoan(@RequestParam(name = "book", required = false) Long bookId,
								  @RequestParam(name = "user", required = false) Long userId,
								  @Valid Loan loan, BindingResult result, Model model,
								  SessionStatus status, RedirectAttributes flash) {
		
		List<User> users = userService.findAll();
		List<Book> books = bookService.findAll();
		
		Book book = bookService.findOne(bookId);
		User user = userService.findOne(userId);
		
		if(result.hasErrors()) {
			model.addAttribute("title", "create a new loan");
			model.addAttribute("loan", loan);
			model.addAttribute("users", users);
			model.addAttribute("books", books);
	
			return "loans/new-loan";
		}
		
		loan.setBook(book);
		loan.setUser(user);

        loan.setReturned(loan.getReturned());
		
		loanService.save(loan);
		status.setComplete();
		flash.addFlashAttribute("success", "loan created successfully!!");
		
		return "redirect:/loans/list-loans";
	}
	
	@GetMapping("/edit-loan/{id}")
	public String editLoan(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Loan loan = null;
		List<User> users = userService.findAll();
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
	
}

