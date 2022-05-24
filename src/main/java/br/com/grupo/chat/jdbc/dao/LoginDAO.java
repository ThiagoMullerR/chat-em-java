package br.com.grupo.chat.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.grupo.chat.jdbc.modelo.Usuario;

public class LoginDAO {
	
	private Connection conexao;

	public LoginDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Usuario logar(Usuario usuario) throws SQLException {
		
		System.out.println("Logando usuario: " + usuario.getNome() + " senha: " + usuario.getSenha());
		String sql = "SELECT * FROM USUARIOS WHERE NOME = ? AND SENHA = ?";
		try(PreparedStatement pstm = conexao.prepareStatement(sql)){
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getSenha());
			pstm.execute();
			
			try(ResultSet rst = pstm.getResultSet()){
				return autentica(rst);
			}
		}
	}
	
	public Usuario autentica(ResultSet rst) throws SQLException {
		
		while(rst.next()) {
			//passa restante dos parametros
			
			 Usuario usuarioLogado = new Usuario(rst.getString(2), rst.getString(3));
			 usuarioLogado.setId(rst.getInt(1));
			 usuarioLogado.setCargo(rst.getString(4));
			 usuarioLogado.setCriado_em(rst.getString(5));
			 usuarioLogado.setEmail(rst.getString(6));
			 
			 return usuarioLogado;
		}
		return null;
	}
	}
	
