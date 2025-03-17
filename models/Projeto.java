package com.pdcase.aulas.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Projeto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_projeto;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String descricao;
	
	@NotNull
	private LocalDate data_criacao;
	
	@NotNull
	private LocalDateTime data_atualizacao;
	
	@NotEmpty
	private String status;
	
	@OneToMany(mappedBy = "projeto", cascade = CascadeType.REMOVE)
	private List<Aula> aulas;
	
	@ManyToMany(mappedBy = "projetos")
    private Set<Grupo> grupos = new HashSet<>();
	
	// Construtor padrão
		public Projeto() {
		    this.data_atualizacao = LocalDateTime.now();
		}
	
	// Getters e Setters
	
	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	public long getId_projeto() {
		return id_projeto;
	}

	public void setId_projeto(long id_projeto) {
		this.id_projeto = id_projeto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public LocalDateTime getData_atualizacao() {
		return data_atualizacao;
	}

	public void setData_atualizacao(LocalDateTime data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
}
