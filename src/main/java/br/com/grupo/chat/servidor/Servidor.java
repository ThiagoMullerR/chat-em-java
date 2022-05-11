package br.com.grupo.chat.servidor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

	public static void main(String[] args) throws IOException {

		System.out.println("--SERVIDOR--");
		
		
		
		ServerSocket servidor = new ServerSocket(12345);

		String ip = InetAddress.getLocalHost().getHostAddress();
		
		int porta = servidor.getLocalPort();
		
		System.out.println("Servidor com o ip " + ip + " iniciado na porta: " + porta);
		System.out.println("Esperando a conexao com o cliente...");
		
		Socket cliente = servidor.accept();
		System.out.println("Nova conexao com o cliente " + cliente.getInetAddress().getHostAddress());
		
		Scanner scanner = new Scanner(cliente.getInputStream());
		
		System.out.println("\nMensagens recebidas do cliente:");
		while(scanner.hasNextLine()) {
			System.out.println(scanner.nextLine());
		}
		
		scanner.close();
		servidor.close();
		cliente.close();

	}

}
