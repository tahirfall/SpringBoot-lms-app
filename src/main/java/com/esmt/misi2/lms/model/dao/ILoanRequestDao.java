package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ILoanRequestDao extends PagingAndSortingRepository<LoanRequest, Long> {

    void save(LoanRequest loanRequest);

    List<LoanRequest> findAll();
    Optional<LoanRequest> findById(Long id);
    List<LoanRequest> findByUser(UserModel user);


    LoanRequest findByUserAndBook(UserModel user, Book book);
    void deleteById(Long id);
}


