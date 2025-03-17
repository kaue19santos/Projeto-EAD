package com.pdcase.aulas.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Aula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_aula;
	
	@NotEmpty
	private String nome;
	
	@NotNull
	private String descricao;

	@NotEmpty
	private String link;
	
	@NotNull
	private LocalDate data_criacao;
	
	@NotNull
	private LocalDateTime data_atualizacao;
	
	@NotEmpty
	private String duracao;
	
	@NotNull
	private String status;
	
	@ManyToOne
    @JoinColumn(name = "id_projeto") // Chave estrangeira na tabela Aula
    private Projeto projeto;
	
	@ManyToMany(mappedBy = "aulas")
    private Set<Grupo> grupos = new HashSet<>();
	
	// Construtor padrão
			public Aula() {
				this.data_criacao = LocalDate.now();
			    this.data_atualizacao = LocalDateTime.now();
			    this.status = "";
			    this.descricao = "";
			}

	// Getter e Setters
	
	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}

	public long getId_aula() {
		return id_aula;
	}

	public void setId_aula(long id_aula) {
		this.id_aula = id_aula;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

}
