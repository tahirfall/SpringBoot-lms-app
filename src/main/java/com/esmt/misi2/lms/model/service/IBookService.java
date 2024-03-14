package com.esmt.misi2.lms.model.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import com.esmt.misi2.lms.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService {

	public List<Book> findAll();
	
	public Page<Book> findAll(Pageable pageable);

	public Book save(Book book);

	public Book findOne(Long id);

	public void delete(Long id);

	List<Book> search(String keyword);

	byte[] getImageContentById(Long id) throws FileNotFoundException;
}


