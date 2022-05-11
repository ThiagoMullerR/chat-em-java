package br.com.grupo.chat.servidor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		System.out.println("--CLIENTE--");
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("ip.txt")
						)
				);
		
		String ip = br.readLine();
		int porta = 12345;
		
		System.out.println("Iniciando conexao com " + ip + " na porta " + porta);
		
		Socket cliente = new Socket(ip, porta);
		System.out.println("O cliente se conectou com o servidor!");
		
		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		
		System.out.println("\nEnviar mensagem para o servidor:");
		while(teclado.hasNextLine()) {
			saida.println(teclado.nextLine());
		}

		saida.close();
		teclado.close();
		cliente.close();
		
		br.close();
	}

}
