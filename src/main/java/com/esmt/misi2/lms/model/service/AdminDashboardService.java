package com.esmt.misi2.lms.model.service;

import com.esmt.misi2.lms.model.dao.IBookDao;
import com.esmt.misi2.lms.model.dao.ILoanDao;
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

    public int getNumberOfBooks() {
        return bookDao.count();
    }

    public int getNumberOfUsers() {
        return userDao.count();
    }

    public int getNumberOfLoans() {
        return loanDao.count();
    }

    public int getNumberOfSimpleUser() {return userDao.countByRoleUser();}

    public int getNumberAvailableBook() {return bookDao.countAvailableBook();}
}


