package com.esmt.misi2.lms.model.service;

import net.coobird.thumbnailator.Thumbnails;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
	public Book save(Book book) {
		bookDao.save(book);
        return book;
    }

	@Override
	public Book findOne(Long id) {
		return bookDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		bookDao.deleteById(id);
	}

	@Override
	public List<Book> search(String keyword) {return bookDao.search(keyword);}



	public byte[] getImageContentById(Long id) throws FileNotFoundException {
		Optional<Book> bookOptional = bookDao.findById(id);

		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			byte[] originalImage = book.getImageContent();

			return originalImage;
		} else {
			// Gérer le cas où aucun livre avec l'ID spécifié n'est trouvé
			throw new FileNotFoundException("Livre non trouvé avec l'ID : " + id);
		}
	}

	public BookServiceImpl(IBookDao bookDao) {
		this.bookDao = bookDao;
	}

}


