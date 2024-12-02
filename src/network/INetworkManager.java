package src.network;

import src.player.Player;

/**
 * Interface defining network connection management operations.
 * Handles accepting new player connections and cleanup of network resources.
 */
public interface INetworkManager {
	/**
	 * Accepts a new network connection and creates a Player instance.
	 * 
	 * @param playerID Unique identifier for the connecting player
	 * @return New Player instance configured with network connection
	 * @throws Exception if connection acceptance fails
	 */
	Player acceptConnection(int playerID) throws Exception;

	/**
	 * Closes all network connections and releases resources.
	 * Should be called when shutting down the game server.
	 * 
	 * @throws Exception if cleanup operations fail
	 */
	void close() throws Exception;
}