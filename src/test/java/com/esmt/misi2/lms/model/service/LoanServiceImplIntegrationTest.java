package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.ILoanDao;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoanServiceImplIntegrationTest {

    @Autowired
    private ILoanService loanService;

    @MockBean
    private ILoanDao loanDao;

    @Test
    void testFindAll() {
        // Simulate data
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        when(loanDao.findAll()).thenReturn(List.of(loan1, loan2));

        // Test
        assertEquals(2, loanService.findAll().size());
    }

    @Test
    void testFindAllPaginable() {
        // Simulate data
        Page<Loan> page = mock(Page.class);
        when(loanDao.findAll(any(PageRequest.class))).thenReturn(page);

        // Test
        assertEquals(page, loanService.findAllPaginable(PageRequest.of(0, 10)));
    }

    @Test
    void testSave() {
        // Simulate data
        Loan loan = new Loan();
        when(loanDao.save(loan)).thenReturn(loan);

        // Test
        assertEquals(loan, loanService.save(loan));
    }

    @Test
    void testFindOne() {
        // Simulate data
        Loan loan = new Loan();
        when(loanDao.findById(1L)).thenReturn(Optional.of(loan));

        // Test
        assertEquals(loan, loanService.findOne(1L));
    }

    @Test
    void testFindByUserAndBook() {
        // Simulate data
        UserModel user = new UserModel();
        Book book = new Book();
        Loan loan = new Loan();
        when(loanDao.findByUserAndBook(user, book)).thenReturn(loan);

        // Test
        assertEquals(loan, loanService.findByUserAndBook(user, book));
    }

    @Test
    void testDelete() {
        // Test
        loanService.delete(1L);

        // Verify
        verify(loanDao, times(1)).deleteById(1L);
    }

    @Test
    void testFindLoanByIdWithBooks() {
        // Simulate data
        Loan loan = new Loan();
        when(loanDao.fetchByIdWithBooks(1L)).thenReturn(loan);

        // Test
        assertEquals(loan, loanService.findLoanByIdWithBooks(1L));
    }
}
