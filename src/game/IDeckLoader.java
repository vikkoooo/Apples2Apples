package src.game;

import java.util.ArrayList;
import src.cards.Card;

public interface IDeckLoader {
	ArrayList<Card> loadGreenApples();

	ArrayList<Card> loadRedApples();
}