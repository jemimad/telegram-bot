package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

public class LocalizacaoDAO extends AbstractDAO {

	public LocalizacaoDAO() {
		super();
	}
	
	public boolean inserir(Localizacao l) {
		int n = 0;

		try {
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO localizacao (nome, descricao) values (?, ?)");

			stmt.setString(1, l.getNome());
			stmt.setString(2, l.getDescricao());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			e.printStackTrace();
		}

		return n == 1;
	}
	
}