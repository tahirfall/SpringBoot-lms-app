package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IUsersDao extends PagingAndSortingRepository<UserModel, Long>{

	@Query("SELECT u FROM UserModel u LEFT JOIN u.loans p WHERE u.id=?1")
	public UserModel fetchByIdWithLoan(Long id);

	public UserModel findByUsername(String username);

	void save(UserModel user);

	Optional<UserModel> findById(Long id);

	List<UserModel> deleteById(Long id);

	List<UserModel> findAll();

	int count();

	@Query("SELECT COUNT(u) FROM UserModel u WHERE u.role = 'ROLE_USER'")
	int countByRoleUser();

}



