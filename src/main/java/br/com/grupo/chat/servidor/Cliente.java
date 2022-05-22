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

	
	
	
	
	private String host;
	private int porta;
	
	public Cliente(String arquivoComOIp, int porta) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(arquivoComOIp)
						)
				);
		this.host = br.readLine();
		this.porta = porta;
	}
	
	public void executa() throws UnknownHostException, IOException {
		
		System.out.println("Iniciando conexao com " + host + " na porta " + porta);
		
		Socket cliente = new Socket(this.host, this.porta);
		System.out.println("O cliente se conectou ao servidor!");
		
		// Thread para receber mensagens do servidor
		Recebedor recebedor = new Recebedor(cliente.getInputStream());
		new Thread(recebedor).start();
		
		//le as msgs do teclado e envia para o servidor
		Scanner teclado = new Scanner(System.in);
		PrintStream saida = new PrintStream(cliente.getOutputStream());
		System.out.println("\nEnviar mensagem para o servidor:");
		while(teclado.hasNextLine()) {
			saida.println(teclado.nextLine());
		}
	}
}
