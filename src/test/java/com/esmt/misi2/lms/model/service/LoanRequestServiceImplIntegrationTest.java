package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.ILoanRequestDao;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class LoanRequestServiceImplIntegrationTest {

    @Autowired
    private ILoanRequestService loanRequestService;

    @MockBean
    private ILoanRequestDao loanRequestDao;

    @Test
    void testFindAll() {
        // Simulate data
        LoanRequest loanRequest1 = new LoanRequest();
        LoanRequest loanRequest2 = new LoanRequest();
        when(loanRequestDao.findAll()).thenReturn(List.of(loanRequest1, loanRequest2));

        // Test
        assertEquals(2, loanRequestService.findAll().size());
    }

    @Test
    void testFindAllPaginable() {
        // Simulate data
        Page<LoanRequest> page = mock(Page.class);
        when(loanRequestDao.findAll(any(PageRequest.class))).thenReturn(page);

        // Test
        assertEquals(page, loanRequestService.findAllPaginable(PageRequest.of(0, 10)));
    }

    @Test
    void testSave() {
        // Simulate data
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestDao.save(loanRequest)).thenReturn(loanRequest);

        // Test
        assertEquals(loanRequest, loanRequestService.save(loanRequest));
    }

    @Test
    void testFindOne() {
        // Simulate data
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestDao.findById(1L)).thenReturn(Optional.of(loanRequest));

        // Test
        assertEquals(loanRequest, loanRequestService.findOne(1L));
    }

    @Test
    void testDelete() {
        // Test
        loanRequestService.delete(1L);

        // Verify
        verify(loanRequestDao, times(1)).deleteById(1L);
    }

    @Test
    void testFindByUserAndBook() {
        // Simulate data
        UserModel user = new UserModel();
        Book book = new Book();
        LoanRequest loanRequest = new LoanRequest();
        when(loanRequestDao.findByUserAndBook(user, book)).thenReturn(loanRequest);

        // Test
        assertEquals(loanRequest, loanRequestService.findByUserAndBook(user, book));
    }

    @Test
    void testFindByUser() {
        // Simulate data
        UserModel user = new UserModel();
        List<LoanRequest> loanRequests = new ArrayList<>();
        when(loanRequestDao.findByUser(user)).thenReturn(loanRequests);

        // Test
        assertEquals(loanRequests, loanRequestService.findByUser(user));
    }
}
