package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.edu.imd.lpii.dorabot.excecoes.NaoExiste;
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
	 * @param c Objeto do tipo categoria a ser cadastrado.
	 * @return true
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
	 * @param id Inteiro informando o id da categoria a ser buscada.
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
	 * Método para busca de categoria a partir de um nome.
	 * @param nome String informando o nome da categoria a ser buscada.
	 * @return Retorna a categoria referente.
	 * @throws NaoExiste Caso não exista uma categoria com a descrição informada.
	 */
	public Categoria buscarPorNome(String nome) throws NaoExiste {
		Categoria c = null;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM categoria WHERE nome = ?");
			stmt.setString(1, nome);
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
		
		if (c == null) {
			throw new NaoExiste("Nao existe esta categoria!");
		} else {
			return c;
		}
	}
	
	/**
	 * Método para listagem de todas as categorias.
	 * @return Retorna uma lista de categorias.
	 * @throws NaoExiste Caso não haja categorias cadastradas no banco.
	 */	
	public List<Categoria> listar() throws NaoExiste {
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
		
		if(lista.size() == 0) {
			throw new NaoExiste("Não há categorias cadastradas!");
		}else {
			return lista;
		}
	}
	
}