package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.ILoanRequestDao;
import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.LoanRequest;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanRequestServiceImpl implements ILoanRequestService {

    @Autowired
    private ILoanRequestDao loanRequestDao;

    @Override
    public List<LoanRequest> findAll() {
        return (List<LoanRequest>) loanRequestDao.findAll();
    }

    @Override
    public Page<LoanRequest> findAllPaginable(Pageable pageable) {
        return loanRequestDao.findAll(pageable);
    }

    @Override
    public LoanRequest save(LoanRequest loanRequest) {
        loanRequestDao.save(loanRequest);
        return loanRequest;
    }

    @Override
    public LoanRequest findOne(Long id) {
        return loanRequestDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        loanRequestDao.deleteById(id);
    }

    @Override
    public LoanRequest findByUserAndBook(UserModel user, Book book) {
        return loanRequestDao.findByUserAndBook(user, book);
    }

    @Override
    public List<LoanRequest> findByUser(UserModel user) {
        return loanRequestDao.findByUser(user);
    }
}


