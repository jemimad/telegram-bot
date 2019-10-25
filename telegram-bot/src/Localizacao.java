import java.util.ArrayList;

public class Localizacao {
	private String nome;
	private String descricao;
	ArrayList<Localizacao> localizacoes = new ArrayList<Localizacao>();
	
	public Localizacao(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
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
		for(Localizacao loc: localizacoes){
			  System.out.println("Nome: " + loc.nome + " | Descrição: " + loc.descricao);
		}
	}
}
