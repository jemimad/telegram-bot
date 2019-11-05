package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufrn.edu.imd.lpii.dorabot.model.Bem;

public class BemDAO extends AbstractDAO {

	public BemDAO() {
		super();
	}
	
	public boolean inserir(Bem b) {
		int n = 0;

		try {
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO bem (codigo, nome, descricao, id_localizacao, id_categoria) values (?, ?, ?, (SELECT id FROM localizacao WHERE nome = ?), (SELECT id FROM categoria WHERE codigo = ?)))");

			stmt.setString(1, b.getCodigo());
			stmt.setString(2, b.getNome());
			stmt.setString(3, b.getDescricao());
			stmt.setString(4, b.getLocalizacao().getNome());
			stmt.setString(5, b.getCategoria().getCodigo());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			e.printStackTrace();
		}

		return n == 1;
	}

}