package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.IBookDao;
import com.esmt.misi2.lms.model.dao.ILoanDao;
import com.esmt.misi2.lms.model.dao.ILoanRequestDao;
import com.esmt.misi2.lms.model.dao.IUsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    @Autowired
    private IBookDao bookDao;

    @Autowired
    private IUsersDao userDao;

    @Autowired
    private ILoanDao loanDao;
    @Autowired
    private ILoanRequestDao loanRequestDaoDao;

    public Long getNumberOfBooks() {
        return bookDao.count();
    }

    public Long getNumberOfUsers() {
        return userDao.count();
    }

    public Long getNumberOfLoans() {
        return loanDao.count();
    }

    public int getNumberOfSimpleUser() {return userDao.countByRoleUser();}

    public int getNumberAvailableBook() {return bookDao.countAvailableBook();}
    public long getNumberOfLoanRequests() {return loanRequestDaoDao.count();}
}


