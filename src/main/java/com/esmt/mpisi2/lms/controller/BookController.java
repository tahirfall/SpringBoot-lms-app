package com.esmt.mpisi2.lms.controller;

import java.util.List;

import com.esmt.mpisi2.lms.model.entity.Author;
import com.esmt.mpisi2.lms.model.entity.Book;
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

import com.esmt.mpisi2.lms.model.service.IAuthorService;
import com.esmt.mpisi2.lms.model.service.IBookService;
import com.esmt.mpisi2.lms.util.paginator.PageRender;

@Controller
@RequestMapping("/books")
@SessionAttributes("book")
public class BookController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private IAuthorService authorService;
	
	@GetMapping("/list-books")
	public String listBooks(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, 5);

		Page<Book> books = bookService.findAll(pageable);

		PageRender<Book> pageRender = new PageRender<>("/books/list-books", books);

		model.addAttribute("title", "List of books");
		model.addAttribute("books", books);
		model.addAttribute("page", pageRender);

		return "books/list-books";
	}

	@GetMapping("/create-book")
	public String createBook(Model model) {

		List<Author> authors = authorService.findAll();

		model.addAttribute("title", "Add a new book");
		Book book = new Book();

		model.addAttribute("book", book);
		model.addAttribute("authors", authors);

		return "books/new-book"; // html
	}

	@PostMapping("/create-book")
	public String createBook(@RequestParam( name = "author", required = false) Long authorId,
							 @Valid Book book, BindingResult result, Model model,
							 SessionStatus status, RedirectAttributes flash) {

		List<Author> authors = authorService.findAll();
		
		Author author = authorService.findOne(authorId);

		if (result.hasErrors()) {
			model.addAttribute("title", "Add a new book");
			model.addAttribute("book", book);
			model.addAttribute("authors", authors);
			return "books/new-book";
		}
		
		book.setAuthor(author);

        book.setDisponible(book.isDisponible());
		
		bookService.save(book);
		status.setComplete();
		flash.addFlashAttribute("success", "book added successfully");

		return "redirect:/books/list-books";
	}

	@GetMapping("/edit-book/{id}")
	public String editBook(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Book book = null;
		List<Author> authors = authorService.findAll();

		if (id > 0) {
			book = bookService.findOne(id);

			if (book == null) {
				flash.addFlashAttribute("error", "The book does not exist!!");
				return "redirect:/books/list-books";
			}
		} else {
			return "redirect:/books/list-books";
		}
		
		model.addAttribute("title", "edit book");
		model.addAttribute("book", book);
		model.addAttribute("authors", authors);

		return "books/new-book";
	}
	
	@GetMapping("/delete-book/{id}")
	public String deleteBook(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			bookService.delete(id);
			flash.addFlashAttribute("success", "book deleted successfully!!");
		}
		
		return "redirect:/books/list-books";
	}

}



