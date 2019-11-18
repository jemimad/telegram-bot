package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.edu.imd.lpii.dorabot.model.Bem;
import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

public class BemDAO extends AbstractDAO {

	public BemDAO() {
		super();
	}
	
	public boolean inserir(Bem b, String nomeLoc, String nomeCat) {
		int n = 0;

		try {
			PreparedStatement stmt = conexao.prepareStatement(
					"INSERT INTO bem (nome, descricao, id_localizacao, id_categoria) values "
					+ "(?, ?, (SELECT id FROM localizacao WHERE nome = ?), (SELECT id FROM categoria WHERE nome = ?))");

			stmt.setString(1, b.getNome());
			stmt.setString(2, b.getDescricao());
			stmt.setString(3, nomeLoc);
			stmt.setString(4, nomeCat);

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			e.printStackTrace();
		}

		return n == 1;
	}
	
	public List<Bem> listar() {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bem b = new Bem();

				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setDescricao(rs.getString("descricao"));

				LocalizacaoDAO locDAO = new LocalizacaoDAO();
				b.setLocalizacao(locDAO.buscarPorID(rs.getInt("id_localizacao")));
				locDAO.fechar();
				
				CategoriaDAO catDAO = new CategoriaDAO();
				b.setCategoria(catDAO.buscarPorID(rs.getInt("id_categoria")));
				catDAO.fechar();

				lista.add(b);
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
	public List<Bem> listarPorLocalizacao(String loc) {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem WHERE id_localizacao = (SELECT id FROM localizacao WHERE nome = ?)");
			stmt.setString(1, loc);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bem b = new Bem();

				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setDescricao(rs.getString("descricao"));
				
				LocalizacaoDAO locDAO = new LocalizacaoDAO();
				b.setLocalizacao(locDAO.buscarPorID(rs.getInt("id_localizacao")));
				locDAO.fechar();
				
				CategoriaDAO catDAO = new CategoriaDAO();
				b.setCategoria(catDAO.buscarPorID(rs.getInt("id_categoria")));
				catDAO.fechar();

				lista.add(b);
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
	public Bem buscarPorCodigo(String codigo) {
		Bem b = null;

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem WHERE id = ?");
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				b = new Bem();

				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setDescricao(rs.getString("descricao"));
				
				LocalizacaoDAO locDAO = new LocalizacaoDAO();
				b.setLocalizacao(locDAO.buscarPorID(rs.getInt("id_localizacao")));
				locDAO.fechar();
				
				CategoriaDAO catDAO = new CategoriaDAO();
				b.setCategoria(catDAO.buscarPorID(rs.getInt("id_categoria")));
				catDAO.fechar();

			}
		} catch (SQLException e) {
			b = null;
			System.out.println("Erro: " + e);
		}

		return b;
	}
	
	public List<Bem> listarPorNome(String nome) {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem WHERE nome = ?");
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bem b = new Bem();

				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setDescricao(rs.getString("descricao"));
				
				LocalizacaoDAO locDAO = new LocalizacaoDAO();
				b.setLocalizacao(locDAO.buscarPorID(rs.getInt("id_localizacao")));
				locDAO.fechar();
				
				CategoriaDAO catDAO = new CategoriaDAO();
				b.setCategoria(catDAO.buscarPorID(rs.getInt("id_categoria")));
				catDAO.fechar();

				lista.add(b);
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
	public boolean movimentar(String codigo, String loc_nova) {
		int n;

		try {
			
			LocalizacaoDAO locDAO = new LocalizacaoDAO();
			Localizacao nova = locDAO.buscarPorNome(loc_nova);
			locDAO.fechar();
			
			
			PreparedStatement stmt = conexao.prepareStatement("UPDATE bem SET id_localizacao = ? WHERE id = ?");

			stmt.setInt(1, nova.getId());
			stmt.setString(2, codigo);

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			n = 0;
			System.out.println("Erro: " + e);
		}

		return n == 1;
	}

}