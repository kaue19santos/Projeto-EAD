package com.pdcase.aulas.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_usuario;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String email;
	
	@NotNull
	private String status;
	
	@NotNull
	private String perfil;
	
	@NotNull
	private LocalDate data_nascimento;
	
	@NotEmpty
	private String telefone;
	
	@NotNull
	private String foto_perfil;
	
	@NotNull
	private String senha;
	
	@NotNull
	private LocalDate data_criacao;
	
	@NotNull
	private LocalDateTime data_atualizacao;
	
	@ManyToOne
	@JoinColumn(name = "id_grupo") // Chave estrangeira na tabela Usuário
    private Grupo grupo;
	
	// Construtor padrão inicializando os campos @NotNull
    public Usuario() {
        this.status = "ativo"; // Valor padrão, pode ser ajustado
        this.perfil = "usuário"; // Valor padrão, pode ser ajustado
        this.data_nascimento = LocalDate.now(); // Definindo como a data atual
        this.foto_perfil = "default.png"; // Caminho para uma foto padrão
        this.senha = "123456"; // Senha padrão (idealmente deve ser gerada de forma segura)
        this.data_criacao = LocalDate.now();
        this.data_atualizacao = LocalDateTime.now();
    }

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_aula(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFoto_perfil() {
		return foto_perfil;
	}

	public void setFoto_perfil(String foto_perfil) {
		this.foto_perfil = foto_perfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
