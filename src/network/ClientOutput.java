package src.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientOutput implements IClientOutput {

	private Socket socket;

	public ClientOutput(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getInput() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void writeMessage(String message) throws IOException {
		// Implementation here
	}
}