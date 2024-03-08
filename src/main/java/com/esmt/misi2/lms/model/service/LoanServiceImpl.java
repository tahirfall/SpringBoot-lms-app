package com.esmt.misi2.lms.model.service;


import java.util.List;

import com.esmt.misi2.lms.model.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.esmt.misi2.lms.model.dao.ILoanDao;

@Service
public class LoanServiceImpl implements ILoanService {

	@Autowired
	private ILoanDao loanDao;

	@Override
	public List<Loan> findAll() {
		return (List<Loan>) loanDao.findAll();
	}
	
	@Override
	public Page<Loan> findAllPaginable(Pageable pageable) {
		return loanDao.findAll(pageable);
	}

	@Override
	public Loan save(Loan loan) {
		loanDao.save(loan);
        return loan;
    }

	@Override
	public Loan findOne(Long id) {
		return loanDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		loanDao.deleteById(id);
	}

	@Override
	public Loan findLoanByIdWithBooks(Long id) {
		return loanDao.fetchByIdWithBooks(id);
	}

}


