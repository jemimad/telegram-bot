package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe respons·vel pela conex„o com o banco de dados.
 */
public abstract class AbstractDAO {

	protected Connection conexao;
	
	/**
	 * Inicia a conex„o com o banco.
	 */
	public AbstractDAO() {
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/ARQUIVO?useSSL=true&useTimezone=true&serverTimezone=UTC", "USER", "PASSWORD");
		} catch (SQLException e) {
			System.out.println("N√£o foi poss√≠vel conectar ao banco de dados: \n\t" + e);
		}
	}
	
	/** 
	 * Finaliza a conex„o com o banco.
	 */
	public void fechar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao finalizar conex√£o com banco de dados: \n\t" + e);
		}
	}
	
}
