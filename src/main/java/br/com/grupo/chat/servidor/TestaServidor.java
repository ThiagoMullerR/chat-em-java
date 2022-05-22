package br.com.grupo.chat.servidor;

import java.io.IOException;

public class TestaServidor {
	public static void main(String[] args) throws IOException {

		System.out.println("--SERVIDOR--");
		new Servidor(12345).executa();
		
	}
}
