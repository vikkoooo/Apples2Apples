package src;

import java.io.*;
import java.net.*;

public class PlayerConnection {
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

	public DataOutputStream getOutput() {
		return output;
	}

	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
}