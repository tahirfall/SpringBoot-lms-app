package com.esmt.misi2.lms.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.esmt.misi2.lms.model.entity.UserLogin;

public interface IUserDao extends CrudRepository<UserLogin, Long>{
	public UserLogin findByUsername(String username);
}
