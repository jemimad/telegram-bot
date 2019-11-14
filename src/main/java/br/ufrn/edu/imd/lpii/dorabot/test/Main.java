package br.ufrn.edu.imd.lpii.dorabot.test;

import java.util.List;

import com.google.protobuf.Message;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import br.ufrn.edu.imd.lpii.dorabot.model.Estados;

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
		String nome = "";
		String descricao = "";
		String cod_localizacao = "";
		String cod_categoria = "";
		
		//loop infinito pode ser alterado por algum timer de intervalo curto
		while (true){
		
			//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
			
			//lista de mensagens
			List<Update> updates = updatesResponse.updates();

			//an�lise de cada a��o da mensagem
			for (Update update : updates) {
				
				if (estado == Estados.NULO) {
					
					if(update.message().text().equals("/start")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Olá! Eu sou a Dora. Use os comandos disponíveis para interagir comigo! :)"));
					} else if(update.message().text().equals("/cadastrarbem")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome do bem:"));
						estado = Estados.ESPERA_NOME_BEM;
					} else if(update.message().text().equals("/cadastrarlocalizacao")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome da categoria:"));
						estado = Estados.ESPERA_NOME_LOCALIZACAO;
					} else if(update.message().text().equals("/cadastrarcategoria")) {
						baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Informe o nome da categoria:"));
						estado = Estados.ESPERA_NOME_CATEGORIA;
					} else if(update.message().text().equals("/gerarRelatorio")) {
						
					} else if(update.message().text().equals("/listarbens")) {
						
					} else if(update.message().text().equals("/listarlocalizacoes")) {
						
					} else if(update.message().text().equals("/listarcategorias")) {
						
					} else if(update.message().text().equals("/listarbensporlocalizacao")) {
						
					} else if(update.message().text().equals("/buscarbemcodigo")) {
						
					} else if(update.message().text().equals("/buscarbemnome")) {
						
					} else if(update.message().text().equals("/buscarbemdescricao")) {
					
					} else if(update.message().text().equals("/movimentarbem")) {
						
					} else if(update.message().text().equals("/obrigada")) {
						
					}
					
				} else if (estado == estado.ESPERA_NOME_BEM) {
					
				} else if (estado == estado.ESPERA_DESCRICAO_BEM) {
					
				} else if (estado == estado.ESPERA_LOCALIZACAO_BEM) {
					
				} else if (estado == estado.ESPERA_CATEGORIA_BEM) {
					
				} else if (estado == estado.ESPERA_NOME_LOCALIZACAO) {
					
				} else if (estado == estado.ESPERA_DESCRICAO_LOCALIZACAO) {
					
				} else if (estado == estado.ESPERA_NOME_CATEGORIA) {
					
				} else if (estado == estado.ESPERA_DESCRICAO_CATEGORIA) {
					
				}
				
			}

		}

	}
	
}
