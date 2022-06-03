package br.com.grupo.chat.chatInterface;

import java.io.IOException;
import java.sql.SQLException;

public class TestaTela {

	public static void main(String[] args) {
		
		try {
			new TelaChat().setVisible(true);
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

}
