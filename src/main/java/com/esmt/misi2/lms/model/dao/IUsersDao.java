package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUsersDao extends JpaRepository<UserModel, Long> {

	@Query("SELECT u FROM UserModel u LEFT JOIN u.loans p WHERE u.id=?1")
	public UserModel fetchByIdWithLoan(Long id);

	public UserModel findByUsername(String username);

	Optional<UserModel> findById(Long id);

	void deleteById(Long id);

	List<UserModel> findAll();

	long count();

	@Query("SELECT COUNT(u) FROM UserModel u WHERE u.role = 'ROLE_USER'")
	int countByRoleUser();

}



