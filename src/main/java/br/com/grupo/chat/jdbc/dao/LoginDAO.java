package br.com.grupo.chat.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.modelo.Login;
import br.com.grupo.chat.jdbc.modelo.Usuario;

public class LoginDAO {
	
	private Connection conexao;

	public LoginDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Usuario logar(Login login) throws SQLException {
		
		System.out.println("Logando usuario " + login.getNome());
		String sql = "SELECT NOME, SENHA FROM USUARIOS WHERE NOME = ? AND SENHA = ?";
		try(PreparedStatement pstm = conexao.prepareStatement(sql)){
			pstm.setString(1, login.getNome());
			pstm.setString(2, login.getSenha());
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				return autentica(rst);
			}
		}
	}
	
	public Usuario autentica(ResultSet rst) throws SQLException {
		
		while(rst.next()) {
			return new Usuario(rst.getString(1), rst.getString(2));
			
		}
		return null;
	}
	
	

}
