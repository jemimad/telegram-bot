package br.ufrn.edu.imd.lpii.dorabot.test;

import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.ufrn.edu.imd.lpii.dorabot.dao.BemDAO;
import br.ufrn.edu.imd.lpii.dorabot.dao.CategoriaDAO;
import br.ufrn.edu.imd.lpii.dorabot.dao.LocalizacaoDAO;
import br.ufrn.edu.imd.lpii.dorabot.model.Bem;
import br.ufrn.edu.imd.lpii.dorabot.model.Categoria;
import br.ufrn.edu.imd.lpii.dorabot.model.Estados;
import br.ufrn.edu.imd.lpii.dorabot.model.Localizacao;

public class Main {

	public static void main(String[] args) {

		//Cria��o do objeto bot com as informa��es de acesso
		TelegramBot bot = new TelegramBot("1027972095:AAHb35m5Al2v7hee1lzh1dTS_XYOLQkl7C8");

		//objeto respons�vel por receber as mensagens
		GetUpdatesResponse updatesResponse;
		//objeto respons�vel por gerenciar o envio de respostas
		SendResponse sendResponse;
		//objeto respons�vel por gerenciar o envio de a��es do chat
		BaseResponse baseResponse;
		
		//controle de off-set, isto �, a partir deste ID ser� lido as mensagens pendentes na fila
		int m=0;
		
		Estados estado = Estados.NULO;
		String localizacao = "";
		String categoria = "";
		String codigo = "";
		Bem bem = new Bem();
		Localizacao loc = new Localizacao();
		Categoria cat = new Categoria();

		//loop infinito pode ser alterado por algum timer de intervalo curto
		while (true){
		
			//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
			
			//lista de mensagens
			List<Update> updates = updatesResponse.updates();

			//an�lise de cada a��o da mensagem
			for (Update update : updates) {
				m = update.updateId()+1;
				
				if (estado == Estados.NULO) {
					
					if(update.message().text().equals("/start")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), 
								"Olá! Eu sou a Dora. Use os comandos disponíveis para interagir comigo! :)"));
						
					} else if(update.message().text().equals("/cadastrarbem")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome do bem:"));
						estado = Estados.ESPERA_NOME_BEM;
						
					} else if(update.message().text().equals("/cadastrarlocalizacao")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome da localização:"));
						estado = Estados.ESPERA_NOME_LOCALIZACAO;
						
					} else if(update.message().text().equals("/cadastrarcategoria")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome da categoria:"));
						estado = Estados.ESPERA_NOME_CATEGORIA;
						
					} else if(update.message().text().equals("/gerarrelatorio")) {
						BemDAO bemDAO = new BemDAO();
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "RELATÓRIO AGRUPADO POR LOCALIZAÇÃO:"));
						for (Map.Entry<String, String> b : bemDAO.quantidadePorLocalizacao().entrySet()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
						}
						for (Bem b : bemDAO.listarAgrupadosPorLocalizacao()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "RELATÓRIO AGRUPADO POR CATEGORIA:"));
						for (Map.Entry<String, String> b : bemDAO.quantidadePorCategoria().entrySet()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
						}
						for (Bem b : bemDAO.listarAgrupadosPorCategoria()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "RELATÓRIO AGRUPADO POR NOME:"));
						for (Map.Entry<String, String> b : bemDAO.quantidadePorNome().entrySet()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.getKey().toUpperCase() + ": " + b.getValue()));
						}
						for (Bem b : bemDAO.listarAgrupadosPorNome()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						bemDAO.fechar();
						
					} else if(update.message().text().equals("/listarbens")) {
						BemDAO bemDAO = new BemDAO();
						for (Bem b : bemDAO.listar()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
						}
						bemDAO.fechar();
						
					} else if(update.message().text().equals("/listarlocalizacoes")) {
						LocalizacaoDAO locDAO = new LocalizacaoDAO();
						for (Localizacao l : locDAO.listar()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), l.toString()));
						}
						locDAO.fechar();
						
					} else if(update.message().text().equals("/listarcategorias")) {
						CategoriaDAO catDAO = new CategoriaDAO();
						for (Categoria c : catDAO.listar()) {
							baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), c.toString()));
						}
						catDAO.fechar();
						
						
					} else if(update.message().text().equals("/listarbensporlocalizacao")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a localização desejada:"));
						estado = Estados.ESPERA_LOCALIZACAO_LISTAGEM;
						
					} else if(update.message().text().equals("/buscarbemcodigo")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o código do bem desejado:"));
						estado = Estados.ESPERA_CODIGO_BUSCA;
						
					} else if(update.message().text().equals("/buscarbemnome")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome do bem desejado:"));
						estado = Estados.ESPERA_NOME_BUSCA;
						
					} else if(update.message().text().equals("/buscarbemdescricao")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a descrição do bem desejado:"));
						estado = Estados.ESPERA_DESCRICAO_BUSCA;
						
					} else if(update.message().text().equals("/movimentarbem")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o código do bem a ser movido:"));
						estado = Estados.ESPERA_CODIGO_MOVIMENTACAO;
						
					} else if(update.message().text().equals("/obrigada")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "De nada, lindx! :)"));
						estado = Estados.NULO;
					}
					
				} else if (estado == Estados.ESPERA_NOME_BEM) {
					bem.setNome(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a descrição do bem:"));
					estado = Estados.ESPERA_DESCRICAO_BEM;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_BEM) {
					bem.setDescricao(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a localização do bem:"));
					estado = Estados.ESPERA_LOCALIZACAO_BEM;
					
				} else if (estado == Estados.ESPERA_LOCALIZACAO_BEM) {
					localizacao = update.message().text();
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a categoria do bem:"));
					estado = Estados.ESPERA_CATEGORIA_BEM;
					
				} else if (estado == Estados.ESPERA_CATEGORIA_BEM) {
					categoria = update.message().text();
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					BemDAO bemDAO = new BemDAO();
					bemDAO.inserir(bem, localizacao, categoria);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem adicionado com sucesso!"));
					bemDAO.fechar();
					estado = Estados.NULO;
					
				} else if (estado == Estados.ESPERA_NOME_LOCALIZACAO) {
					loc.setNome(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a descrição da localização:"));
					estado = Estados.ESPERA_DESCRICAO_LOCALIZACAO;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_LOCALIZACAO) {
					loc.setDescricao(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					LocalizacaoDAO locDAO = new LocalizacaoDAO();
					locDAO.inserir(loc);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Localização adicionada com sucesso!"));
					locDAO.fechar();
					estado = Estados.NULO;
					
				} else if (estado == Estados.ESPERA_NOME_CATEGORIA) {
					cat.setNome(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a descrição da categoria:"));
					estado = Estados.ESPERA_DESCRICAO_CATEGORIA;
					
				} else if (estado == Estados.ESPERA_DESCRICAO_CATEGORIA) {
					cat.setDescricao(update.message().text());
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					CategoriaDAO catDAO = new CategoriaDAO();
					catDAO.inserir(cat);
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Categoria adicionada com sucesso!"));
					catDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_LOCALIZACAO_LISTAGEM) {
					BemDAO bemDAO = new BemDAO();
					for (Bem b : bemDAO.listarPorLocalizacao(update.message().text())) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
					}
					bemDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_CODIGO_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), bemDAO.buscarPorCodigo(update.message().text()).toString()));
					bemDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_NOME_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					for (Bem b : bemDAO.listarPorNome(update.message().text())) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
					}
					bemDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_DESCRICAO_BUSCA) {
					BemDAO bemDAO = new BemDAO();
					for (Bem b : bemDAO.listarPorNome(update.message().text())) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), b.toString()));
					}
					bemDAO.fechar();
					estado = Estados.NULO;
					
				} else if(estado == Estados.ESPERA_CODIGO_MOVIMENTACAO) {
					codigo = update.message().text();
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe a nova localização do bem:"));
					estado = Estados.ESPERA_NOVALOCALIZACAO_MOVIMENTACAO;
					
				} else if(estado == Estados.ESPERA_NOVALOCALIZACAO_MOVIMENTACAO) {
					localizacao = update.message().text();
					BemDAO bemDAO = new BemDAO();
					bemDAO.movimentar(codigo, localizacao);
					baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Bem movido com sucesso!"));
					estado = Estados.NULO;
					
				}
				
			}

		}

	}	
}