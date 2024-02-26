package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmt.misi2.lms.model.dao.IBookDao;

@Service
public class BookServiceImpl implements IBookService {
	
	@Autowired
	private IBookDao bookDao;

	@Override
	public List<Book> findAll() {
		return (List<Book>) bookDao.findAll();
	}
	
	@Override
	public Page<Book> findAll(Pageable pageable) {
		return bookDao.findAll(pageable);
	}

	@Override
	public void save(Book book) {
		bookDao.save(book);
	}

	@Override
	public Book findOne(Long id) {
		return bookDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		bookDao.deleteById(id);
	}

}
