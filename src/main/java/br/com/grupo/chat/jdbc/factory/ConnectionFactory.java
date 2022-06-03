package br.com.grupo.chat.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	//Estabelece a conexao do servidor com o banco de dados
	private String 
		ip = "localhost",
		db = "chat_db",
		user = "chat",
		password = "0987654321"
	;
	
	private int maxPoolSize = 15;

	public DataSource dataSource;
	
	//Em um cenário onde diversos clientes podem acessar uma mesma aplicação simultaneamente,
	//reciclamos um conjunto de conexões de tamanho fixo ou dinâmico
	public ConnectionFactory() {
		
		// c3p0(.jar) - implementa o pool de conexões
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://"+ip+"/"+db+"?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser(user);
		comboPooledDataSource.setPassword(password);
		
		comboPooledDataSource.setMaxPoolSize(maxPoolSize);
		
		this.dataSource = comboPooledDataSource;
		
	}
	
	public Connection recuperarConexao() throws SQLException{
		return this.dataSource.getConnection();
	}
}


