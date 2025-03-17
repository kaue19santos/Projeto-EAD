package com.pdcase.aulas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdcase.aulas.models.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, Long> {
	Grupo findById(long id);

	List<Grupo> findByNome(String nome);
}
