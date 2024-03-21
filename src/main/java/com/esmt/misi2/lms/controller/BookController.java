package com.esmt.misi2.lms.controller;

import com.esmt.misi2.lms.model.entity.Book;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esmt.misi2.lms.model.service.IBookService;
import com.esmt.misi2.lms.util.paginator.PageRender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@SessionAttributes("book")
public class BookController {

	@Autowired
	private IBookService bookService;

	
	@GetMapping("/list-books")
	public String listBooks(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		Pageable pageable = PageRequest.of(page, 4);

		Page<Book> books = bookService.findAll(pageable);

		PageRender<Book> pageRender = new PageRender<>("/books/list-books", books);

		model.addAttribute("title", "List of books");
		model.addAttribute("books", books);
		model.addAttribute("page", pageRender);

		return "books/list-books";
	}

	@GetMapping("/create-book")
	public String createBook(Model model) {


		model.addAttribute("title", "Add a new book");
		Book book = new Book();

		model.addAttribute("book", book);

		return "books/new-book"; // html
	}

	/*
	@PostMapping("/create-book")
	public String createBook(@Valid Book book, BindingResult result, Model model,
							 SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Add a new book");
			model.addAttribute("book", book);
			return "books/new-book";
		}

		book.setDisponible(book.isDisponible());
		bookService.save(book);
		status.setComplete();
		flash.addFlashAttribute("success", "Book added successfully");

		return "redirect:/books/list-books";
	}
	*/
	@PostMapping("/create-book")
	public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult result, Model model,
							 @RequestParam("image") MultipartFile image,
							 SessionStatus status, RedirectAttributes flash) {

		if (result.hasErrors()) {
			model.addAttribute("title", "Add a new book");
			model.addAttribute("book", book);
			return "books/new-book";
		}

		// Vérifiez si une image est téléchargée
		if (!image.isEmpty()) {
			try {
				book.setImageContent(image.getBytes());
				book.setImageType(image.getContentType());
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		bookService.save(book);
		status.setComplete();
		flash.addFlashAttribute("success", "Book saved successfully");

		return "redirect:/books/list-books";
	}



	@GetMapping("/edit-book/{id}")
	public String editBook(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Book book = null;

		if (id > 0) {
			book = bookService.findOne(id);

			if (book == null) {
				flash.addFlashAttribute("error", "The book does not exist!!");
				return "redirect:/books/list-books";
			}
		} else {
			return "redirect:/books/list-books";
		}
		
		model.addAttribute("title", "Edit book");
		model.addAttribute("book", book);

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

	@GetMapping("/detail/{id}")
	public String detailBook(@PathVariable(value = "id") Long id, Model model) {

		Book book = bookService.findOne(id);

		if (book == null) {
			return "redirect:/books/list-books";
		}

		model.addAttribute("book", book);
		model.addAttribute("title", "Detail of book: " + book.getTitle());

		return "books/detail";
	}


	@GetMapping("/search-books")
	public String searchBooks(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<Book> searchResults = bookService.search(keyword);
		model.addAttribute("title", "Search Results");
		model.addAttribute("books", searchResults);
		return "books/search-results";
	}


	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
		try {
			byte[] imageBytes = bookService.getImageContentById(id);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (FileNotFoundException e) {
			// Gérer l'erreur de manière appropriée, par exemple, renvoyer une image par défaut
			return ResponseEntity.notFound().build();
		}
	}





}



