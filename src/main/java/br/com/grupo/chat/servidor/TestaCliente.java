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
		
//		Usuario usuario = new Usuario("Juliana", "123");
		Usuario usuario = new Usuario("thi", "thi");
		
		System.out.println("--CLIENTE--");
		
			try {
				new Cliente("ip.txt", 12345).executa(usuario);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}