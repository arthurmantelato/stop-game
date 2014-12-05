package br.com.sd.stop_player.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import br.com.sd.stop_player.game.StopGame;

public class Client {
	
	private String serverHostname;
	private int serverPort;

	public Client(String serverHostname, int serverPort) {
		this.serverHostname = serverHostname;
		this.serverPort = serverPort;
	}
	
	public void connect() {
		try {
			Socket socket = new Socket(serverHostname, serverPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fromServer;
			while((fromServer = in.readLine()) !=  null) {
				System.out.println(String.format("SERVER: %s", fromServer));
			}
			String gameParameters = loadGameParameters(fromServer);
			StopGame stopGame = new StopGame(gameParameters);
			
		} catch (UnknownHostException e) {
			System.err.println("Servidor não encontrado.");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Erro de I/O ao abrir socket com o servidor.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private String loadGameParameters(String fromServer) {
		// TODO Auto-generated method stub
		return null;
	}
}
