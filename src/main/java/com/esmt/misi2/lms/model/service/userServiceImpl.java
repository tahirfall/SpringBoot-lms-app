package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Users;
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
	public List<Users> findAll() {
		return (List<Users>) userDao.findAll();
	}

	@Override
	public Page<Users> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	@Override
	public void save(Users user) {
		userDao.save(user);
	}

	@Override
	public Users findOne(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public Users findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Users fetchByIdWithLoans(Long id) {
		return userDao.fetchByIdWithLoan(id);
	}

}


