package src.game;

import java.util.ArrayList;

/**
 * Interface defining the shuffling behavior for card collections.
 * Uses generic type to allow shuffling of any card type (Red or Green Apples).
 */
public interface IShuffler {
	/**
	 * Randomly reorders elements in the provided ArrayList.
	 * 
	 * @param <T> Generic type parameter allowing any card type
	 * @param deck The ArrayList to be shuffled
	 */
	<T> void shuffle(ArrayList<T> deck);
}