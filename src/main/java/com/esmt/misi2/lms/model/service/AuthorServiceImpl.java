package com.esmt.misi2.lms.model.service;

import java.util.List;

import com.esmt.misi2.lms.model.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esmt.misi2.lms.model.dao.IAuthorDao;

@Service
public class AuthorServiceImpl implements IAuthorService {

	@Autowired
	private IAuthorDao authorDao;

	@Override
	public List<Author> findAll() {
		return (List<Author>) authorDao.findAll();
	}

	@Override
	public Page<Author> findAll(Pageable pageable) {
		return authorDao.findAll(pageable);
	}

	@Override
	public void save(Author author) {
		authorDao.save(author);
	}

	@Override
	public Author findOne(Long id) {
		return authorDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		authorDao.deleteById(id);
	}
}
