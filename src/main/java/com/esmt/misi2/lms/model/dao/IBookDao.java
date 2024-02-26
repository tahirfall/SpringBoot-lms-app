package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IBookDao extends PagingAndSortingRepository<Book, Long>{

    void save(Book book);

    Optional<Book> findById(Long id);

    void deleteById(Long id);

    List<Book> findAll();
}
