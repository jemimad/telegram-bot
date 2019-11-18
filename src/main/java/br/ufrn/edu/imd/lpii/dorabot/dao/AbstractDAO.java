package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe responsável pela conexão com o banco de dados.
 */
public abstract class AbstractDAO {

	protected Connection conexao;
	
	/**
	 * Método para iniciar a conexão com o banco.
	 */
	public AbstractDAO() {
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/ARQUIVO?useSSL=true&useTimezone=true&serverTimezone=UTC", "USER", "PASSWORD");
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar ao banco de dados: \n\t" + e);
		}
	}
	
	/** 
	 * Método para finalizar a conexão com o banco.
	 */
	public void fechar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao finalizar conexão com banco de dados: \n\t" + e);
		}
	}
	
}
