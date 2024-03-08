package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	public List<UserModel> findAll();
	
	public Page<UserModel> findAll(Pageable pageable);

	public UserModel save(UserModel user);

	public UserModel findOne(Long id);
	
	public UserModel fetchByIdWithLoans(Long id);

	public void delete(Long id);

	public UserModel findByUsername(String username);
}


