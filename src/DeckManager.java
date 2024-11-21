package src;

import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DeckManager {
	private List<Card> redApples;
	private List<Card> greenApples;
	private CardFactory nounCardFactory;
	private CardFactory adjectiveCardFactory;

	public DeckManager() throws Exception {
		this.redApples = new ArrayList<>();
		this.greenApples = new ArrayList<>();
		this.nounCardFactory = new NounCardFactory();
		this.adjectiveCardFactory = new AdjectiveCardFactory();
		loadCards();
		shuffleDecks();
	}

	private void loadCards() throws Exception {
		List<String> redAppleTexts = Files.readAllLines(
				Paths.get(Constants.RED_APPLES_PATH),
				Charset.forName(Constants.CHARSET));
		List<String> greenAppleTexts = Files.readAllLines(
				Paths.get(Constants.GREEN_APPLES_PATH),
				Charset.forName(Constants.CHARSET));
		for (String text : redAppleTexts) {
			redApples.add(nounCardFactory.createCard(text));
		}
		for (String text : greenAppleTexts) {
			greenApples.add(adjectiveCardFactory.createCard(text));
		}
	}

	private void shuffleDecks() {
		Random rnd = ThreadLocalRandom.current();
		Collections.shuffle(redApples, rnd);
		Collections.shuffle(greenApples, rnd);
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

	public List<Card> dealInitialHand(int numberOfCards) {
		List<Card> hand = new ArrayList<>();
		for (int i = 0; i < numberOfCards; i++) {
			hand.add(drawRedApple());
		}
		return hand;
	}

	public boolean hasRedApples() {
		return !redApples.isEmpty();
	}

	public boolean hasGreenApples() {
		return !greenApples.isEmpty();
	}
}