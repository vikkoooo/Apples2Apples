package src;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DeckManager {
	private ArrayList<String> redApples;
	private ArrayList<String> greenApples;

	public DeckManager() throws Exception {
		loadCards();
		shuffleDecks();
	}

	private void loadCards() throws Exception {
		redApples = new ArrayList<>(
				Files.readAllLines(Paths.get("./src/", "redApples.txt"), StandardCharsets.ISO_8859_1));
		greenApples = new ArrayList<>(
				Files.readAllLines(Paths.get("./src/", "greenApples.txt"), StandardCharsets.ISO_8859_1));
	}

	private void shuffleDecks() {
		Random rnd = ThreadLocalRandom.current();
		Collections.shuffle(redApples, rnd);
		Collections.shuffle(greenApples, rnd);
	}

	public String drawRedApple() {
		return redApples.remove(0);
	}

	public String drawGreenApple() {
		return greenApples.remove(0);
	}

	public List<String> dealInitialHand() {
		List<String> hand = new ArrayList<>();
		for (int i = 0; i < Constants.INITIAL_HAND_SIZE; i++) {
			hand.add(redApples.remove(0));
		}
		return hand;
	}
}