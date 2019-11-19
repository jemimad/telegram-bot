package br.ufrn.edu.imd.lpii.dorabot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.ufrn.edu.imd.lpii.dorabot.excecoes.NaoExiste;
import br.ufrn.edu.imd.lpii.dorabot.model.Bem;
import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

/**
 * Classe responsável por requisitar ao banco as consultas necessárias para os objetos da classe Bem.
 */
public class BemDAO extends AbstractDAO {

	public BemDAO() {
		super();
	}
	
	/**
	 * Método para cadastro de bens, recebe três paramêtros:.
	 * @param b Objeto do tipo bem a ser cadastrado;
	 * @param nomeLoc String referente a localização do bem;
	 * @param nomeCat String referente a categoria do bem.
	 * @return true
	 */
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
	
	/**
	 * Método para listagem de todos os bens.
	 * @return Retorna uma lista de bens.
	 * @throws NaoExiste Caso não haja nenhum bem cadastro no banco.
	 */	
	public List<Bem> listar() throws NaoExiste {
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
		
		if (lista.size() == 0) {
			throw new NaoExiste("Não há nenhum bem cadastrado!");
		}else {
			return lista;
		}
	}
	
	/**
	 * Método para listagem de bens a partir de uma localização.
	 * @param loc String informando a localização desejada.
	 * @return Retorna uma lista de bens.
	 * @throws NaoExiste Caso não haja nenhum bem na localização desejada.
	 */	
	public List<Bem> listarPorLocalizacao(String loc) throws NaoExiste {
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
		if(lista.size() == 0) {
			throw new NaoExiste("Não há nenhum bem nessa localização!");
		}else {
			return lista;
		}
	}
	
	/**
	 * Método para busca de bens a partir de um código.
	 * @param codigo String informando o código do bem desejado.
	 * @return Retorna uma lista de bens.
	 * @throws NaoExiste Caso o código do bem a ser buscado não exista no banco.
	 */		
	public Bem buscarPorCodigo(String codigo) throws NaoExiste {
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
		
		if(b == null) {
			throw new NaoExiste("Não existe nenhum bem com esse código!");
		} else {
			return b;
		}
	}
	
	/**
	 * Método para busca de bens a partir de uma descrição.
	 * @param descricao String informando a descrição do bem desejado.
	 * @return Retorna um bem.
	 * @throws NaoExiste Caso não exista um bem com a descrição informada.
	 */		
	public Bem buscarPorDescricao(String descricao) throws NaoExiste {
		Bem b = null;

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem WHERE descricao = ?");
			stmt.setString(1, descricao);
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
		
		if(b == null) {
			throw new NaoExiste("Não existe nenhum bem com essa descrição");
		} else {
			return b;
		}
	}
	
	/**
	 * Método para listagem de bens a partir de um nome.
	 * @param nome String informando o nome do bem desejado.
	 * @return Retorna uma lista de bens.
	 * @throws NaoExiste Caso não exista bem com o nome informado.
	 */	
	
	public List<Bem> listarPorNome(String nome) throws NaoExiste {
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
		if(lista.size() == 0) {
			throw new NaoExiste("Não há bem com esse nome!");
		}else {
			return lista;
		}
	}
	
	/**
	 * Método para alterar a localização de um bem.
	 * @param codigo String informando o código do bem que irá ser movimentado;
	 * @param loc_nova String informando a nova localização do bem.
	 * @return true.
	 */		
	public boolean movimentar(String codigo, String loc_nova) {
		int n;

		try {
			
			LocalizacaoDAO locDAO = new LocalizacaoDAO();
			Localizacao nova = new Localizacao();
			try {
				nova = locDAO.buscarPorNome(loc_nova);
			} catch (NaoExiste e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
	
	/**
	 * Método para listagem de bens agrupados por localização.
	 * @return Retorna uma lista de bens.
	 */	
	public List<Bem> listarAgrupadosPorLocalizacao() {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT bem.id, bem.nome, bem.descricao, bem.id_localizacao, bem.id_categoria, localizacao.nome "
					+ "FROM bem INNER JOIN localizacao ON bem.id_localizacao = localizacao.id ORDER BY localizacao.nome ASC");
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
	
	/**
	 * Método para listagem de bens agrupados por categoria.
	 * @return Retorna uma lista de bens.
	 */	
	public List<Bem> listarAgrupadosPorCategoria() {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT bem.id, bem.nome, bem.descricao, bem.id_localizacao, bem.id_categoria, categoria.nome "
					+ "FROM bem INNER JOIN categoria ON bem.id_categoria = categoria.id ORDER BY categoria.nome ASC;");
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
	
	/**
	 * Método para listagem de bens agrupados por nome.
	 * @return Retorna uma lista de bens.
	 */	
	public List<Bem> listarAgrupadosPorNome() {
		List<Bem> lista = new ArrayList<Bem>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM bem ORDER BY nome ASC;");
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
	
	/**
	 * Método para contagem de bens por localização.
	 * @return Retorna uma lista de bens.
	 */	
	public Map<String, String> quantidadePorLocalizacao() {
		Map<String, String> lista = new TreeMap<String, String>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT localizacao.nome, COUNT(*) as `contador` FROM bem INNER JOIN localizacao "
					+ "ON bem.id_localizacao = localizacao.id GROUP BY localizacao.id ORDER BY localizacao.nome ASC");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.put(rs.getString("nome"), rs.getString("contador"));
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
	/**
	 * Método para contagem de bens por categoria.
	 * @return Retorna uma lista de bens.
	 */	
	public Map<String, String> quantidadePorCategoria() {
		Map<String, String> lista = new TreeMap<String, String>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT categoria.nome, COUNT(*) as `contador` FROM bem INNER JOIN categoria "
					+ "ON bem.id_categoria = categoria.id GROUP BY categoria.id ORDER BY categoria.nome ASC");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.put(rs.getString("nome"), rs.getString("contador"));
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}
	
	/**
	 * Método para contagem de bens por nome.
	 * @return Retorna uma lista de bens.
	 */	
	public Map<String, String> quantidadePorNome() {
		Map<String, String> lista = new TreeMap<String, String>();

		try {
			PreparedStatement stmt = conexao.prepareStatement("SELECT nome, COUNT(*) as `contador` FROM bem GROUP BY nome ORDER BY nome ASC");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				lista.put(rs.getString("nome"), rs.getString("contador"));
			}
		} catch (SQLException e) {
			lista = null;
			System.out.println("Erro: " + e);
		}

		return lista;
	}

}