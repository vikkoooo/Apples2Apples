package src.network;

import java.net.*;

import src.game.Constants;
import src.player.Player;

public class NetworkManager implements INetworkManager {
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
