package src.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements card shuffling functionality using ThreadLocalRandom for thread safety.
 * Uses Java Collections.shuffle() with a custom random source for reliable randomization.
 */
public class Shuffler implements IShuffler {
	/**
	 * Randomly reorders elements in the provided ArrayList.
	 * Uses ThreadLocalRandom for better concurrent performance than shared Random.
	 * 
	 * @param <T> Generic type parameter allowing any card type
	 * @param deck The ArrayList to be shuffled
	 */
	@Override
	public <T> void shuffle(ArrayList<T> deck) {
		Random rnd = ThreadLocalRandom.current();
		Collections.shuffle(deck, rnd);
	}
}