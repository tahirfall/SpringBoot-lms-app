package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IUsersDao extends PagingAndSortingRepository<Users, Long>{

	@Query("SELECT u FROM Users u LEFT JOIN u.loans p WHERE u.id=?1")
	public Users fetchByIdWithLoan(Long id);

	public Users findByUsername(String username);

	void save(Users user);

	Optional<Users> findById(Long id);

	void deleteById(Long id);

	List<Users> findAll();
}



