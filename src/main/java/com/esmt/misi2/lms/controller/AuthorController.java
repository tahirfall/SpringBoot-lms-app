package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.entity.Author;
import com.esmt.misi2.lms.model.service.IAuthorService;
import com.esmt.misi2.lms.util.paginator.PageRender;
import jakarta.validation.Valid;

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
@RequestMapping("/authors")
@SessionAttributes("author")
public class AuthorController {
	
	@Autowired
	private IAuthorService authorService;

	@GetMapping("/list-authors")
	public String listsAuthors (@RequestParam(name = "page", defaultValue = "0") int page,
			Model model) {
		
		Pageable pageable = PageRequest.of(page, 5);
		
		Page<Author> authors = authorService.findAll(pageable);
		
		PageRender<Author> pageRender = new PageRender<>("/authors/list-authors", authors);
		
		model.addAttribute("title", "lists of authors");
		model.addAttribute("authors", authors);
		model.addAttribute("page", pageRender);
		
		return "authors/list-authors";
	}
	
	@GetMapping("/create-author")
	public String createAuthor(Model model) {
		
		Author author = new Author();
		
		model.addAttribute("title", "Add a new author");
		model.addAttribute("author", author);
		
		return "authors/new-author";
	}
	
	@PostMapping("/create-author")
	public String saveAuthor(@Valid Author author, BindingResult result, Model model,
							   SessionStatus status, RedirectAttributes flash) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Add a new author");
			model.addAttribute("author", author);
			return "authors/new-author"; //html
		}
		
		authorService.save(author);
		status.setComplete();
		flash.addFlashAttribute("success", "Author created successfully!!!");
		
		return "redirect:/authors/list-authors";
	}
	
	@GetMapping("/edit-author/{id}")
	public String editAuthor(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Author author = null;
		
		if(id > 0 ) {
			author = authorService.findOne(id);
			
			if(author == null) {
				flash.addFlashAttribute("error", "The author does not exit!!!");
				return "redirect:/authors/list-authors";
			}
		} else {
			System.out.println("The ID cannot be zero.");
		}
		
		model.addAttribute("title", "edit author");
		model.addAttribute("author", author);
		
		return "authors/new-author";
	}
	
	@GetMapping("/delete-author/{id}")
	public String deleteAuthor(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			authorService.delete(id);
			flash.addFlashAttribute("success", "Author deleted successfully!!!");
		}
		
		return "redirect:/authors/list-authors";
	}
	
}
