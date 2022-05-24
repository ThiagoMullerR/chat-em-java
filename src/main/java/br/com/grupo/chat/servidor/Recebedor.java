package br.com.grupo.chat.servidor;

import java.io.InputStream;
import java.util.Scanner;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Recebedor implements Runnable{
	
	private InputStream servidor;

	public Recebedor(InputStream servidor) {
		this.servidor = servidor;
	}

	public void run() {
		// Recebe msgs do servidor e imprime na tela
		Scanner scanner = new Scanner(this.servidor);
		while(scanner.hasNextLine()) {
			System.out.println(scanner.nextLine());
		}
		
	}

}
