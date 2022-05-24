package br.com.grupo.chat.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Entrada {
	
	
	
//	System.out.println("Iniciando conexao com " + host + " na porta " + porta);
//	
//	Socket cliente = new Socket(this.host, this.porta);
//	System.out.println("O cliente se conectou ao servidor!");
//	
//	// Thread para receber mensagens do servidor
//	Recebedor recebedor = new Recebedor(cliente.getInputStream());
//	new Thread(recebedor).start();
//	
//	//le as msgs do teclado e envia para o servidor
//	Scanner teclado = new Scanner(System.in);
//	PrintStream saida = new PrintStream(cliente.getOutputStream());
//	System.out.println("\nEnviar mensagem para o servidor:");
//	while(teclado.hasNextLine()) {
//		saida.println(teclado.nextLine());
//	}
	public boolean validaUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}
}
