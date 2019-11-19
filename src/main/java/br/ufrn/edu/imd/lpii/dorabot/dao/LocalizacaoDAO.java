package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.edu.imd.lpii.dorabot.excecoes.NaoExiste;
import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

/**
 * Classe responsável por requisitar ao banco as consultas necessárias para os objetos da classe Localiza��o.
 */
public class LocalizacaoDAO extends AbstractDAO {

	public LocalizacaoDAO() {
		super();
	}
	
	/**
	 * Método para cadastro de localizações.
	 * @param l Objeto do tipo localização a ser cadastrado.
	 * @return true
	 */
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
	
	/**
	 * Método para busca de localização a partir de um id.
	 * @param id Inteiro informando o id da localização a ser busca.
	 * @return Retorna a localização referente.
	 */
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
	
	/**
	 * Método para busca de localização a partir de um nome.
	 * @param nome String informando o nome da localização a ser buscada.
	 * @return Retorna a localização referente.
	 * @throws NaoExiste Caso não haja localização com o nome informado.
	 */
	public Localizacao buscarPorNome(String nome) throws NaoExiste {
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
		
		if(l == null) {
			throw new NaoExiste("Não existe essa localização!");
		} else {
			return l;
		}
	}
	
	/**
	 * Método para listagem de todas as localizações.
	 * @return Retorna uma lista de localizações.
	 * @throws NaoExiste Caso não haja localizações cadastradas no banco.
	 */	
	public List<Localizacao> listar() throws NaoExiste {
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
		
		if(lista.size() == 0) {
			throw new NaoExiste("Não há localizações cadastradas!");
		}else {
			return lista;
		}
	}
	
}