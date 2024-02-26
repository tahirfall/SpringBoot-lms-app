package com.esmt.mpisi2.lms.model.dao;

import com.esmt.mpisi2.lms.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IUsersDao extends PagingAndSortingRepository<User, Long>{

	@Query("SELECT u FROM User u LEFT JOIN u.loans p WHERE u.id=?1")
	public User fetchByIdWithLoan(Long id);

	void save(User user);

	Optional<User> findById(Long id);

	void deleteById(Long id);

	List<User> findAll();
}
