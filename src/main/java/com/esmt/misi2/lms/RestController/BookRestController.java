package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.service.IBookService;
import com.esmt.misi2.lms.util.paginator.PageRender;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/list-books")
    public Page<Book> listBooks(@RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.findOne(id);
    }

    @PostMapping("/create-book")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookService.save(book);
    }


    @PutMapping("/edit-book/{id}")
    public Book editBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existingBook = bookService.findOne(id);

        if (existingBook != null) {
            // Mettre à jour les propriétés du livre existant avec les nouvelles valeurs
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setDisponible(book.isDisponible());
            // Ajoutez d'autres propriétés à mettre à jour selon vos besoins

            return bookService.save(existingBook);
        } else {
            // Gérer le cas où le livre n'est pas trouvé
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }


    @DeleteMapping("/delete-book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/search-books")
    public List<Book> searchBooks(@RequestParam(name = "keyword", required = false) String keyword) {
        return bookService.search(keyword);
    }
}

