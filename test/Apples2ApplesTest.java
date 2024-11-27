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
		ArrayList<Card> loadedCards = mockDeckLoader.loadGreenApples();

		// Assert
		assertEquals(2, loadedCards.size());
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
		ArrayList<Card> loadedCards = mockDeckLoader.loadRedApples();

		// Assert
		assertEquals(2, loadedCards.size());
		verify(mockDeckLoader, times(2)).loadRedApples();
	}

	// Test 3. Shuffle both the green apples and red apples decks.
	@Test
	void testShuffleDeck() {
		// Arrange
		ArrayList<Card> deck = new ArrayList<>();
		deck.add(new RedApple("Card1"));
		deck.add(new RedApple("Card2"));

		// Act
		mockShuffler.shuffle(deck);

		// Assert
		verify(mockShuffler).shuffle(deck);
	}

	// TODO: Test 4. Deal seven red apples to each player, including the judge.

	// TODO: Test 5. Randomise which player starts being the judge.

}
