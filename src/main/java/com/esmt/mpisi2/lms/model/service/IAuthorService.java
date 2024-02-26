package com.esmt.mpisi2.lms.model.service;

import java.util.List;

import com.esmt.mpisi2.lms.model.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorService {

	public List<Author> findAll();

	public Page<Author> findAll(Pageable pageable);

	public void save(Author author);

	public Author findOne(Long id);

	public void delete(Long id);

}
