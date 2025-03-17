package com.pdcase.aulas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pdcase.aulas.models.Aula;
import com.pdcase.aulas.models.Projeto;

public interface AulaRepository extends CrudRepository<Aula, Long> {

    Iterable<Aula> findByProjeto(Projeto projeto);

    List<Aula> findByNome(String nome);

    @Query("SELECT a FROM Aula a WHERE a.id_aula = :id")
    Aula getById(@Param("id") long id);
}
