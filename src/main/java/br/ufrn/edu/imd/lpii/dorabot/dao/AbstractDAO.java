package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
<<<<<<< HEAD
 * Classe respons�vel pela conex�o com o banco de dados.
=======
 * Classe respons�vel pela conex�o com o banco de dados.
>>>>>>> branch 'master' of https://github.com/jemimad/telegram-bot.git
 */
public abstract class AbstractDAO {

	protected Connection conexao;
	
	/**
	 * Inicia a conex�o com o banco.
	 */
	public AbstractDAO() {
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/ARQUIVO?useSSL=true&useTimezone=true&serverTimezone=UTC", "USER", "PASSWORD");
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar ao banco de dados: \n\t" + e);
		}
	}
	
	/** 
	 * Finaliza a conex�o com o banco.
	 */
	public void fechar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao finalizar conexão com banco de dados: \n\t" + e);
		}
	}
	
}
