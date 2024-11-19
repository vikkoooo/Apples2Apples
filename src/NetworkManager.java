package src;

import java.net.*;
import java.io.*;

public class NetworkManager {
	private ServerSocket serverSocket;

	public NetworkManager() throws Exception {
		serverSocket = new ServerSocket(Constants.PORT);
	}

	public Player acceptConnection(int playerID) throws Exception {
		Socket socket = serverSocket.accept();
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		return new Player(playerID, false, socket, inFromClient, outToClient);
	}

	public void close() throws Exception {
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
}

class PlayerConnection {
	private Socket socket;
	private BufferedReader input;
	private DataOutputStream output;

	public PlayerConnection(Socket socket) throws Exception {
		this.socket = socket;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new DataOutputStream(socket.getOutputStream());
	}
}