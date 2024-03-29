package com.esmt.misi2.lms.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.esmt.misi2.lms.model.dao.IBookDao;
import com.esmt.misi2.lms.model.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BookServiceImplTest {

    private IBookService bookService;
    private IBookDao bookDao;

    @BeforeEach
    public void setUp() {
        bookDao = mock(IBookDao.class);
        bookService = new BookServiceImpl(bookDao);
    }

    @Test
    public void testFindAll() {
        List<Book> expectedBooks = new ArrayList<>();
        when(bookDao.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.findAll();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testFindAllWithPageable() {
        Page<Book> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(bookDao.findAll(pageable)).thenReturn(expectedPage);

        Page<Book> actualPage = bookService.findAll(pageable);

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testSave() {
        Book book = new Book();
        when(bookDao.save(book)).thenReturn(book);

        Book savedBook = bookService.save(book);

        assertEquals(book, savedBook);
    }

    @Test
    public void testFindOne() {
        Book expectedBook = new Book();
        when(bookDao.findById(1L)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.findOne(1L);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void testFindOneNotFound() {
        when(bookDao.findById(1L)).thenReturn(Optional.empty());

        Book actualBook = bookService.findOne(1L);

        assertNull(actualBook);
    }

    @Test
    public void testDelete() {
        bookService.delete(1L);

        verify(bookDao, times(1)).deleteById(1L);
    }

    @Test
    public void testSearch() {
        List<Book> expectedBooks = new ArrayList<>();
        when(bookDao.search("keyword")).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.search("keyword");

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetImageContentById() throws FileNotFoundException {
        Book book = new Book();
        book.setImageContent(new byte[]{1, 2, 3});
        when(bookDao.findById(1L)).thenReturn(Optional.of(book));

        byte[] imageContent = bookService.getImageContentById(1L);

        assertArrayEquals(new byte[]{1, 2, 3}, imageContent);
    }

    @Test
    public void testGetImageContentByIdNotFound() {
        when(bookDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> {
            bookService.getImageContentById(1L);
        });
    }
}
