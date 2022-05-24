package br.com.grupo.chat.chatInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.grupo.chat.jdbc.dao.RegistraDAO;
import br.com.grupo.chat.jdbc.factory.ConnectionFactory;
import br.com.grupo.chat.jdbc.modelo.Usuario;
import br.com.grupo.chat.servidor.Cliente;

public class TelaChat extends JFrame implements ActionListener {
	
	private JMenuBar mnBarra;
	private JMenu mnMenu;
	private JMenuItem miChat, miCadastro;
	
	
	private JPanel cadastro;
	
	private JButton botCadastrar;
	private JButton botLimparTudo;
	
	private JPanel preencher;
	
	private JLabel labCadastro;
	private JLabel labNome;
	private JLabel labEmail;
	private JLabel labCadSenha;
	private JLabel labConfSenha;
	private JLabel labCargo;
	private JLabel labBanner;
	private JTextField texNome;
	private JTextField texEmail;
	private JPasswordField texCadSenha;
	private JPasswordField texConfSenha;
	private JTextField texCargo;
	private JTextField texBanner;
	private ImageIcon bannerCetesb;
	
	
	private JPanel chatLogin;
	
	private JPanel login;
	
	private JLabel labUser;
	private JLabel labSenha;
	private JLabel labLogin;
	private JLabel logo;
	private JTextField texUser;
	private JPasswordField texSenha;
	private JButton botConect;
	private JButton botSair;
	private ImageIcon logoCetesb;
	
	private JPanel chat;
	
	private JScrollPane scRolagemVis;
	private JScrollPane scRolagemEsc;
	private JTextArea txtVisor;
	private JTextArea txtEscreve;
	private JButton botLimpar;
	private JButton botEnviar;
	
	
	public TelaChat() {
		super();
		configurarFrame();
		configuraMenu();
		configurarPainelChatLogin();
		configurarPainelCadastro( );
		add(cadastro);
		add(chatLogin);
		chatLogin.setVisible(false);
		
	}
	
	private void configuraMenu() {
		
		miCadastro = new JMenuItem("Cadastro");
		miChat = new JMenuItem("Chat");
		
		mnMenu = new JMenu("MENU");
		mnMenu.setFont(new Font(null, Font.BOLD, 14));
		mnMenu.add(miCadastro);
		mnMenu.add(miChat);
				
		mnBarra = new JMenuBar();
		mnBarra.add(mnMenu);
		setJMenuBar(mnBarra);
		
		miCadastro.addActionListener(this);
		miChat.addActionListener(this);
		
	}
	
	private void configurarPainelCadastro() {
		
		cadastro = new JPanel();
		cadastro.setLayout(null);
		cadastro.setBounds(0, 0, 600, 600);
				
		configurarPainelPreencher();
		
		labCadastro = new JLabel("CADASTRO DE USUÁRIO");	
		labCadastro.setFont(new Font(null, Font.BOLD, 20));
		labBanner = new JLabel(bannerCetesb);
		botLimparTudo = new JButton("Limpar");
		botLimparTudo.setFont(new Font(null, Font.BOLD, 13));
		botCadastrar = new JButton("Cadastrar");
		botCadastrar.setFont(new Font(null, Font.BOLD, 13));
		bannerCetesb = new ImageIcon("./img/BannerCetesb.png");
		
		labBanner.setBounds(0, 0, 600, 120);
		labCadastro.setBounds(170, 150, 300, 50);
		botCadastrar.setBounds(95, 490, 150, 28);
		botLimparTudo.setBounds(345, 490, 150, 28);
		
		cadastro.add(labBanner);
		cadastro.add(labCadastro);
		cadastro.add(botLimparTudo);
		cadastro.add(botCadastrar);
		cadastro.add(preencher);
		
		// Adicionando evento aos bot�es
		botCadastrar.addActionListener(this);
		botLimparTudo.addActionListener(this);
		
		labBanner.setIcon(bannerCetesb);
	}
	
	
	private void configurarPainelPreencher() {
		
		preencher = new JPanel();
		preencher.setLayout(null);
		preencher.setBounds(75, 200, 440, 260);
		//preencher.setBackground(new Color(56,95,162));
		
		configurarDadosCadastro();
		
		preencher.add(labNome);
		preencher.add(labEmail);
		preencher.add(labCargo);
		preencher.add(labCadSenha);
		preencher.add(labConfSenha);		
		preencher.add(texNome);
		preencher.add(texEmail);
		preencher.add(texCargo);
		preencher.add(texCadSenha);
		preencher.add(texConfSenha);
		
		preencher.setBorder(BorderFactory.createLoweredBevelBorder());
		
	}
	
	private void configurarDadosCadastro() {
		
		labNome = new JLabel("Nome:");
		labNome.setFont(new Font(null, Font.BOLD, 13));
		labEmail = new JLabel("E-mail:");
		labEmail.setFont(new Font(null, Font.BOLD, 13));
		labCargo = new JLabel("Cargo:");
		labCargo.setFont(new Font(null, Font.BOLD, 13));
		labCadSenha = new JLabel("Senha:");
		labCadSenha.setFont(new Font(null, Font.BOLD, 13));
		labConfSenha = new JLabel("Confirmar senha:");
		labConfSenha.setFont(new Font(null, Font.BOLD, 13));
		texNome = new JTextField();
		texEmail = new JTextField();
		texCargo = new JTextField();
		texCadSenha = new JPasswordField();
		texConfSenha = new JPasswordField();

		
		// Dimensionando os Componentes
		labNome.setBounds(90, 20, 50, 25);
		labEmail.setBounds(88, 70, 50, 25);
		labCargo.setBounds(88, 120, 50, 25);
		labCadSenha.setBounds(88, 170, 50, 25);
		labConfSenha.setBounds(24, 220, 130, 25);
		texNome.setBounds(150, 20, 250, 25);
		texEmail.setBounds(150, 70, 250, 25);
		texCargo.setBounds(150, 120, 250, 25);
		texCadSenha.setBounds(150, 170, 250, 25);
		texConfSenha.setBounds(150, 220, 250, 25);
											
	}
	
	private void configurarPainelChatLogin() {
		
		chatLogin = new JPanel();
		chatLogin.setLayout(null);
		chatLogin.setBounds(0, 0, 600, 600);
		
		configurarPainelLogin();
		configurarPainelChat();
		
		chatLogin.add(login);
		chatLogin.add(chat);	
		
	}
	
	
	private void configurarPainelChat() {
		
		chat = new JPanel();
		chat.setLayout(null);
		chat.setBounds(10, 130, 400, 420);
				
		configurarDadosChat();
		
		chat.add(scRolagemVis);
		chat.add(scRolagemEsc);
		chat.add(botLimpar);
		chat.add(botEnviar);
		chat.setBorder(BorderFactory.createTitledBorder("CHAT"));
		
	}
	
	private void configurarDadosChat() {
		txtVisor = new JTextArea("");
		txtEscreve = new JTextArea("");
		botLimpar = new JButton("Limpar");
		botEnviar = new JButton("Enviar");
		scRolagemEsc = new JScrollPane(txtEscreve);
		scRolagemVis = new JScrollPane(txtVisor);

		
		// Dimensionando os Componentes
		scRolagemVis.setBounds(20, 25, 360, 200);
		scRolagemEsc.setBounds(20, 240, 360, 130);
		botLimpar.setBounds(170, 380, 100, 25);
		botEnviar.setBounds(280, 380, 100, 25);
		
		// Desabilitando os bot�es e caixas de texto antes de conectar
		botEnviar.setEnabled(false);
		botLimpar.setEnabled(false);
		txtVisor.setEnabled(false);
		txtEscreve.setEnabled(false);
		
		// Adicionando evento aos bot�es
		botLimpar.addActionListener(this);
		botEnviar.addActionListener(this);
	}
	
	private void configurarPainelLogin() {
		
		login = new JPanel();
		login.setLayout(null);
		login.setBounds(0, 0, 600, 120);
		login.setBackground(new Color(56,95,162));
		
		configurarDadosLogin();
		login.add(labUser);
		login.add(labSenha);
		login.add(labLogin);
		login.add(texUser);
		login.add(texSenha);
		login.add(botConect);
		login.add(botSair);
		login.add(logo);
	}
	
	private void configurarDadosLogin() {
		labUser = new JLabel("Usuário:");
		labSenha = new JLabel("Senha:");
		labLogin = new JLabel("LOGIN:");
		logo = new JLabel(logoCetesb);
		texUser = new JTextField();
		texSenha = new JPasswordField();
		botConect = new JButton("Conectar");
		botSair = new JButton("Sair");
		logoCetesb = new ImageIcon("./img/LogoCetesb.png");
		
		
		
		
		
		
		// Dimensionando os Componentes
		labUser.setBounds(40, 40, 50, 25);
		labSenha.setBounds(40, 70, 50, 25);
		labLogin.setBounds(20, 10, 80, 25);
		texUser.setBounds(100, 40, 170, 25);
		texSenha.setBounds(100, 70, 170, 25);
		botConect.setBounds(290, 40, 100, 25);
		botSair.setBounds(290, 70, 100, 25);
		logo.setBounds(450, 10, 100, 100);
		
		// Adicionando evento aos bot�es
		botConect.addActionListener(this);
		botSair.addActionListener(this);
		
		// Desabilitando os bot�es antes de conectar
		botSair.setEnabled(false);
		
		
		logo.setIcon(logoCetesb);
		
		labUser.setForeground(Color.white);
		labSenha.setForeground(Color.white);
		labLogin.setForeground(Color.white);
		
	}
		
	private void configurarFrame() {
		setTitle("Comunicação Interna - CETESB");
		setSize(new Dimension(600, 620));
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	// A��es dos bot�es
	public void actionPerformed(ActionEvent ae) {
		
		Object obj = ae.getSource();
				
		//Menu
		
		if (obj.equals(miCadastro)) {
			
			cadastro.setVisible(true);
			chatLogin.setVisible(false);
			
		}
		
		if (obj.equals(miChat)) {
			
			chatLogin.setVisible(true);
			cadastro.setVisible(false);
			
		}
		
		
		// Inicia conexao ao clicar em "conectar"
		botConect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!texUser.getText().equals("") && !texSenha.getText().equals("")) {
					try {
						 try {
							new Cliente("ip.txt", 12345).executa(new Usuario(texUser.getText(), texSenha.getText()));
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 
//						if(!conectou) {
//							JOptionPane.showMessageDialog(null, "Nome ou senha invalidos!");
//						}Ø
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nome e Senha devem ser informados.");
					
				}
					
				
			}
		});
		
		
		
		
		//Painel de Login
		
		if (obj.equals(botConect)) {
			
			//Verificar se o usu�rio existe e se a senha est� correta e fazer a conex�o com o servidor
			
			//Habilitando os bot�es depois que a conex�o � feita
			botEnviar.setEnabled(true);
			botLimpar.setEnabled(true);
			txtVisor.setEnabled(true);
			txtEscreve.setEnabled(true);
			botSair.setEnabled(true);
			
			//Desabilitando o bot�o de conex�o depois que a conex�o � feita
			botConect.setEnabled(false);
			
		}	
		
		if (obj.equals(botSair)) {
			
			//Finalizar a conex�o
			
			//Desbilitando os bot�es depois que a conex�o � desfeita
			botEnviar.setEnabled(false);
			botLimpar.setEnabled(false);
			txtVisor.setEnabled(false);
			txtEscreve.setEnabled(false);
			botSair.setEnabled(false);
			
			//Habilitando o bot�o de conex�o depois de desconectar
			botConect.setEnabled(true);
			
			//Apagando o conte�do do chat depois de sair da conex�o
			txtEscreve.setText("");
			txtVisor.setText("");
			
		}
		
		//Painel do Chat
		
		if (obj.equals(botLimpar)) {
			
			txtEscreve.setText("");
			
		}
		
		if (obj.equals(botEnviar)) {
			
			//Enviar a mensagem pro servidor
			
			
			//Apagar a mensagem da �rea de escrita no momento do envio
			txtEscreve.setText("");
			
		}
		
		
		//Tela de Cadastro
		
		if (obj.equals(botLimparTudo)) {
			
			texNome.setText("");
			texEmail.setText("");
			texCadSenha.setText("");
			texConfSenha.setText("");
			texCargo.setText("");
			
		}

		if (obj.equals(botCadastrar)) {
			
			final String nome = texNome.getText();
			final String email = texEmail.getText();
			final String cadSenha = texCadSenha.getText();
			final String confSenha = texConfSenha.getText();
			final String cargo = texCargo.getText();
			
			if (cadSenha.equals(confSenha)) {
				
				//Peguei do seu c�digo do TestaCadastro
				
				Usuario usuario = new Usuario(nome, cadSenha);
				usuario.setEmail(email);
				usuario.setCargo(cargo);

				try(final var conexao = new ConnectionFactory().recuperarConexao()){
					final var registraDAO = new RegistraDAO(conexao);
					
					registraDAO.registra(usuario);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "Usuário " + usuario.getNome() + " cadastrado com sucesso!", "CONEXÃO", JOptionPane.INFORMATION_MESSAGE);
				
				texNome.setText("");
				texEmail.setText("");
				texCadSenha.setText("");
				texConfSenha.setText("");
				texCargo.setText("");
				
			}
			
			else {
				
				JOptionPane.showMessageDialog(null, "Senhas inseridas são diferentes!", "SENHAS DISTINTAS", JOptionPane.ERROR_MESSAGE);
				
				
			}
						
		}
	
	}
	
	
	
	public static void main(String[] args) {
		
		new TelaChat().setVisible(true);

	}
	

}