package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.Apples2Apples;
import src.cards.*;
import src.game.*;
import src.network.*;
import src.player.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Apples2ApplesTest {

	//private Apples2Apples game;
	private GameManager gameManager;

	@Mock
	private DeckLoader mockDeckLoader;
	@Mock
	private Shuffler mockShuffler;
	@Mock
	private PlayerManager mockPlayerManager;
	@Mock
	private GameRules mockGameRules;
	@Mock

	private NetworkManager mockNetworkManager;
	private DeckManager deckManager;

	private static final int CARDS_PER_PLAYER = 7;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		deckManager = new DeckManager(mockDeckLoader, mockShuffler);
		gameManager = new GameManager(
				deckManager,
				mockPlayerManager,
				mockGameRules,
				mockNetworkManager);
	}

	// Test 1. Read all the green apples (adjectives) from a file and add to the green apples deck.
	@Test
	void testLoadGreenApplesFromFile() {
		// Arrange
		ArrayList<Card> expectedGreenApples = new ArrayList<>();
		expectedGreenApples.add(new GreenApple("Funny"));
		expectedGreenApples.add(new GreenApple("Scary"));
		when(mockDeckLoader.loadGreenApples()).thenReturn(expectedGreenApples);

		// Act
		ArrayList<Card> actualGreenApples = mockDeckLoader.loadGreenApples();

		// Assert
		assertEquals(expectedGreenApples.size(), actualGreenApples.size());
		assertTrue(actualGreenApples.containsAll(expectedGreenApples));
		verify(mockDeckLoader, times(2)).loadGreenApples();
	}

	// Test 2. Read all the red apples (nouns) from a file and add to the red apples deck.
	@Test
	void testLoadRedApplesFromFile() {
		// Arrange
		ArrayList<Card> expectedRedApples = new ArrayList<>();
		expectedRedApples.add(new RedApple("Cat"));
		expectedRedApples.add(new RedApple("Pizza"));
		when(mockDeckLoader.loadRedApples()).thenReturn(expectedRedApples);

		// Act
		ArrayList<Card> actualRedApples = mockDeckLoader.loadRedApples();

		// Assert
		assertEquals(expectedRedApples.size(), actualRedApples.size());
		assertTrue(actualRedApples.containsAll(expectedRedApples));
		verify(mockDeckLoader, times(2)).loadRedApples();
	}

	// Test 3. Shuffle both the green apples and red apples decks.
	@Test
	void testShuffleDecks() {
		// Arrange
		Shuffler realShuffler = new Shuffler();

		// Red deck setup
		ArrayList<Card> redDeck = new ArrayList<>();
		redDeck.add(new RedApple("Card1"));
		redDeck.add(new RedApple("Card2"));
		redDeck.add(new RedApple("Card3"));
		redDeck.add(new RedApple("Card4"));
		redDeck.add(new RedApple("Card5"));
		redDeck.add(new RedApple("Card6"));
		ArrayList<Card> originalRedDeck = new ArrayList<>(redDeck);

		// Green deck setup
		ArrayList<Card> greenDeck = new ArrayList<>();
		greenDeck.add(new GreenApple("Funny"));
		greenDeck.add(new GreenApple("Scary"));
		greenDeck.add(new GreenApple("Serious"));
		greenDeck.add(new GreenApple("Exciting"));
		greenDeck.add(new GreenApple("Boring"));
		ArrayList<Card> originalGreenDeck = new ArrayList<>(greenDeck);

		// Act
		realShuffler.shuffle(redDeck);
		realShuffler.shuffle(greenDeck);

		// Assert
		// Verify the red deck has been shuffled
		assertNotEquals(originalRedDeck, redDeck, "Red deck order should be different after shuffle.");
		// Verify the green deck has been shuffled
		assertNotEquals(originalGreenDeck, greenDeck, "Green deck order should be different after shuffle.");
	}

	// TODO: Test 4. Deal seven red apples to each player, including the judge.

	// TODO: Test 5. Randomise which player starts being the judge.

}
