package br.com.sd.stop_binder.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

import br.com.sd.stop_binder.game.StopGameConfiguration;

public class Binder extends Thread {
	
	private StopGameConfiguration gameConfiguration;
	private Socket socket;

	public Binder(Socket socket) {
		super(String.format("Binder"));
		this.socket = socket;
	}

	public void loadGameParameters(StopGameConfiguration gameConfiguration) {
		this.gameConfiguration = gameConfiguration;
	}
	
	public void run() {
		try {
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output.println(String.format("%s;%s", gameConfiguration.getRoundsAmount(), gameConfiguration.getRoundTime()));
			while(input.ready()) {
				System.out.println(String.format("CLIENT: %s", input.readLine()));
			}
			/*ExecutorService executor = Executors.newSingleThreadExecutor();
			Future<String> handler = executor.submit(new Task(clientSocket, gameConfiguration));
			handler.get(connectionTimeout, TimeUnit.SECONDS);	*/
		} catch (IOException e) {
			System.out.println(String.format("Erro ao iniciar servidor/binder: %s", e.getMessage()));
			System.exit(1);
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