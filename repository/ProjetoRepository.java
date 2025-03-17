package com.pdcase.aulas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pdcase.aulas.models.Grupo;
import com.pdcase.aulas.models.Projeto;
 
public interface ProjetoRepository extends CrudRepository<Projeto, Long> {
	
	Projeto findById(long id);
	
	List<Projeto> findByNome(String nome);	
}