package br.com.grupo.chat.jdbc;

import java.sql.SQLException;

import br.com.grupo.chat.jdbc.dao.RegistraDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Registra;

public class TestaCadastro {

	public static void main(String[] args) throws SQLException {

		final var usuario = new Registra("Juliana", "123", "Bom dia");

		try(final var conexao = new ConnectionFactory().recuperarConexao()){
			final var registraDAO = new RegistraDAO(conexao);
			
			registraDAO.registra(usuario);
		}
		System.out.println("Usuario " + usuario.getNome() + " criado!");
	}
}
