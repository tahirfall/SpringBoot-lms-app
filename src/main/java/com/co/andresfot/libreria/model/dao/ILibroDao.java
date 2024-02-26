package com.co.andresfot.libreria.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.co.andresfot.libreria.model.entity.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroDao extends PagingAndSortingRepository<Libro, Long>{

    void save(Libro libro);

    Optional<Libro> findById(Long id);

    void deleteById(Long id);

    List<Libro> findAll();
}
