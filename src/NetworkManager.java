package src;

import java.net.*;

public class NetworkManager {
	private ServerSocket serverSocket;

	public NetworkManager() throws Exception {
		serverSocket = new ServerSocket(Constants.PORT);
	}

	public Player acceptConnection(int playerID) throws Exception {
		Socket socket = serverSocket.accept();
		PlayerConnection connection = new PlayerConnection(socket);
		return new Player(playerID, false, connection);
	}

	public void close() throws Exception {
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
}
