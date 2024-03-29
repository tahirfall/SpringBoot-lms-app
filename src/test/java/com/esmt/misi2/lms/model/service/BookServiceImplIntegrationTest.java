package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.IBookDao;
import com.esmt.misi2.lms.model.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplIntegrationTest {

    @Autowired
    private IBookService bookService;

    @MockBean
    private IBookDao bookDao;

    @Test
    void testFindAll() {
        // Simulate data
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookDao.findAll()).thenReturn(List.of(book1, book2));

        // Test
        assertEquals(2, bookService.findAll().size());
    }

    @Test
    void testFindAllPaginable() {
        // Simulate data
        Page<Book> page = mock(Page.class);
        when(bookDao.findAll(any(PageRequest.class))).thenReturn(page);

        // Test
        assertEquals(page, bookService.findAll(PageRequest.of(0, 10)));
    }

    @Test
    void testSave() {
        // Simulate data
        Book book = new Book();
        when(bookDao.save(book)).thenReturn(book);

        // Test
        assertEquals(book, bookService.save(book));
    }

    @Test
    void testFindOne() {
        // Simulate data
        Book book = new Book();
        when(bookDao.findById(1L)).thenReturn(Optional.of(book));

        // Test
        assertEquals(book, bookService.findOne(1L));
    }

    @Test
    void testDelete() {
        // Test
        bookService.delete(1L);

        // Verify
        verify(bookDao, times(1)).deleteById(1L);
    }

    @Test
    void testSearch() {
        // Simulate data
        List<Book> books = List.of(new Book(), new Book());
        when(bookDao.search("keyword")).thenReturn(books);

        // Test
        assertEquals(books, bookService.search("keyword"));
    }

    @Test
    void testGetImageContentById() throws FileNotFoundException {
        // Simulate data
        Book book = new Book();
        byte[] imageContent = new byte[]{1, 2, 3};
        book.setImageContent(imageContent);
        when(bookDao.findById(1L)).thenReturn(Optional.of(book));

        // Test
        assertArrayEquals(imageContent, bookService.getImageContentById(1L));
    }
}
