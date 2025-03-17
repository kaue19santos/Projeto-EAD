package com.pdcase.aulas.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_grupo;

	@NotEmpty
	private String nome;

	@NotNull
	private LocalDate data_criacao;

	@OneToMany(mappedBy = "grupo", cascade = CascadeType.REMOVE)
	private List<Usuario> usuarios;

	@ManyToMany
	@JoinTable(name = "grupo_projeto", joinColumns = @JoinColumn(name = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_projeto"))

	private Set<Projeto> projetos = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "grupo_aula", joinColumns = @JoinColumn(name = "id_grupo"), inverseJoinColumns = @JoinColumn(name = "id_aula"))

	private Set<Aula> aulas = new HashSet<>();

	public long getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(long id_grupo) {
		this.id_grupo = id_grupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(Set<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Set<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}

}