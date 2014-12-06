package br.com.sd.stop_player.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player {
	
	private Socket socket;
	private String name;

	public Player(String name) {
		this.name = name;
	}
	
	public void connect(String hostname, int port) {
		try {
			this.socket = new Socket(hostname, port);
			System.out.println(String.format("Conectado ao servidor %s:%s", hostname, port));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			output.println(String.format("Hello, my name is %s", name));
			
			String fromServer;
			while((fromServer = input.readLine()) != null && !fromServer.equals("FIMDEJOGO")) {
				System.out.println(String.format("SERVER: %s", fromServer));
			}
			
		} catch (UnknownHostException e) {
			System.err.println(String.format("Servidor não encontrado: %s", e.getMessage()));
			System.exit(1);
		} catch (IOException e) {
			System.err.println(String.format("Erro de I/O ao abrir socket com o servidor: %s", e.getMessage()));
			System.exit(1);
		}
	}
	
	public void disconnect() {
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				System.err.println(String.format("Erro ao fechar socket: %s", e.getMessage()));
				System.exit(1);
			}
		}
	}
}