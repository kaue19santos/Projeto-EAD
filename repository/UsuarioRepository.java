package com.pdcase.aulas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pdcase.aulas.models.Grupo;
import com.pdcase.aulas.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	Iterable<Usuario> findByGrupo(Grupo grupo);

    List<Usuario> findByNome(String nome);

    @Query("SELECT a FROM Usuario a WHERE a.id_usuario = :id")
    Usuario getById(@Param("id") long id);
}
