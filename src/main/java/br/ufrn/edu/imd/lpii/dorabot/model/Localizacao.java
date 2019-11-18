package br.ufrn.edu.imd.lpii.dorabot.model;

/**
 * Classe para objetos do tipo Localiza��o, onde ser�o contidos os atributos
 * e m�todos para o mesmo.
 */

public class Localizacao {
	
	private int id;
	private String nome;
	private String descricao;
	
	public Localizacao() {
		
	}
	
	public Localizacao(int id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome(){
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

	@Override
	public String toString() {
		return "ID: " + id + "\nNome: " + nome + "\nDescricao: " + descricao + "\n";
	}
	
}
