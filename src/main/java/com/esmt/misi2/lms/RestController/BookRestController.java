package com.esmt.misi2.lms.RestController;

import com.esmt.misi2.lms.exceptions.BookNotFoundException;
import com.esmt.misi2.lms.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<Book>> listBooks(@RequestParam(name = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Book> books = bookService.findAll(pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.findOne(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.save(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/edit-book/{id}")
    public ResponseEntity<Book> editBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book existingBook = bookService.findOne(id);

        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setDescription(book.getDescription());
            existingBook.setDisponible(book.isDisponible());

            Book updatedBook = bookService.save(existingBook);
            return ResponseEntity.ok(updatedBook);
        } else {
            throw new BookNotFoundException("Book with id: " + id + " not found");
        }
    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-books")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(name = "keyword", required = false) String keyword) {
        List<Book> foundBooks = bookService.search(keyword);
        return ResponseEntity.ok(foundBooks);
    }
}
