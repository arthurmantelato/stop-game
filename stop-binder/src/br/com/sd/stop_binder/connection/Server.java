package br.com.sd.stop_binder.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import br.com.sd.stop_binder.game.StopGameConfiguration;
import br.com.sd.stop_binder.util.ConfigurationProperties;

public class Server {

	private static Properties configuration;
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("ERRO: Arquivo de configuracao não informado.");
			System.out.println("\tModo de uso: java Binder X:\\caminho\\arquivo\\de\\configuration.properties");
			System.exit(1);
		}
		String configurationFilePath = args[0];
		configuration = new Properties();
		try {
			configuration.load(new FileInputStream(configurationFilePath));
		} catch (IOException e) {
			System.err.println(String.format("Erro ao carregar configuração: %s", e.getMessage()));
			System.exit(1);
		}
		startServer();
	}
	
	private static void startServer() {
		int port = Integer.parseInt(configuration.getProperty(ConfigurationProperties.SERVER_PORT));
		int connectionTimeout = Integer.parseInt(configuration.getProperty(ConfigurationProperties.SERVER_CONNECTION_TIMEOUT));
		
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println(String.format("Erro ao abrir socket[porta=%d]: %s", port, e.getMessage()));
			System.exit(1);
		}
		
		int roundsAmount = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_ROUNDS_AMOUNT));
		int roundTimeInSeconds = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_ROUND_TIME));
		int minimumPlayersAmount = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_MINIMUM_PLAYERS_AMOUNT));
		StopGameConfiguration gameConfiguration = new StopGameConfiguration(roundsAmount, roundTimeInSeconds);
		
		int playersReady = 0;
		while (playersReady < minimumPlayersAmount) { 
			System.out.println("Servidor iniciado. Aguardando jogadores...");
			try {
				Binder binder = new Binder(socket.accept());
				System.out.println("Jogador identificado. Repassando regras...");
				binder.loadGameParameters(gameConfiguration);
				binder.start();
				playersReady++;
			} catch (IOException e) {
				System.err.println(String.format("Erro ao iniciar socket[%d]: %s", port, e.getMessage()));
				System.exit(1);
			}
		}
	}
}