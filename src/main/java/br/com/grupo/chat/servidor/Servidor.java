package br.com.grupo.chat.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Servidor {
	
	private int porta;
	private List<PrintStream> clientes;
	private String user;
//	private Usuario usuario;
	
	public Servidor (int porta) {
		this.porta = porta;
		//this.usuario = usuario;
		this.clientes = new ArrayList<PrintStream>();
	}
	
	public void executa() throws IOException {
		ServerSocket servidor = new ServerSocket(this.porta);
		System.out.println(
				"Servidor com o ip " 
				+ InetAddress.getLocalHost().getHostAddress() + 
				" iniciado na porta: " + porta
		);
		
		while(true) {
			System.out.println("Esperando a conexao com o cliente...");
			
			//Aceita o cliente
			Socket cliente = servidor.accept();
			
			this.user = cliente.getInetAddress().getHostAddress();
			
			System.out.println("Nova conexao com o cliente " + user);
			
			//Add saida do cliente a lista
			PrintStream ps = new PrintStream(cliente.getOutputStream());
			this.clientes.add(ps);
			
			//cria tratador de cliente numa nova thread
			TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
			new Thread(tc).start();
		}
	}
	
	public void distribuiMensagem(String msg) {
		//envia msg para todos
		for (PrintStream cliente : this.clientes) {
			
			cliente.println(user + ":");
			cliente.println(msg + "\n");
		}
	}
}
