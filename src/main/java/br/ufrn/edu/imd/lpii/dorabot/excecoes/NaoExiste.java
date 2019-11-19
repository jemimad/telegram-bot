package br.ufrn.edu.imd.lpii.dorabot.excecoes;

/**
 * Classe usada para o tratamento de exceções.
 */
public class NaoExiste extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NaoExiste(String mensagem) {
		super(mensagem);
	}
	
}
