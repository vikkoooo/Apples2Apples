package src.game;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import src.cards.Card;
import src.cards.factory.AdjectiveCardFactory;
import src.cards.factory.NounCardFactory;

public class DeckLoader implements IDeckLoader {
	private AdjectiveCardFactory adjectiveCardFactory;
	private NounCardFactory nounCardFactory;
	private ArrayList<Card> greenApples;
	private ArrayList<Card> redApples;

	public DeckLoader() {
		this.adjectiveCardFactory = new AdjectiveCardFactory();
		this.nounCardFactory = new NounCardFactory();
		this.greenApples = new ArrayList<>();
		this.redApples = new ArrayList<>();
	}

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

	private void loadGreenCardsFromFile() throws Exception {
		List<String> greenAppleTexts = Files.readAllLines(
				Paths.get(Constants.GREEN_APPLES_PATH),
				Charset.forName(Constants.CHARSET));

		for (String text : greenAppleTexts) {
			greenApples.add(adjectiveCardFactory.createCard(text));
		}
	}

	private void loadRedCardsFromFile() throws Exception {
		List<String> redAppleTexts = Files.readAllLines(
				Paths.get(Constants.RED_APPLES_PATH),
				Charset.forName(Constants.CHARSET));

		for (String text : redAppleTexts) {
			redApples.add(nounCardFactory.createCard(text));
		}
	}
}
