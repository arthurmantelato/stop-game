package br.com.sd.stop_player.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import br.com.sd.stop_player.util.ConfigurationProperties;


public class Player {

	private static Properties configuration;

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("ERRO: Arquivo de configuracao não informado.");
			System.out.println("\tModo de uso: java Player X:\\caminho\\arquivo\\de\\configuration.properties");
			System.exit(1);
		}
		String configurationFilePath = args[0];
		configuration = new Properties();
		try {
			configuration.load(new FileInputStream(configurationFilePath));
		} catch (IOException e) {
			System.err.println("ERRO: Erro ao carregar configuração.");
			System.exit(1);
		}
		
		String serverHostname = configuration.getProperty(ConfigurationProperties.SERVER_HOSTNAME);
		int serverPort = Integer.parseInt(configuration.getProperty(ConfigurationProperties.SERVER_PORT));
		
		Client client = new Client(serverHostname, serverPort);
		client.connect();
	}
}
