package src.game;

import java.util.ArrayList;

public interface IShuffler {
	<T> void shuffle(ArrayList<T> deck);
}