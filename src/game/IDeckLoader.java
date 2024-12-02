package src.game;

import java.util.ArrayList;
import src.cards.Card;

/**
 * Interface defining contract for loading card decks from external sources.
 * Provides methods to load both Green Apple (adjective) and Red Apple (noun) cards.
 */
public interface IDeckLoader {
	/**
	 * Loads the Green Apple (adjective) cards from storage.
	 * 
	 * @return ArrayList containing all Green Apple cards
	 * @throws RuntimeException if loading fails
	 */
	ArrayList<Card> loadGreenApples();

	/**
	 * Loads the Red Apple (noun) cards from storage.
	 * 
	 * @return ArrayList containing all Red Apple cards
	 * @throws RuntimeException if loading fails
	 */
	ArrayList<Card> loadRedApples();
}