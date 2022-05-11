package br.com.grupo.chat.jdbc.modelo;

public class Login {
	private String nome, senha;
	
	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public Login(String nome, String senha) {
		this.nome = nome;
		this.senha = senha;
	}
}
