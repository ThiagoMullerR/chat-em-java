package br.com.grupo.chat.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.grupo.chat.jdbc.modelo.Registra;

public class RegistraDAO {
	
	private Connection conexao;
	
	public RegistraDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public void registra(Registra usuario) throws SQLException {
		
		String sql = "INSERT INTO USUARIOS (nome, senha, descricao) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstm = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pstm.setString(1, usuario.getNome());
			pstm.setString(2, usuario.getSenha());
			pstm.setString(3, usuario.getDescricao());
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getGeneratedKeys()){
				while(rst.next()) {
					usuario.setId(rst.getInt(1));
				}
			}
		}
	}
}
