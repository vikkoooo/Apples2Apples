package src.game;

import java.util.*;
import src.cards.Card;

/**
 * Manages the game's card decks including initialization, shuffling, and drawing cards.
 * Maintains separate decks for Red Apples (nouns) and Green Apples (adjectives).
 */
public class DeckManager {
	private IDeckLoader deckLoader;
	private IShuffler shuffler;
	private ArrayList<Card> redApples;
	private ArrayList<Card> greenApples;

	/**
	 * Initializes the deck manager with loader and shuffler implementations.
	 * Loads and shuffles both decks upon creation.
	 * 
	 * @param deckLoader For loading card data
	 * @param shuffler For randomizing card order
	 * @throws Exception if deck initialization fails
	 */
	public DeckManager(IDeckLoader deckLoader, IShuffler shuffler) throws Exception {
		this.deckLoader = deckLoader;
		this.shuffler = shuffler;
		this.redApples = new ArrayList<>();
		this.greenApples = new ArrayList<>();
		initializeDecks();
		shuffleDecks();
	}

	/**
	 * Loads both Red and Green Apple decks using the deck loader
	 */
	private void initializeDecks() {
		this.redApples = deckLoader.loadRedApples();
		this.greenApples = deckLoader.loadGreenApples();
	}

	/**
	 * Randomizes the order of cards in both decks
	 */
	private void shuffleDecks() {
		shuffler.shuffle(redApples);
		shuffler.shuffle(greenApples);
	}

	/**
	 * Draws and removes the top Red Apple card from the deck.
	 * 
	 * @return The drawn Red Apple card
	 * @throws IllegalStateException if the deck is empty
	 */
	public Card drawRedApple() {
		if (redApples.isEmpty()) {
			throw new IllegalStateException("Red apples deck is empty");
		}
		return redApples.remove(0);
	}

	/**
	 * Draws and removes the top Green Apple card from the deck.
	 * 
	 * @return The drawn Green Apple card
	 * @throws IllegalStateException if the deck is empty
	 */
	public Card drawGreenApple() {
		if (greenApples.isEmpty()) {
			throw new IllegalStateException("Green apples deck is empty");
		}
		return greenApples.remove(0);
	}

	/**
	 * Deals a specified number of Red Apple cards to form an initial hand.
	 * 
	 * @param numberOfCards Number of cards to deal
	 * @return ArrayList containing the dealt cards
	 */
	public ArrayList<Card> dealInitialHand(int numberOfCards) {
		ArrayList<Card> hand = new ArrayList<>();
		for (int i = 0; i < numberOfCards; i++) {
			hand.add(drawRedApple());
		}
		return hand;
	}

	/**
	 * Returns a defensive copy of the Red Apples deck.
	 * 
	 * @return Copy of the Red Apples deck
	 */
	public ArrayList<Card> getRedApples() {
		return new ArrayList<>(redApples);
	}

	/**
	 * Returns a defensive copy of the Green Apples deck.
	 * 
	 * @return Copy of the Green Apples deck
	 */
	public ArrayList<Card> getGreenApples() {
		return new ArrayList<>(greenApples);
	}
}