package br.com.grupo.chat.servidor;

import java.io.IOException;
import java.sql.SQLException;

public class TestaServidor {
	public static void main(String[] args) throws IOException, SQLException {
		
		System.out.println("--SERVIDOR--");
		new Servidor(12345).executa();
		
	}
}
