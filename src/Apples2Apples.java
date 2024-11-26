package src;

import src.game.DeckLoader;
import src.game.DeckManager;
import src.game.GameManager;
import src.game.GameRules;
import src.game.IDeckLoader;
import src.game.IGameRules;
import src.game.IPlayerManager;
import src.game.IShuffler;
import src.game.PlayerManager;
import src.game.Shuffler;
import src.network.INetworkManager;
import src.network.NetworkManager;

public class Apples2Apples {
	public static void main(String[] args) {
		try {
			// Create the DeckManager
			IDeckLoader deckLoader = new DeckLoader();
			IShuffler shuffler = new Shuffler();
			DeckManager deckManager = new DeckManager(deckLoader, shuffler);

			// Create other components
			IPlayerManager playerManager = new PlayerManager();
			IGameRules gameRules = new GameRules();
			INetworkManager networkManager = new NetworkManager();

			// Dynamically assign number of players
			int numberOfOnlinePlayers = args.length == 0 ? 0 : Integer.parseInt(args[0]);

			// Create the GameManager
			GameManager gameManager = new GameManager(deckManager, playerManager, gameRules, networkManager);

			gameManager.setupPlayers(numberOfOnlinePlayers);
			gameManager.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}