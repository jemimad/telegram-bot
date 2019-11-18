package br.ufrn.edu.imd.lpii.dorabot.model;
/**
 * Classe para objetos do tipo Bem, onde estão contidos os atributos e metodos para o mesmo.
 */
public class Bem {

	private int id;
	private String nome;
	private String descricao;
	Localizacao localizacao;
	Categoria categoria;
	
	public Bem() {
		
	}
	
	public Bem(int id, String nome, String descricao, Localizacao localizacao, Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.localizacao = localizacao;
		this.categoria = categoria;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Localizacao getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nNome: " + nome + "\nDescrição: " + descricao + "\nLocalização: " + localizacao.getNome()
				+ "\nCategoria: " + categoria.getNome() + "\n";
	}
	
	
	
}
