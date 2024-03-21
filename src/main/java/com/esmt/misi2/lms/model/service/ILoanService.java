package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILoanService {

	public List<Loan> findAll();
	
	public Page<Loan> findAllPaginable(Pageable pageable);

	public Loan save(Loan loan);

	public Loan findOne(Long id);
	Loan findByUserAndBook(UserModel user, Book book);
	public Loan findLoanByIdWithBooks(Long id);

	public void delete(Long id);

}


