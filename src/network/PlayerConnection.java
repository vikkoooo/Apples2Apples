package src.network;

import java.io.*;
import java.net.*;

public class PlayerConnection implements IClientOutput {
	private Socket socket;
	private BufferedReader input;
	private DataOutputStream output;

	public PlayerConnection(Socket socket) throws IOException {
		this.socket = socket;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new DataOutputStream(socket.getOutputStream());
	}

	public BufferedReader getInput() {
		return input;
	}

	@Override
	public void writeMessage(String message) throws IOException {
		output.writeBytes(message + "\n");
	}

	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
}