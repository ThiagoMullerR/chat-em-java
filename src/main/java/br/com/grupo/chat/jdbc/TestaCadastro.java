package br.com.grupo.chat.jdbc;

import java.sql.SQLException;

import br.com.grupo.chat.jdbc.dao.RegistraDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class TestaCadastro {

	public static void main(String[] args) throws SQLException {

		final var usuario = new Usuario("Juliana", "123");

		try(final var conexao = new ConnectionFactory().recuperarConexao()){
			final var registraDAO = new RegistraDAO(conexao);
			
			registraDAO.registra(usuario);
		}
		System.out.println("Usuario " + usuario.getNome() + " criado!");
	}
}
