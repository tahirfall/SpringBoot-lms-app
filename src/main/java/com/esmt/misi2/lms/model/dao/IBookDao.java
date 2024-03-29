package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Book;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IBookDao extends JpaRepository<Book, Long> {


    Optional<Book> findById(Long id);

    void deleteById(Long id);

    List<Book> findAll();

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> search(@Param("keyword") String keyword);

    long count();

    @Query("SELECT COUNT(u) FROM Book u WHERE u.disponible = true")
    int countAvailableBook();

}



