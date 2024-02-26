package com.esmt.mpisi2.lms.model.service;

import java.util.List;

import com.esmt.mpisi2.lms.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	public List<User> findAll();
	
	public Page<User> findAll(Pageable pageable);

	public void save(User user);

	public User findOne(Long id);
	
	public User fetchByIdWithLoans(Long id);

	public void delete(Long id);
	
}
