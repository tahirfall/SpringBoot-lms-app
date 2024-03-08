package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmt.misi2.lms.model.dao.IUsersDao;

@Service
public class userServiceImpl implements IUserService {

	@Autowired
	private IUsersDao userDao;
	
	@Override
	public List<UserModel> findAll() {
		return (List<UserModel>) userDao.findAll();
	}

	@Override
	public Page<UserModel> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	@Override
	public UserModel save(UserModel user) {
		userDao.save(user);
        return user;
    }

	@Override
	public UserModel findOne(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public UserModel findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public UserModel fetchByIdWithLoans(Long id) {
		return userDao.fetchByIdWithLoan(id);
	}

}


