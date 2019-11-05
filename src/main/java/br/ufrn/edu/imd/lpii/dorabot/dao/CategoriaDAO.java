package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufrn.edu.imd.lpii.dorabot.model.Categoria;

public class CategoriaDAO extends AbstractDAO {

	public CategoriaDAO() {
		super();
	}
	
	public boolean inserir(Categoria c) {
		int n = 0;

		try {
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO categoria (codigo, nome, descricao) values (?, ?, ?)");

			stmt.setString(1, c.getCodigo());
			stmt.setString(2, c.getNome());
			stmt.setString(3, c.getDescricao());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			e.printStackTrace();
		}

		return n == 1;
	}
	
}