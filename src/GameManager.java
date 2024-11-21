package src;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class GameManager {
	private ArrayList<Player> players;
	private DeckManager deckManager;
	private NetworkManager networkManager;
	private int judge;
	private ArrayList<PlayedApple> playedApples;

	public GameManager(int numberOfOnlinePlayers) throws Exception {
		this.players = new ArrayList<>();
		this.deckManager = new DeckManager();
		this.networkManager = new NetworkManager();
		this.playedApples = new ArrayList<>();
		setupPlayers(numberOfOnlinePlayers);
	}

	private void setupPlayers(int numberOfOnlinePlayers) throws Exception {
		// Connect online players
		for (int i = 0; i < numberOfOnlinePlayers; i++) {
			Player player = networkManager.acceptConnection(i);
			// Convert Cards to strings for network transmission
			List<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			String handString = hand.stream()
					.map(Card::getText)
					.collect(Collectors.joining(";"));
			player.getOutToClient().writeMessage(handString);
			players.add(player);
			System.out.println("Connected to Player ID: " + i);
		}

		// Add bots if needed
		for (int i = numberOfOnlinePlayers; i < Constants.MIN_PLAYERS - 1; i++) {
			List<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			List<String> handAsStrings = hand.stream()
					.map(Card::getText)
					.collect(Collectors.toList());
			players.add(new Player(i, new ArrayList<>(handAsStrings), true));
		}

		// Add server player
		List<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
		List<String> handAsStrings = hand.stream()
				.map(Card::getText)
				.collect(Collectors.toList());
		players.add(new Player(players.size(), new ArrayList<>(handAsStrings), false));
	}

	public void startGame() throws Exception {
		boolean finished = false;
		judge = ThreadLocalRandom.current().nextInt(players.size());

		while (!finished) {
			playRound();
			finished = checkWinCondition();
			judge = (judge + 1) % players.size();
		}
	}

	private void playRound() throws Exception {
		System.out.println("*****************************************************");
		if (judge == players.size() - 1) {
			System.out.println("**                 NEW ROUND - JUDGE               **");
		} else {
			System.out.println("**                    NEW ROUND                    **");
		}
		System.out.println("*****************************************************");

		// Get green apple card and convert to string for display
		Card greenAppleCard = deckManager.drawGreenApple();
		String playedGreenApple = greenAppleCard.getText();
		System.out.println("Green apple: " + playedGreenApple + "\n");

		for (Player player : players) {
			if (player != players.get(judge)) {
				player.play(playedApples);
			}
		}

		Collections.shuffle(playedApples, ThreadLocalRandom.current());

		System.out.println("\nThe following apples were played:");
		for (int i = 0; i < playedApples.size(); i++) {
			System.out.println("[" + i + "] " + playedApples.get(i).redApple);
		}

		PlayedApple winningApple = players.get(judge).judge(playedApples);
		players.get(winningApple.playerID).getGreenApples().add(playedGreenApple);

		System.out.println("Player ID " + winningApple.playerID + " won with: " + winningApple.redApple + "\n");

		playedApples.clear();

		for (Player player : players) {
			if (player != players.get(judge)) {
				Card redCard = deckManager.drawRedApple();
				player.addCard(redCard.getText());
			}
		}
	}

	private boolean checkWinCondition() {
		for (Player player : players) {
			if (player.getGreenApples().size() >= Constants.WIN_CONDITION) {
				System.out.println("Player ID " + player.getGreenApples().size() + " won the game!");
				return true;
			}
		}
		return false;
	}
}