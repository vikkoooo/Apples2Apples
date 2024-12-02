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

/**
 * Main class for the Apples to Apples card game.
 * Initializes and starts the game with either local or networked players.
 */
public class Apples2Apples {
	/**
	 * Entry point for the application.
	 * Sets up the game components and starts a new game session.
	 * 
	 * @param args Command line arguments:
	 *             - No args: Creates a local game with 3 bots and 1 human player
	 *             - One arg: Creates a networked game waiting for the specified number of online players
	 *             - IP address: Runs in client mode and connects to the specified server
	 */
	public static void main(String[] args) {
		try {
			// Initialize core game components
			IDeckLoader deckLoader = new DeckLoader();
			IShuffler shuffler = new Shuffler();
			DeckManager deckManager = new DeckManager(deckLoader, shuffler);

			// Initialize game management components
			IPlayerManager playerManager = new PlayerManager();
			IGameRules gameRules = new GameRules();
			INetworkManager networkManager = new NetworkManager();

			// Parse command line args to determine game mode
			// If no args provided, default to local game with no online players
			int numberOfOnlinePlayers = args.length == 0 ? 0 : Integer.parseInt(args[0]);

			// Create game manager and configure the game session
			GameManager gameManager = new GameManager(deckManager, playerManager, gameRules, networkManager);

			// Set up players based on specified game mode
			gameManager.setupPlayers(numberOfOnlinePlayers);

			// Begin the game loop
			gameManager.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}