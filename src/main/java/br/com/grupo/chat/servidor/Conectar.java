package br.com.grupo.chat.servidor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.dao.LoginDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Conectar {
	public boolean inicia(Usuario usuario) throws SQLException, IOException {
		System.out.println("--CLIENTE--");
		
		try(Connection conexao = new ConnectionFactory().recuperarConexao()){
			LoginDAO loginDAO = new LoginDAO(conexao);
			Usuario usuarioLogado = loginDAO.logar(usuario);
			
			if(usuarioLogado != null) {
				System.out.println(usuarioLogado.getNome() + " logou!");
				new Cliente("ip.txt", 12345).executa();
				return true;
			} else {
				System.out.println("Erro - usuario ou senha invalidas!");
				return false;
			}
			
		}
	}
}
