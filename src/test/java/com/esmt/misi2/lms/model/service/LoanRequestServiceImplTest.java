package com.esmt.misi2.lms.model.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.esmt.misi2.lms.model.dao.ILoanRequestDao;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class LoanRequestServiceImplTest {

    private ILoanRequestService loanRequestService;
    private ILoanRequestDao loanRequestDao;

    @BeforeEach
    public void setUp() {
        loanRequestDao = mock(ILoanRequestDao.class);
        loanRequestService = new LoanRequestServiceImpl(loanRequestDao);
    }

    @Test
    public void testFindAll() {
        List<LoanRequest> expectedLoanRequests = new ArrayList<>();
        when(loanRequestDao.findAll()).thenReturn(expectedLoanRequests);

        List<LoanRequest> actualLoanRequests = loanRequestService.findAll();

        assertEquals(expectedLoanRequests, actualLoanRequests);
    }

    @Test
    public void testFindAllPaginable() {
        Page<LoanRequest> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(loanRequestDao.findAll(pageable)).thenReturn(expectedPage);

        Page<LoanRequest> actualPage = loanRequestService.findAllPaginable(pageable);

        assertEquals(expectedPage, actualPage);
    }

    @Test
    public void testSave() {
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestDao.save(loanRequest)).thenReturn(loanRequest);

        LoanRequest savedLoanRequest = loanRequestService.save(loanRequest);

        assertEquals(loanRequest, savedLoanRequest);
    }

    @Test
    public void testFindOne() {
        LoanRequest expectedLoanRequest = new LoanRequest();
        when(loanRequestDao.findById(1L)).thenReturn(Optional.of(expectedLoanRequest));

        LoanRequest actualLoanRequest = loanRequestService.findOne(1L);

        assertEquals(expectedLoanRequest, actualLoanRequest);
    }

    @Test
    public void testFindOneNotFound() {
        when(loanRequestDao.findById(1L)).thenReturn(Optional.empty());

        LoanRequest actualLoanRequest = loanRequestService.findOne(1L);

        assertNull(actualLoanRequest);
    }

    @Test
    public void testDelete() {
        loanRequestService.delete(1L);

        verify(loanRequestDao, times(1)).deleteById(1L);
    }

    @Test
    public void testFindByUserAndBook() {
        UserModel user = new UserModel();
        Book book = new Book();
        LoanRequest expectedLoanRequest = new LoanRequest();
        when(loanRequestDao.findByUserAndBook(user, book)).thenReturn(expectedLoanRequest);

        LoanRequest actualLoanRequest = loanRequestService.findByUserAndBook(user, book);

        assertEquals(expectedLoanRequest, actualLoanRequest);
    }

    @Test
    public void testFindByUser() {
        UserModel user = new UserModel();
        List<LoanRequest> expectedLoanRequests = new ArrayList<>();
        when(loanRequestDao.findByUser(user)).thenReturn(expectedLoanRequests);

        List<LoanRequest> actualLoanRequests = loanRequestService.findByUser(user);

        assertEquals(expectedLoanRequests, actualLoanRequests);
    }


}
