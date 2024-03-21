package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILoanRequestService {
    List<LoanRequest> findAll();

    Page<LoanRequest> findAllPaginable(Pageable pageable);

    LoanRequest save(LoanRequest loanRequest);

    LoanRequest findOne(Long id);

    void delete(Long id);

    LoanRequest findByUserAndBook(UserModel user, Book book);

    List<LoanRequest> findByUser(UserModel user);

}


