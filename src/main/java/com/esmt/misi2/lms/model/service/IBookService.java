package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {

	public List<Book> findAll();
	
	public Page<Book> findAll(Pageable pageable);

	public void save(Book book);

	public Book findOne(Long id);

	public void delete(Long id);
	
}
