package br.com.grupo.chat.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.dao.LoginDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Login;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class TestaLogin {

	public static void main(String[] args) throws SQLException {

		Login usuario = new Login("Juliana", "123");
		
		try(Connection conexao = new ConnectionFactory().recuperarConexao()){
			LoginDAO loginDAO = new LoginDAO(conexao);
			Usuario usuarioLogado = loginDAO.logar(usuario); 
			if(usuarioLogado != null) {
				System.out.println(usuarioLogado.getNome() + " logou!");
			} else {
				System.out.println("Erro - usuario ou senha errada!");
			}
			
		}
	}
		
}
