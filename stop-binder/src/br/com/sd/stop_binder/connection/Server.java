package br.com.sd.stop_binder.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import br.com.sd.stop_binder.game.StopGameConfiguration;

public class Server {
	
	private StopGameConfiguration gameConfiguration;
	private ServerSocket serverSocket;
	private int connectionTimeout;

	public Server(int port, int connectionTimeout) {
		try {
			this.connectionTimeout = connectionTimeout;
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("ERRO: Erro ao abrir socket do servidor.");
			e.printStackTrace();
			System.exit(3);
		}
	}

	public void loadGameParameters(StopGameConfiguration gameConfiguration) {
		this.gameConfiguration = gameConfiguration;
	}
	
	public void start() {
		try {
			Socket clientSocket = serverSocket.accept();
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			output.println(String.format("%s;%s", gameConfiguration.getRoundsAmount(), gameConfiguration.getRoundTime()));
			/*ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<String> handler = executor.submit(new Task(clientSocket, gameConfiguration));
			handler.get(connectionTimeout, TimeUnit.SECONDS);	*/
			
		} catch (IOException e) {
			System.out.println("ERRO: Erro ao iniciar servidor/binder.");
			e.printStackTrace();
			System.exit(4);
		} /*catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}

class Task implements Callable<String> {

	private Socket socket;
	private StopGameConfiguration gameConfiguration;
	
	public Task(Socket socket, StopGameConfiguration gameConfiguration) {
		this.socket = socket;
		this.gameConfiguration = gameConfiguration;
	}

	@Override
	public String call() throws Exception {
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
		output.println(String.format("%s;%s", gameConfiguration.getRoundsAmount(), gameConfiguration.getRoundTime()));
		return "Pronto";
	}
	
}