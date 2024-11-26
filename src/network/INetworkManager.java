package src.network;

import src.player.Player;

public interface INetworkManager {
	Player acceptConnection(int playerID) throws Exception;

	void close() throws Exception;
}