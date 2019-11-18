package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.edu.imd.lpii.dorabot.model.Categoria;


/**
 * Classe responsável por requisitar ao banco as consultas necessárias para os objetos da classe Categoria.
 */

public class CategoriaDAO extends AbstractDAO {

	public CategoriaDAO() {
		super();
	}
	
	/**
	 * Método para cadastro de categorias.
	 * @param c Recebe como parâmetro o objeto c do tipo Categoria a ser cadastrado.
	 * @return Retorna 1 quando a categoria for cadastrada com sucesso e 0 quando houver algum erro.
	 */
	public boolean inserir(Categoria c) {
		int n = 0;

		try {
			PreparedStatement stmt = conexao.prepareStatement("INSERT INTO categoria (nome, descricao) values (?, ?)");

			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getDescricao());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			e.printStackTrace();
		}

		return n == 1;
	}
	
	/**
	 * Método para busca de categoria a partir de um id.
	 * @param id Recebe como parâmetro o id do tipo inteiro.
	 * @return Retorna a categoria referente.
	 */
	public Categoria buscarPorID(int id) {
		Categoria c = null;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM categoria WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				c = new Categoria();

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setDescricao(rs.getString("descricao"));

			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			c = null;
			System.out.println("Erro: " + e);
		}

		return c;
	}
	
	/**
	 * Método para listagem de todas as categorias.
	 * @return Retorna uma lista de categorias.
	 */	
	public List<Categoria> listar() {
		List<Categoria> lista = new ArrayList<Categoria>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM categoria");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Categoria c = new Categoria();

				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setDescricao(rs.getString("descricao"));

				lista.add(c);
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
}