package com.co.andresfot.libreria.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.co.andresfot.libreria.model.entity.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutorDao extends PagingAndSortingRepository<Autor, Long>{

    void save(Autor autor);

    Optional<Autor> findById(Long id);

    void deleteById(Long id);

    List<Autor> findAll();
}
