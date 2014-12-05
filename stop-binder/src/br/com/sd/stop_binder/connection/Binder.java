package br.com.sd.stop_binder.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import br.com.sd.stop_binder.game.StopGameConfiguration;
import br.com.sd.stop_binder.util.ConfigurationProperties;

public class Binder {

	private static Properties configuration;
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("ERRO: Arquivo de configuracao não informado.");
			System.out.println("\tModo de uso: java Binder X:\\caminho\\arquivo\\de\\configuration.properties");
			System.exit(1);
		}
		String configurationFilePath = args[0];
		configuration = new Properties();
		try {
			configuration.load(new FileInputStream(configurationFilePath));
		} catch (IOException e) {
			System.out.println("ERRO: Erro ao carregar configuração.");
			e.printStackTrace();
			System.exit(2);
		}
		
		int port = Integer.parseInt(configuration.getProperty(ConfigurationProperties.SERVER_PORT));
		int connectionTimeout = Integer.parseInt(configuration.getProperty(ConfigurationProperties.SERVER_CONNECTION_TIMEOUT)); 
		Server server = new Server(port, connectionTimeout);
		
		int roundsAmount = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_ROUNDS_AMOUNT));
		int roundTimeInSeconds = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_ROUND_TIME));
		int minimumPlayersAmount = Integer.parseInt(configuration.getProperty(ConfigurationProperties.GAME_MINIMUM_PLAYERS_AMOUNT));
		StopGameConfiguration gameConfiguration = new StopGameConfiguration(roundsAmount, roundTimeInSeconds, minimumPlayersAmount);
		
		server.loadGameParameters(gameConfiguration);
		server.start();
	}
}
