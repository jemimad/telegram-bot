package br.ufrn.edu.imd.lpii.dorabot.test;

import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import br.ufrn.edu.imd.lpii.dorabot.dao.BemDAO;
import br.ufrn.edu.imd.lpii.dorabot.dao.CategoriaDAO;
import br.ufrn.edu.imd.lpii.dorabot.dao.LocalizacaoDAO;
import br.ufrn.edu.imd.lpii.dorabot.excecoes.NaoExiste;
import br.ufrn.edu.imd.lpii.dorabot.model.Bem;
import br.ufrn.edu.imd.lpii.dorabot.model.Categoria;
import br.ufrn.edu.imd.lpii.dorabot.model.Estados;
import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

/**
 * Classe principal responsável por tratar toda a comunicação entre o usuário e o Bot.
 * Nessa classe é feita a conexão com o bot, através do token, 
 * assim como toda a conversação entre o usuário e o bot.
 *
 * @author Jâncy Wdson Coriolano de Aragão - github: jancyaragao
 * @author Jemima Dias Nascimento - github: jemimad
 * 
 */
public class Main {

	public static void main(String[] args) {

		/** Criação do objeto bot com as informações de acesso */
		
		TelegramBot bot = new TelegramBot("1027972095:AAHb35m5Al2v7hee1lzh1dTS_XYOLQkl7C8");

		/** Objeto responsável por receber as mensagens do usuário */
		GetUpdatesResponse updatesResponse;

		
		/** Controle de off-set, isto é, a partir deste ID será lido as 
		 * mensagem pendentes na fila */
		int m=0;
		

		Estados estado = Estados.NULO;		
		String localizacao = "";
		String categoria = "";
		String codigo = "";
		Bem bem = new Bem();
		Localizacao loc = new Localizacao();
		Categoria cat = new Categoria();

		while (true){
			/** Executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)*/
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
			
			/** Lista de mensagens */
			List<Update> updates = updatesResponse.updates();

			/** Análise de cada mensagem */
			for (Update update : updates) {
				m = update.updateId()+1;
				
				/** Verifica se o estado atual é nulo. Se sim, inicia a leitura dos comandos alterando o estado
				 * de acordo com o comando que o usuário digitou. Por exemplo, se o comando for '/start', o bot envia
				 * uma mensagem dando início a conversa. Senão, se for '/cadastrarbem' (ou algum outro dos comandos existentes no bot),
				 * é enviada uma mensagem para o usuário solicitando a entrada de algum dado e a máquina de estados é alterada.
				 * (nesse ponto é importante entender os estados no nosso enumerator). Assim que o usuário informar o dado solicitado,
				 * é feita novamente a verificação do estado atual, que nesse caso não será nulo, exceto no caso das listagens onde não é
				 * necessário informar nenhum dado. Assim, a primeira condição não será satisfeita e entraremos no "else" que irá verificar
				 * o estado, receber o dado e alterar a máquina de estado para esperar o próximo dado solicitado se necessário. */
				if (estado == Estados.NULO) {
					
					if(update.message().text().equals("/start")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), 
								"Olá! Eu sou a Dora. Use os comandos disponíveis para interagir comigo! :)"));
						
					} else if(update.message().text().equals("/cadastrarbem")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Qual o nome do bem?"));
						estado = Estados.ESPERA_NOME_BEM;
						
					} else if(update.message().text().equals("/cadastrarlocalizacao")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Onde ele está?"));
						estado = Estados.ESPERA_NOME_LOCALIZACAO;
						
					} else if(update.message().text().equals("/cadastrarcategoria")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "E qual a categoria dele?"));
						estado = Estados.ESPERA_NOME_CATEGORIA;
						
					} else if(update.message().text().equals("/gerarrelatorio")) {
						BemDAO bemDAO = new BemDAO();
						try {
							bemDAO.listar();
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "BENS AGRUPADO POR LOCALIZAÇÃO:"));
							for (Map.Entry<String, String> b : bemDAO.quantidadePorLocalizacao().entrySet()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
							}
							for (Bem b : bemDAO.listarAgrupadosPorLocalizacao()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
							}
							
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "BENS AGRUPADO POR CATEGORIA:"));
							for (Map.Entry<String, String> b : bemDAO.quantidadePorCategoria().entrySet()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
							}
							for (Bem b : bemDAO.listarAgrupadosPorCategoria()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
							}
							
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "BENS AGRUPADO POR NOME:"));
							for (Map.Entry<String, String> b : bemDAO.quantidadePorNome().entrySet()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
							}
							for (Bem b : bemDAO.listarAgrupadosPorNome()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
							}
							bemDAO.fechar();
						} catch (NaoExiste e) {
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "Não há bens cadastrados, assim não tenho como mostrar o relatório :("));
						}
						
					} else if(update.message().text().equals("/listarbens")) {
						BemDAO bemDAO = new BemDAO();
						try {
							for (Bem b : bemDAO.listar()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
								bemDAO.fechar();
							}
						}catch (NaoExiste e){
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "Não há bens cadastrados :("));
						}
						
					} else if(update.message().text().equals("/listarlocalizacoes")) {
						LocalizacaoDAO locDAO = new LocalizacaoDAO();
						try {
							for (Localizacao l : locDAO.listar()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), l.toString()));
							}
							locDAO.fechar();
						}catch (NaoExiste e){
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "Não há localizações cadastradas :("));
						}
						
					} else if(update.message().text().equals("/listarcategorias")) {
						CategoriaDAO catDAO = new CategoriaDAO();
						
						try{
							for (Categoria c : catDAO.listar()) {
								bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
								bot.execute(new SendMessage(update.message().chat().id(), c.toString()));
							}
							catDAO.fechar();
							
						}catch(NaoExiste e) {
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), "Não há categorias cadastradas :("));
						}
						
						
					} else if(update.message().text().equals("/listarbensporlocalizacao")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Onde você quer procurar?"));
						estado = Estados.ESPERA_LOCALIZACAO_LISTAGEM;
						
					} else if(update.message().text().equals("/buscarbemcodigo")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Qual o código do bem?"));
						estado = Estados.ESPERA_CODIGO_BUSCA;
						
					} else if(update.message().text().equals("/buscarbemnome")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Qual o nome do bem?"));
						estado = Estados.ESPERA_NOME_BUSCA;
						
					} else if(update.message().text().equals("/buscarbemdescricao")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Qual a descrição do bem?"));
						estado = Estados.ESPERA_DESCRICAO_BUSCA;
						
					} else if(update.message().text().equals("/movimentarbem")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Qual o código do bem que você quer mover?"));
						estado = Estados.ESPERA_CODIGO_MOVIMENTACAO;
						
					} else if(update.message().text().equals("/obrigada")) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Tchauzinho! :)"));
						estado = Estados.NULO;
					}
					
				} else if (estado == Estados.ESPERA_NOME_BEM) {
					bem.setNome(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					bot.execute(new SendMessage(update.message().chat().id(), "Descreva esse bem para mim, por favor"));
					estado = Estados.ESPERA_DESCRICAO_BEM;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_BEM) {
					bem.setDescricao(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					bot.execute(new SendMessage(update.message().chat().id(), "Onde ele está?"));
					estado = Estados.ESPERA_LOCALIZACAO_BEM;
					
				} else if (estado == Estados.ESPERA_LOCALIZACAO_BEM) {
					localizacao = update.message().text();
					try {
						LocalizacaoDAO locDAO = new LocalizacaoDAO();
						locDAO.buscarPorNome(localizacao);
						
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "E qual a categoria dele?"));
						estado = Estados.ESPERA_CATEGORIA_BEM;
					} catch (NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não existe essa localização no nosso banco."
								+ " Para cadastrá-la utilize o comando /cadastrarlocalizacao ou utilize o comando /listarlocalizacoes"
								+ " para visualizar as localizações existentes."));
						estado = Estados.NULO;
					}
					
				} else if (estado == Estados.ESPERA_CATEGORIA_BEM) {
					categoria = update.message().text();
					
					try {
						CategoriaDAO catDAO = new CategoriaDAO();
						catDAO.buscarPorNome(categoria);
						
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						BemDAO bemDAO = new BemDAO();
						bemDAO.inserir(bem, localizacao, categoria);
						bot.execute(new SendMessage(update.message().chat().id(), "Salvei seu bem!"));
						bemDAO.fechar();
						estado = Estados.NULO;
					} catch (NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não existe essa categoria no nosso banco."
								+ " Para cadastrá-la utilize o comando /cadastrarcategoria ou utilize o comando /listarcategorias"
								+ " para visualizar as categorias existentes. "));
						estado = Estados.NULO;
					}
					
				} else if (estado == Estados.ESPERA_NOME_LOCALIZACAO) {
					loc.setNome(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					bot.execute(new SendMessage(update.message().chat().id(), "Descreva esse local pra mim, por favor"));
					estado = Estados.ESPERA_DESCRICAO_LOCALIZACAO;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_LOCALIZACAO) {
					loc.setDescricao(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					LocalizacaoDAO locDAO = new LocalizacaoDAO();
					locDAO.inserir(loc);
					bot.execute(new SendMessage(update.message().chat().id(), "Salvei sua localização!"));
					locDAO.fechar();
					estado = Estados.NULO;
					
				} else if (estado == Estados.ESPERA_NOME_CATEGORIA) {
					cat.setNome(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					bot.execute(new SendMessage(update.message().chat().id(), "Descreva essa categoria para mim, por favor"));
					estado = Estados.ESPERA_DESCRICAO_CATEGORIA;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_CATEGORIA) {
					cat.setDescricao(update.message().text());
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					CategoriaDAO catDAO = new CategoriaDAO();
					catDAO.inserir(cat);
					bot.execute(new SendMessage(update.message().chat().id(), "Salvei sua categoria!"));
					catDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_LOCALIZACAO_LISTAGEM) {
					BemDAO bemDAO = new BemDAO();
					
					try{
						for (Bem b : bemDAO.listarPorLocalizacao(update.message().text())) {
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						
						bemDAO.fechar();
						estado = Estados.NULO;
					}catch(NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não encontrei nenhum bem nessa localização :("));
						estado = Estados.NULO;
					}
					
					
				} else if(estado == Estados.ESPERA_CODIGO_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					
					try {
						bot.execute(new SendMessage(update.message().chat().id(), bemDAO.buscarPorCodigo(update.message().text()).toString()));
						bemDAO.fechar();
						estado = Estados.NULO;		
					}catch (NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não encontrei nenhum bem com esse código :("));
						estado = Estados.NULO;
					}
					
				} else if(estado == Estados.ESPERA_NOME_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					try{
						for (Bem b : bemDAO.listarPorNome(update.message().text())) {
							bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						bemDAO.fechar();
						estado = Estados.NULO;
					}catch (NaoExiste e){
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não encontrei nenhum bem com esse nome :("));
						estado = Estados.NULO;
					}
					
					
				} else if(estado == Estados.ESPERA_DESCRICAO_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					try {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), bemDAO.buscarPorDescricao(update.message().text()).toString()));
						bemDAO.fechar();
						estado = Estados.NULO;
						
					}catch(NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não encontrei nenhum bem com essa descrição :("));
						estado = Estados.NULO;
					}
					
				} else if(estado == Estados.ESPERA_CODIGO_MOVIMENTACAO) {
					BemDAO bemDAO = new BemDAO();
					try {
						bemDAO.buscarPorCodigo(update.message().text());
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "E para onde vai esse bem?"));
						estado = Estados.ESPERA_NOVALOCALIZACAO_MOVIMENTACAO;
					} catch (NaoExiste e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não encontrei nenhum bem com esse código :("));
						estado = Estados.NULO;
					}
					
				} else if(estado == Estados.ESPERA_NOVALOCALIZACAO_MOVIMENTACAO) {
					LocalizacaoDAO locDAO = new LocalizacaoDAO();
					try {
						locDAO.buscarPorNome(update.message().text());
						BemDAO bemDAO = new BemDAO();
						bemDAO.movimentar(codigo, localizacao);
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Movi seu bem!"));
						estado = Estados.NULO;
					} catch (Exception e) {
						bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						bot.execute(new SendMessage(update.message().chat().id(), "Não existe essa localização no nosso banco."
								+ " Para cadastrá-la utilize o comando /cadastrarlocalizacao ou utilize o comando /listarlocalizacoes"
								+ " para visualizar as localizações existentes."));
						estado = Estados.NULO;
					}
					
				}
				
			}

		}

	}	
}