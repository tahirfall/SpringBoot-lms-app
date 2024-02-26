package com.co.andresfot.libreria.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.co.andresfot.libreria.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u LEFT JOIN u.prestamos p WHERE u.id=?1")
	public Usuario fetchByIdWithPrestamos(Long id);

	void save(Usuario usuario);

	Optional<Usuario> findById(Long id);

	void deleteById(Long id);

	List<Usuario> findAll();
}
