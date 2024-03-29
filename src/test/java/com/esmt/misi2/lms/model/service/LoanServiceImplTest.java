package com.esmt.misi2.lms.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.esmt.misi2.lms.model.dao.ILoanDao;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class LoanServiceImplTest {

    private ILoanService loanService;
    private ILoanDao loanDao;

    @BeforeEach
    public void setUp() {
        loanDao = mock(ILoanDao.class);
        loanService = new LoanServiceImpl(loanDao);
    }

    @Test
    public void testFindAll() {
        List<Loan> expectedLoans = new ArrayList<>();
        when(loanDao.findAll()).thenReturn(expectedLoans);

        List<Loan> actualLoans = loanService.findAll();

        assertEquals(expectedLoans, actualLoans);
    }

    @Test
    public void testFindAllPaginable() {
        Page<Loan> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(loanDao.findAll(pageable)).thenReturn(expectedPage);

        Page<Loan> actualPage = loanService.findAllPaginable(pageable);

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testSave() {
        Loan loan = new Loan();
        when(loanDao.save(loan)).thenReturn(loan);

        Loan savedLoan = loanService.save(loan);

        assertEquals(loan, savedLoan);
    }

    @Test
    public void testFindOne() {
        Loan expectedLoan = new Loan();
        when(loanDao.findById(1L)).thenReturn(Optional.of(expectedLoan));

        Loan actualLoan = loanService.findOne(1L);

        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void testFindOneNotFound() {
        when(loanDao.findById(1L)).thenReturn(Optional.empty());

        Loan actualLoan = loanService.findOne(1L);

        assertNull(actualLoan);
    }

    @Test
    public void testFindByUserAndBook() {
        UserModel user = new UserModel();
        Book book = new Book();
        Loan expectedLoan = new Loan();
        when(loanDao.findByUserAndBook(user, book)).thenReturn(expectedLoan);

        Loan actualLoan = loanService.findByUserAndBook(user, book);

        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void testDelete() {
        loanService.delete(1L);

        verify(loanDao, times(1)).deleteById(1L);
    }

    @Test
    public void testFindLoanByIdWithBooks() {
        Loan expectedLoan = new Loan();
        when(loanDao.fetchByIdWithBooks(1L)).thenReturn(expectedLoan);

        Loan actualLoan = loanService.findLoanByIdWithBooks(1L);

        assertEquals(expectedLoan, actualLoan);
    }
}
