package br.com.grupo.chat.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
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
		
		final var stringConeection = new StringBuilder()
		.append("jdbc:mysql://")
		.append(ip)
		.append("/")
		.append(db)
		.append("?useTimezone=true&serverTimezone=UTC")
		.toString();
		
		comboPooledDataSource.setJdbcUrl(stringConeection);
		comboPooledDataSource.setUser(user);
		comboPooledDataSource.setPassword(password);
		
		comboPooledDataSource.setMaxPoolSize(maxPoolSize);
		
		this.dataSource = comboPooledDataSource;
		
	}
	
	public Connection recuperarConexao() throws SQLException{
		return this.dataSource.getConnection();
	}
	
	
	
	
	
}
