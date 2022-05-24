package br.com.grupo.chat.servidor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Cliente {

	
	
	
	private Usuario usuario;
	private String host;
	private int porta;
	private Usuario usuarioLogado;
	
	public Cliente(String arquivoComOIp, int porta) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(arquivoComOIp)
						)
				);
		this.host = br.readLine();
		this.porta = porta;
	}
	
	public void executa(Usuario usuario) throws UnknownHostException, IOException, ClassNotFoundException {
		this.usuario = usuario;
		System.out.println("Usuario " + usuario.getNome() + " senha: " + usuario.getSenha() + " solicitando conexao com " + host + " na porta " + porta);

		
//		Entrada entrada = new Entrada();
//		boolean ehValido = entrada.validaUsuario(usuario);
//		if(ehValido) {
//			//inicia conexao com usuario logado
//		}
		Socket cliente = new Socket(this.host, this.porta);
			if(cliente != null) {
				
				System.out.println("O cliente se conectou ao servidor!");
				OutputStream fluxo = cliente.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(fluxo);

				//login
				osw.write(usuario.getNome() + "\n" + usuario.getSenha());
				osw.flush();
				
				System.out.println("flush");
				
				// Thread para receber mensagens do servidor
				Recebedor recebedor = new Recebedor(cliente.getInputStream());
				System.out.println("recebedor");
				new Thread(recebedor).start();
				System.out.println("thread");
				System.out.println("Dados do usuario:");
				System.out.println("Nome: " + usuario.getNome());
				System.out.println("Senha: " + usuario.getSenha());
				System.out.println("Email: " + usuario.getEmail());
				System.out.println("Cargo: " + usuario.getCargo());
				
				
				
				//le as msgs do teclado e envia para o servidor
				Scanner teclado = new Scanner(System.in);
				System.out.println("scanner");
				PrintStream saida = new PrintStream(cliente.getOutputStream());
				System.out.println("saida");
//				while(teclado.hasNext()) {
//					teclado.next();
//				}
////				teclado.nextLine();
//				teclado.nextLine();
//				teclado.nextLine();
//				teclado.nextLine();
				
				System.out.println("\nEnviar mensagem para o servidor:");
				
				
				System.out.println("Dados do usuario:");
				System.out.println("Nome: " + usuario.getNome());
				System.out.println("Senha: " + usuario.getSenha());
				System.out.println("Email: " + usuario.getEmail());
				System.out.println("Cargo: " + usuario.getCargo());
				
				System.out.println("antes do while");
				
				System.out.println("Recebendo novo clienteLogado");
//				
//					ObjectOutputStream outStream = new ObjectOutputStream(cliente.getOutputStream());
//			        ObjectInputStream objectInputStream = new ObjectInputStream(cliente.getInputStream());
//			        while(objectInputStream != null) {
//				        this.usuarioLogado = (Usuario) objectInputStream.readObject();
//
////			        }
//			        
//					System.out.println("Dados do usuario:");
//					System.out.println("Nome: " + usuarioLogado.getNome());
//					System.out.println("Senha: " + usuarioLogado.getSenha());
//					System.out.println("Email: " + usuarioLogado.getEmail());
//					System.out.println("Cargo: " + usuarioLogado.getCargo());
//		        
		        
				while(teclado.hasNextLine()) {
					
					saida.println(teclado.nextLine());
				}
				
			}
			
		//Scanner usuarioScanner = new Scanner((Readable) usuario);
		//Scanner nome = new Scanner((Readable) usuario);
		
	}
}
