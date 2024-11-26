package src.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Shuffler implements IShuffler {
	@Override
	public <T> void shuffle(ArrayList<T> deck) {
		Random rnd = ThreadLocalRandom.current();
		Collections.shuffle(deck, rnd);
	}
}