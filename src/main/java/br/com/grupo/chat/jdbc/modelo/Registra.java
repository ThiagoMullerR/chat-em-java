package br.com.grupo.chat.jdbc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registra {
	
	
	private Connection conexao;

	private Integer id;
	private String nome;
	private String senha;
	private String descricao;
	
	public Registra(String nome, String senha, String descricao) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.descricao = descricao;
	}
	
	

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public String getSenha() {
		// envia senha criptografada
		return senha;
	}

	public String getDescricao() {
		return descricao;
	}

	
	public boolean autenticaUsuario() throws SQLException {
		System.out.println("Autententicando: " + nome);
		String sql = "";
		try(PreparedStatement pstm = conexao.prepareStatement(sql)){
			
		}
		return true;
	}



	public void setId(int id) {

		this.id = id;
		
	}


	
	
}
