package br.com.grupo.chat.servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.UUID;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Cliente {
	private Usuario usuario;
	private String host;
	private int porta;
	private Usuario usuarioLogado;
	private String line;
	
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
		Socket conexao = new Socket(host, porta);
			if(conexao != null) {
				System.out.println("O cliente se conectou ao servidor!");
				
				
				OutputStream fluxo = conexao.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(fluxo);

				//login
				osw.write(usuario.getNome() + System.lineSeparator());
				osw.flush();
				osw.write(usuario.getSenha() + System.lineSeparator());
				osw.flush();
				osw.write("");
				
				
				// Thread para receber mensagens do servidor
				//Recebedor recebedor = new Recebedor(conexao.getInputStream());

				// thread para enviar
				//  -----------
				
				//thread para receber
				//new Thread(recebedor).start();
				
				//le as msgs do teclado e envia para o servidor
				//Scanner teclado = new Scanner(System.in);

				// Precisa?
				//PrintStream saida = new PrintStream(conexao.getOutputStream());
			
				Scanner teclado = new Scanner(System.in);
				
				ER.conexao = conexao;
				ER.nome = usuario.getNome();
				ER.teclado = teclado;
				
				
				
				
				new Thread(new Enviar()).start();
				new Thread(new Receber()).start();

				
//				while(teclado.hasNext()) {
//					teclado.next();
//				}
////				teclado.nextLine();
//				teclado.nextLine();
//				teclado.nextLine();
//				teclado.nextLine();
				
				
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
//				System.out.println("\nEnviar mensagem para o servidor:");
//				while(teclado.hasNextLine()) {
//					//saida.println(teclado.nextLine());
//				}
				
			}
			
		//Scanner usuarioScanner = new Scanner((Readable) usuario);
		//Scanner nome = new Scanner((Readable) usuario);
		
	}
	// captura a mensagem escrita na interface grafica e passa para o cliente
		public void capturaTexto(String mensagem) {
		  	
		  	new Enviar().run(mensagem);
		}
}



// Lucas
abstract class ER implements Runnable {

	static Socket conexao;
	static String nome;
	static Scanner teclado;

	public void enviar(Mensagem msg, ObjectOutputStream saida) {
		try {
//			System.out.println(msg);
			saida.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class EnviarArquivo extends ER {

	ObjectOutputStream saida = null;
	String caminho = null;
	String nomearq = null;

	EnviarArquivo(ObjectOutputStream saida, String caminho, String nomearq) {
		this.saida = saida;
		this.caminho = caminho;
		this.nomearq = nomearq;
	}

	@Override
	public void run() {

		String uuid = UUID.randomUUID().toString();

		File fi = new File(caminho);
		FileInputStream input = null;
		try {
			input = new FileInputStream(fi);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int b = 0;

		try {
			while (input.available() > 0) {

				b = input.read();
				Mensagem msg = new MensagemTransferencia(ER.nome, nomearq, b, uuid);
//				System.out.println(msg);
				enviar(msg, saida);
			}
			Mensagem msg = new MensagemTransferencia(ER.nome, nomearq, -1, uuid);
//			System.out.println(msg);
			enviar(msg, saida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class ReceberArquivo extends ER{
	
	String uuid = null;
	ObjectOutputStream saida = null;
	
	ReceberArquivo(String uuid, ObjectOutputStream saida){
		this.uuid = uuid;
		this.saida = saida;
	}

	@Override
	public void run() {
		MensagemRequisitarArquivo msg = new MensagemRequisitarArquivo(uuid);
		enviar(msg, saida);
		
		
		
	}
}

class Enviar extends ER {

	public void run(String line) {

		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(ER.conexao.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		System.out.println("Enviar criado com " + ER.conexao);
		System.out.println("Conectado");

//		PrintStream saida = null;
//		try {
//			saida = new PrintStream(ER.conexao.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		while (ER.teclado.hasNextLine()) {
			//String line = ER.teclado.nextLine();

			try {
				if (line.charAt(0) == '/') {
					String[] caminho;
					String nomearquivo;
					caminho = line.split("/");
					nomearquivo = caminho[caminho.length - 1];

					EnviarArquivo ea = new EnviarArquivo(saida, line, nomearquivo);
					new Thread(ea).start();
					
				} else if(line.charAt(0) == 'd') {
					String[] linha;
					String uuid;
					linha = line.split(",");
					uuid = linha[1];
					
					ReceberArquivo ra = new ReceberArquivo(uuid,saida);
					new Thread(ra).start();
			} else {
				Mensagem msg = new MensagemUsuario(ER.nome, line);
				enviar(msg, saida);
			}

			
				
			} catch(Exception e){
				
				
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

class Receber extends ER {
	private Mensagem msg = null;

	@Override
	public void run() {
		
		GerenciadorArquivo ga = new GerenciadorArquivo("/mnt/data/Desktop/KDE/javafiletest/cliente/");

		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(ER.conexao.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			


				try {
					msg = (Mensagem) entrada.readObject();
					
					if (msg.getClass() == MensagemUsuario.class) {
						System.out.println(msg);
					} else if (msg.getClass() == MensagemListaArquivos.class) {
//						System.out.println(msg);
					} else if (msg.getClass() == MensagemTransferencia.class) {
//						System.out.println(msg);
						
						ga.Escreve((MensagemTransferencia) msg);
						
					}
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	
}




















