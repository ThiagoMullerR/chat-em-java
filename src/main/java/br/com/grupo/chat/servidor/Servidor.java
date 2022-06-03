package br.com.grupo.chat.servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.grupo.chat.jdbc.dao.LoginDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class Servidor {
	private int porta;
	private List<PrintStream> clientes;
	private String user;
	private Usuario usuario;
	private Usuario usuarioLogado;
	
//	private List<Usuario> usuariosOnline = new ArrayList<Usuario>();
	
	public Servidor (int porta) {
		this.porta = porta;
		//this.usuario = usuario;
		this.clientes = new ArrayList<PrintStream>();
	}
	
	//inicia Socket
	public void executa() throws IOException, SQLException {
		
		
		
		ServerSocket servidor = new ServerSocket(porta);
		System.out.println(
				"Servidor com o ip " 
				+ InetAddress.getLocalHost().getHostAddress() + 
				" iniciado na porta: " + porta
		);
		
		
		while(true) {
			//Aceita o cliente
			Socket conexao = servidor.accept();
			System.out.println("Esperando a conexao com o cliente...");
			
			//usuario conecta
			this.user = conexao.getInetAddress().getHostAddress();
			System.out.println("Nova conexao com o cliente " + user);
			

			// recebe  login do cliente
            InputStream fluxo = conexao.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader (fluxo));
	       
        	String login = br.readLine();
            String password = br.readLine();
            
            usuario = new Usuario(login, password);

//            verifica se o login existe no banco de dados
            if(login.equals(usuario.getNome()) && password.equals(usuario.getSenha())){
                System.out.println("Welcome, " + login);
                try(Connection conexaoDB = new ConnectionFactory().recuperarConexao()){
                	System.out.println("conexao");
    				LoginDAO loginDAO = new LoginDAO(conexaoDB);
    				System.out.println("loginDAO");
    				this.usuarioLogado = loginDAO.logar(usuario);
    				
    				if(usuarioLogado != null) { //equals ou != null?
    					System.out.println(usuarioLogado.getNome() + " logou!");
//    					usuariosOnline.add(usuarioLogado);
    					// aqui o servidor deve enviar objeto usuario para o cliente e permitir a conexao logada
    					
    					
//    					//envia objeto
//    					OutputStream outputStream = cliente.getOutputStream();
//    					
//    					ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//    					System.out.println("enviando UsuarioLogado...");
//    			        objectOutputStream.writeObject(usuarioLogado);
//    			        System.out.println("UsuarioLogado enviado para o cliente!");
//    					
//    					
//    					System.out.println("enviando UsuarioLogado...");
//						ObjectOutputStream outStream = new ObjectOutputStream(cliente.getOutputStream());
//    			        ObjectInputStream objectInputStream = new ObjectInputStream(cliente.getInputStream());
//    			        while(outStream != null){
//
//        			        outStream.writeObject(usuarioLogado);
//    			        }
//						System.out.println("carregando..");
//    					
//    			        System.out.println("UsuarioLogado enviado para o cliente!");
    					
    					
    					
    					//Add saida do cliente(teclado) na lista que vai ser exibida no cliente
    					PrintStream ps = new PrintStream(conexao.getOutputStream());
    					this.clientes.add(ps);
    					
    					//cria tratador de cliente numa nova thread
    					//TrataCliente tc = new TrataCliente(conexao.getInputStream(), this);
    					//new Thread(tc).start();
    					
    					conexaoCliente novaConexao = new conexaoCliente(conexao);
    					new Thread(novaConexao).start();
    					
    					
    					
    					System.out.println("Dados do usuario recebido:");
    					System.out.println("Nome: " + usuario.getNome());
    					System.out.println("Senha: " + usuario.getSenha());
    					System.out.println("Email: " + usuario.getEmail());
    					System.out.println("Cargo: " + usuario.getCargo());
    					System.out.println("-------------------------");
    					System.out.println("Dados do usuarioLogado (DAO):");
    					System.out.println("Nome: " + usuarioLogado.getNome());
    					System.out.println("Senha: " + usuarioLogado.getSenha());
    					System.out.println("Email: " + usuarioLogado.getEmail());
    					System.out.println("Cargo: " + usuarioLogado.getCargo());
    					
    					
    					//new Cliente("ip.txt", 12345).executa(usuario);
    					//return true;
    				} else {
    					System.out.println("Erro - usuario ou senha invalidas!");
    					
    				}
    				
    			}
            }else{
                System.out.println("erro de autenticacao");
                conexao.close();
            }
			
		}
	}
	
	public void distribuiMensagem(String msg) {
		//envia msg para todos
		for (PrintStream cliente : this.clientes) {
			cliente.println(usuarioLogado.getNome() + " (" + user + "):");
			cliente.println(msg);
		}
	}
}




// lucas

class transferirArquivo implements Runnable{
	
	String nomearquivo = null;
	int bytearq = 0;
	String uuid = null;		
	String caminho = "/mnt/data/Desktop/KDE/javafiletest/servidor/";
	ObjectOutputStream saida = null;

	transferirArquivo(MensagemRequisitarArquivo msg, ObjectOutputStream saida){
		this.caminho = this.caminho + msg.uuid;
		this.saida = saida;
		this.nomearquivo = msg.uuid;
		this.uuid = msg.uuid;
	}


		@Override
		public void run() {

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
					Mensagem msg = new MensagemTransferencia("servidor", nomearquivo, b, uuid);
//					System.out.println(msg);
					saida.writeObject(msg);
				}
				Mensagem msg = new MensagemTransferencia("servidor", nomearquivo, -1, uuid);
//				System.out.println(msg);
				saida.writeObject(msg);
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

class Broadcaster {

	public void broadcast(Mensagem msg, ObjectOutputStream output) {

		conexaoCliente.listaClientes.forEach((outputlista) -> {

			if (output != outputlista) {
				try {
					outputlista.writeObject(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

	}

}





// TrataCLiente?
class conexaoCliente implements Runnable {
	private Socket conexao;
	static ArrayList<ObjectOutputStream> listaClientes = new ArrayList<>();
	static GerenciadorArquivo ga = new GerenciadorArquivo("/mnt/data/Desktop/KDE/javafiletest/servidor/");
	static Broadcaster bd = new Broadcaster();
	private Mensagem msg = null;

	conexaoCliente(Socket conexao) {
		this.conexao = conexao;
//		System.out.println("construido " + conexao);
	}

	@Override
	public void run() {

		System.out.println("Nova Conexão: " + conexao);

		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(conexao.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(conexao.getOutputStream());
			listaClientes.add(saida);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			
			
			try {
				msg = (Mensagem) entrada.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			

			if (msg.getClass() == MensagemUsuario.class) {
				System.out.println(msg);
				bd.broadcast(msg, saida);
			} else if (msg.getClass() == MensagemTransferencia.class) {
				try {
					ga.Escreve((MensagemTransferencia) msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (msg.getClass() == MensagemRequisitarArquivo.class) {
				transferirArquivo ta = new transferirArquivo((MensagemRequisitarArquivo) msg, saida);
				new Thread(ta).start();
			}

//			System.out.println(msg);
		}

	}
}


