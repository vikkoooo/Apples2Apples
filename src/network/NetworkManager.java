package src.network;

import java.net.*;
import src.game.Constants;
import src.player.Player;

/**
 * Manages server-side network connections for multiplayer games.
 * Creates a server socket and handles incoming player connections.
 */
public class NetworkManager implements INetworkManager {
	/** Server socket listening for incoming connections */
	private ServerSocket serverSocket;

	/**
	 * Creates a new network manager and opens server socket on configured port.
	 * 
	 * @throws Exception if socket creation fails
	 */
	public NetworkManager() throws Exception {
		serverSocket = new ServerSocket(Constants.PORT);
	}

	/**
	 * Accepts an incoming client connection and creates a new Player instance.
	 * Blocks until a connection is established.
	 * 
	 * @param playerID Unique identifier for the connecting player
	 * @return New Player instance with network connection
	 * @throws Exception if connection acceptance fails
	 */
	public Player acceptConnection(int playerID) throws Exception {
		Socket socket = serverSocket.accept();
		PlayerConnection connection = new PlayerConnection(socket);
		return new Player(playerID, false, connection);
	}

	/**
	 * Closes the server socket and releases network resources.
	 * 
	 * @throws Exception if socket closure fails
	 */
	public void close() throws Exception {
		if (serverSocket != null) {
			serverSocket.close();
		}
	}
}