package src.game;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import src.cards.Card;
import src.cards.factory.AdjectiveCardFactory;
import src.cards.factory.NounCardFactory;

/**
 * Handles loading card data from files and creating card instances using factories.
 * Implements lazy loading pattern for card decks to optimize memory usage.
 */
public class DeckLoader implements IDeckLoader {
	// Factory instances for creating different card types
	private AdjectiveCardFactory adjectiveCardFactory;
	private NounCardFactory nounCardFactory;

	// Cache for loaded card decks
	private ArrayList<Card> greenApples;
	private ArrayList<Card> redApples;

	/**
	 * Initializes the loader with card factories and empty deck caches.
	 */
	public DeckLoader() {
		this.adjectiveCardFactory = new AdjectiveCardFactory();
		this.nounCardFactory = new NounCardFactory();
		this.greenApples = new ArrayList<>();
		this.redApples = new ArrayList<>();
	}

	/**
	 * Loads and returns the Green Apple cards deck.
	 * Implements lazy loading - only reads from file on first request.
	 * Returns a new copy of the deck to prevent modifications to the cache.
	 * 
	 * @return A new ArrayList containing all Green Apple cards
	 * @throws RuntimeException if card loading fails
	 */
	@Override
	public ArrayList<Card> loadGreenApples() {
		if (greenApples.isEmpty()) {
			try {
				loadGreenCardsFromFile();
			} catch (Exception e) {
				throw new RuntimeException("Failed to load green apples", e);
			}
		}
		return new ArrayList<>(greenApples);
	}

	/**
	 * Loads and returns the Red Apple cards deck.
	 * Implements lazy loading - only reads from file on first request.
	 * Returns a new copy of the deck to prevent modifications to the cache.
	 * 
	 * @return A new ArrayList containing all Red Apple cards
	 * @throws RuntimeException if card loading fails
	 */
	@Override
	public ArrayList<Card> loadRedApples() {
		if (redApples.isEmpty()) {
			try {
				loadRedCardsFromFile();
			} catch (Exception e) {
				throw new RuntimeException("Failed to load red apples", e);
			}
		}
		return new ArrayList<>(redApples);
	}

	/**
	 * Helper method to load Green Apple cards from file.
	 * Reads the file and creates card instances using the adjective factory.
	 * 
	 * @throws Exception if file reading fails
	 */
	private void loadGreenCardsFromFile() throws Exception {
		List<String> greenAppleTexts = Files.readAllLines(
				Paths.get(Constants.GREEN_APPLES_PATH),
				Charset.forName(Constants.CHARSET));

		for (String text : greenAppleTexts) {
			greenApples.add(adjectiveCardFactory.createCard(text));
		}
	}

	/**
	 * Helper method to load Red Apple cards from file.
	 * Reads the file and creates card instances using the noun factory.
	 * 
	 * @throws Exception if file reading fails
	 */
	private void loadRedCardsFromFile() throws Exception {
		List<String> redAppleTexts = Files.readAllLines(
				Paths.get(Constants.RED_APPLES_PATH),
				Charset.forName(Constants.CHARSET));

		for (String text : redAppleTexts) {
			redApples.add(nounCardFactory.createCard(text));
		}
	}
}