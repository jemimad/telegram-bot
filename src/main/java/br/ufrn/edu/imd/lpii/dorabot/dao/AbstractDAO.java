package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO {

	protected Connection conexao;
	
	public AbstractDAO() {
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/ARQUIVO?useSSL=true", "USER", "PASSWORD");
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar ao banco de dados: \n\t" + e);
		}
	}

	public void fechar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao finalizar conexão com banco de dados: \n\t" + e);
		}
	}
	
}
