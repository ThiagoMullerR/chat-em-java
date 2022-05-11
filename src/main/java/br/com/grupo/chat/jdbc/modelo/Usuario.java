package br.com.grupo.chat.jdbc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Usuario {
	
	
	private Connection conexao;

	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private String descricao;
	
	public Usuario(String nome, String senha) {
		super();
		this.nome = nome;
		this.senha = senha;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setSenha(String senha) {
		//criptografa
		this.senha = senha;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		// envia senha criptografada
		return null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean autenticaUsuario() throws SQLException {
		System.out.println("Autententicando: " + nome);
		String sql = "";
		try(PreparedStatement pstm = conexao.prepareStatement(sql)){
			
		}
		return true;
	}


	
	
}
