package com.esmt.mpisi2.lms.model.service;

import java.util.List;

import com.esmt.mpisi2.lms.model.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILoanService {

	public List<Loan> findAll();
	
	public Page<Loan> findAllPaginable(Pageable pageable);

	public void save(Loan loan);

	public Loan findOne(Long id);
	
	public Loan findLoanByIdWithBooks(Long id);

	public void delete(Long id);

}


