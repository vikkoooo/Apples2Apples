package src.game;

import java.util.*;
import src.cards.Card;

public class DeckManager {
	private ArrayList<Card> redApples;
	private ArrayList<Card> greenApples;
	private IDeckLoader deckLoader;
	private IShuffler shuffler;

	public DeckManager(IDeckLoader deckLoader, IShuffler shuffler) throws Exception {
		this.deckLoader = deckLoader;
		this.shuffler = shuffler;
		this.redApples = new ArrayList<>();
		this.greenApples = new ArrayList<>();
		initializeDecks();
		shuffleDecks();
	}

	private void initializeDecks() {
		this.redApples = deckLoader.loadRedApples();
		this.greenApples = deckLoader.loadGreenApples();
	}

	private void shuffleDecks() {
		shuffler.shuffle(redApples);
		shuffler.shuffle(greenApples);
	}

	public Card drawRedApple() {
		if (redApples.isEmpty()) {
			throw new IllegalStateException("Red apples deck is empty");
		}
		return redApples.remove(0);
	}

	public Card drawGreenApple() {
		if (greenApples.isEmpty()) {
			throw new IllegalStateException("Green apples deck is empty");
		}
		return greenApples.remove(0);
	}

	public ArrayList<Card> dealInitialHand(int numberOfCards) {
		ArrayList<Card> hand = new ArrayList<>();
		for (int i = 0; i < numberOfCards; i++) {
			hand.add(drawRedApple());
		}
		return hand;
	}

	public ArrayList<Card> getRedApples() {
		return new ArrayList<>(redApples);
	}

	public ArrayList<Card> getGreenApples() {
		return new ArrayList<>(greenApples);
	}
}