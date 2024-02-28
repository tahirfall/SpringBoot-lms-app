package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	public List<Users> findAll();
	
	public Page<Users> findAll(Pageable pageable);

	public void save(Users user);

	public Users findOne(Long id);
	
	public Users fetchByIdWithLoans(Long id);

	public void delete(Long id);

	public Users findByUsername(String username);
}


