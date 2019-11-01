package br.ufrn.edu.imd.lpii.dorabot.model;

import java.util.ArrayList;

public class Bem {

	private String codigo;
	private String nome;
	private String descricao;
	Localizacao localizacao;
	Categoria categoria;
	ArrayList<Bem> bens = new ArrayList<Bem>();
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	
	public void listarBens(String nomeBem) {
		for(Bem bens: bens) {
			if(bens.getNome().equalsIgnoreCase(nomeBem)  == true) {
				System.out.println("C�digo: " + bens.getCodigo()+ "\nNome: " + bens.getNome() + "\nDescricao: " + bens.getDescricao() + "\nLocaliza��o: "
						+ bens.localizacao.getNome() + "\nCategoria: " + bens.categoria.getNome());
			}
		}
	}
	
	public void listarBens() {
		for(Bem bens: bens) {
			System.out.println("C�digo: " + bens.getCodigo()+ "\nNome: " + bens.getNome() + "\nDescricao: " + bens.getDescricao() + "\nLocaliza��o: "
					+ bens.localizacao.getNome() + "\nCategoria: " + bens.categoria.getNome());
		}
	}

	
	public void listarBensLocalizacao(Localizacao localiz) {
		for(Bem bens: bens) {
			if((bens.localizacao.getNome().equalsIgnoreCase(localiz.getNome())) == true) {
				System.out.println("Bens da localiza��o " + localiz + ":\n" + bens.getNome() + "\n");
			}
		}
	}
	
	public void listarBensCodigo(String codigo) {
		for(Bem bens: bens) {
			if(bens.getCodigo().equalsIgnoreCase(codigo) == true) {
				System.out.println("Bem encontrado!\nLocaliza��o: " + bens.localizacao.getNome());
			}else {
				System.out.println("N�o h� bem com esse c�digo!");
			}
		}
	}
	
	public void listarBensNome(String nome) {
		for(Bem bens: bens) {
			if(bens.getNome().equalsIgnoreCase(nome) == true) {
				bens.listarBens(nome);
			}else {
				System.out.println("N�o h� bem com esse c�digo!");
			}
		}
	}
	
	public void listarBensDescricao(String descricao) {
		for(Bem bens: bens) {
			if(bens.getDescricao().equalsIgnoreCase(descricao) == true) {
				bens.listarBens(bens.getNome());
			}else {
				System.out.println("N�o h� bem com essa descri��o!");
			}
		}
	}
	
	public void listarBensCategoria(Categoria categ) {
		for(Bem bens: bens) {
			if(bens.categoria.getNome().equalsIgnoreCase(categ.getNome()) == true) {
				bens.listarBens(bens.getNome());
			}else {
				System.out.println("N�o h� bem com essa categoria");
			}
		}
	}
	
	public void movimentarBem(String codigo, String novaLocalizacao) {
		for(Bem bens: bens) {
			if(bens.getCodigo().equalsIgnoreCase(codigo) == true) {
				if(bens.localizacao.getNome().equalsIgnoreCase(novaLocalizacao) == false){
					System.out.println("Bem movimentado!");
				}else {
					System.out.println("O bem j� se encontra nesta localiza��o!");
				}
			}else {
				System.out.println("N�o h� bem com esse c�digo!");
				
			}
		}
	}
	
	/*public void gerarRelatorio() {
		for(Localizacao loc: localizacoes) {
			for(Bem bens: bens) {
				if(bens.localizacao.getNome().equalsIgnoreCase(loc.getNome()) == true) {
					listarBensLocalizacao(loc);
				}
			}
		}
		
		for(Categoria categ: categorias) {
			for(Bem bens: bens) {
				if(bens.categoria.getNome().equalsIgnoreCase(categ.getNome()) == true) {
					listarBensCategoria(categ);
				}
			}
		}
		
		for*
		
	}*/
	
}
