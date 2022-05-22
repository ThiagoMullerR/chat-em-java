package br.com.grupo.chat.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.factory.ConnectionFactory;

public class TestaDB {

	public static void main(String[] args) throws SQLException {
		

		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.recuperarConexao();
		
		PreparedStatement stm = connection.prepareStatement("SELECT id, nome, email, cargo, criado_em FROM USUARIOS");
		stm.execute();

		ResultSet rst = stm.getResultSet();
		
		while(rst.next()) {
			Integer id = rst.getInt("id");
			String nome = rst.getString("nome");
			String email = rst.getString("email");
			String cargo = rst.getString("cargo");
			String criadoEm = rst.getString("criado_em");
			
			System.out.println("Listando usuarios: ");
			System.out.println(id + " | " + nome + " | " + email + " | " + cargo + " | " + criadoEm );
		}
		
		connection.close();

	}

}
