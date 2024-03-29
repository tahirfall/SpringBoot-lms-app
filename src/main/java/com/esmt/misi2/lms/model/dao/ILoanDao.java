package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Book;
import com.esmt.misi2.lms.model.entity.Loan;
import com.esmt.misi2.lms.model.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ILoanDao extends JpaRepository<Loan, Long> {

	@Query("SELECT p FROM Loan p LEFT JOIN p.book l WHERE p.id=?1")
	public Loan fetchByIdWithBooks(Long id);

	Optional<Loan> findById(Long id);
	Loan findByUserAndBook(UserModel user, Book book);

	void deleteById(Long id);

	List<Loan> findAll();

	long count();
}



