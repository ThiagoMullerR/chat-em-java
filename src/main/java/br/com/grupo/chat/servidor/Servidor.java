package br.com.grupo.chat.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
		
		
		
		ServerSocket servidor = new ServerSocket(this.porta);
		System.out.println(
				"Servidor com o ip " 
				+ InetAddress.getLocalHost().getHostAddress() + 
				" iniciado na porta: " + porta
		);
		
		
		while(true) {
			//Aceita o cliente
			Socket cliente = servidor.accept();
			System.out.println("Esperando a conexao com o cliente...");
			
			//usuario conecta
			this.user = cliente.getInetAddress().getHostAddress();
			System.out.println("Nova conexao com o cliente " + user);
			

			// recebe  login do cliente
            InputStream fluxo = cliente.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader (fluxo));
	        System.out.println("fluxo e br");
        	String login = br.readLine();
        	System.out.println("criou login " + login);
            String password = br.readLine();
            System.out.println("criou pass " + password);
	        System.out.println("login e pass");
            System.out.println("login  = " + login);
            usuario = new Usuario(login, password);
            
            
            System.out.println("Dados do usuario - Depois do while:");
			System.out.println("Nome: " + usuario.getNome());
			System.out.println("Senha: " + usuario.getSenha());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Cargo: " + usuario.getCargo());
            

//            verifica se o login existe no banco de dados
            if(login.equals(usuario.getNome()) && password.equals(usuario.getSenha())){
                System.out.println("Welcome, " + login);
                try(Connection conexao = new ConnectionFactory().recuperarConexao()){
                	System.out.println("conexao");
    				LoginDAO loginDAO = new LoginDAO(conexao);
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
    					PrintStream ps = new PrintStream(cliente.getOutputStream());
    					this.clientes.add(ps);
    					
    					//cria tratador de cliente numa nova thread
    					TrataCliente tc = new TrataCliente(cliente.getInputStream(), this);
    					new Thread(tc).start();
    					
    					
    					
    					System.out.println("Dados do usuario:");
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
                cliente.close();
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
