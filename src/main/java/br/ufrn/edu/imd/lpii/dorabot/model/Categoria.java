package br.ufrn.edu.imd.lpii.dorabot.model;

import java.util.ArrayList;

public class Categoria {

	private String codigo;
	private String nome;
	private String descricao;
	ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	
	public Categoria(String codigo, String nome, String descricao){
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String getCodigo(){
		return codigo;
	}
	
	public void setCodigo(String codigo){
		this.codigo = codigo;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao; 
	}
	
	public void listarLocalizacoes() {
		for(Categoria categ: categorias){
			  System.out.println("Codigo: " + categ.getCodigo() + " | Nome: " + categ.getNome() + " | Descrição: " + categ.getNome());
		}
	}
	
}
