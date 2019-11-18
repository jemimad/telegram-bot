package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public Localizacao buscarPorID(int id) {
		Localizacao l = null;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM localizacao WHERE id = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				l = new Localizacao();

				l.setId(rs.getInt("id"));
				l.setNome(rs.getString("nome"));
				l.setDescricao(rs.getString("descricao"));

			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			l = null;
			System.out.println("Erro: " + e);
		}

		return l;
	}
	
	public Localizacao buscarPorNome(String nome) {
		Localizacao l = null;
		
		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM localizacao WHERE nome = ?");
			stmt.setString(1, nome);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				l = new Localizacao();

				l.setId(rs.getInt("id"));
				l.setNome(rs.getString("nome"));
				l.setDescricao(rs.getString("descricao"));

			}
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			l = null;
			System.out.println("Erro: " + e);
		}

		return l;
	}
	
	public List<Localizacao> listar() {
		List<Localizacao> lista = new ArrayList<Localizacao>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM localizacao");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Localizacao l = new Localizacao();

				l.setId(rs.getInt("id"));
				l.setNome(rs.getString("nome"));
				l.setDescricao(rs.getString("descricao"));

				lista.add(l);
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
}