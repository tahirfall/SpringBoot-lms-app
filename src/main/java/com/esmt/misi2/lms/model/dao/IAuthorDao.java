package com.esmt.misi2.lms.model.dao;

import com.esmt.misi2.lms.model.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IAuthorDao extends PagingAndSortingRepository<Author, Long>{

    void save(Author author);

    Optional<Author> findById(Long id);

    void deleteById(Long id);

    List<Author> findAll();
}
