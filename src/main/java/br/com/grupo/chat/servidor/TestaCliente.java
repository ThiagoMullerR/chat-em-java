package br.com.grupo.chat.servidor;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.dao.LoginDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class TestaCliente {
public static void main(String[] args) throws UnknownHostException, IOException, SQLException {
		
		System.out.println("--CLIENTE--");
		
		
		Usuario usuario = new Usuario("julianao", "123");
		
		try(Connection conexao = new ConnectionFactory().recuperarConexao()){
			LoginDAO loginDAO = new LoginDAO(conexao);
			Usuario usuarioLogado = loginDAO.logar(usuario);
			
			if(usuarioLogado != null) {
				System.out.println(usuarioLogado.getNome() + " logou!");
				new Cliente("ip.txt", 12345).executa();
			} else {
				System.out.println("Erro - usuario ou senha errada!");
			}
			
		}
	}
}
