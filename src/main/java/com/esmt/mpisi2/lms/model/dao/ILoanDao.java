package com.esmt.mpisi2.lms.model.dao;

import com.esmt.mpisi2.lms.model.entity.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ILoanDao extends PagingAndSortingRepository<Loan, Long>{

	@Query("SELECT p FROM Loan p LEFT JOIN p.book l WHERE p.id=?1")
	public Loan fetchByIdWithBooks(Long id);

	void save(Loan loan);

	Optional<Loan> findById(Long id);

	void deleteById(Long id);

	List<Loan> findAll();
}



