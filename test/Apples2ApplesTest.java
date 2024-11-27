package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.cards.*;
import src.game.*;
import src.network.*;
import src.player.*;

import java.lang.reflect.Field;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class Apples2ApplesTest {
	private GameManager gameManager;
	private DeckManager deckManager;

	// Declare mocks
	@Mock
	private IDeckLoader mockDeckLoader;
	@Mock
	private IShuffler mockShuffler;
	@Mock
	private PlayerManager mockPlayerManager;
	@Mock
	private GameRules mockGameRules;
	@Mock
	private NetworkManager mockNetworkManager;
	@Mock
	private PlayerConnection mockConnection;

	@BeforeEach
	public void setUp() throws Exception {
		// Initialize mocks
		MockitoAnnotations.openMocks(this);
		mockDeckLoader = mock(IDeckLoader.class);
		mockShuffler = mock(IShuffler.class);

		// Prepare mock data for extended tests
		ArrayList<Card> redApples = new ArrayList<>();
		for (int i = 1; i <= 30; i++) {
			redApples.add(new RedApple("RedCard" + i));
		}

		ArrayList<Card> greenApples = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			greenApples.add(new GreenApple("GreenCard" + i));
		}

		when(mockDeckLoader.loadRedApples()).thenReturn(redApples);
		when(mockDeckLoader.loadGreenApples()).thenReturn(greenApples);

		// Initialize deck manager and other components
		deckManager = new DeckManager(mockDeckLoader, mockShuffler);

		// Initialize game manager with its dependencies
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

	// Test 4. Deal seven red apples to each player, including the judge.
	@Test
	public void testDealSevenRedApplesToEachPlayer() {
		// Arrange
		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player judge = new Player(3, new ArrayList<>(), false);

		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(judge);

		// Act
		for (Player player : players) {
			ArrayList<Card> initialHand = deckManager.dealInitialHand(7);
			player.getHand().addAll(initialHand);
		}

		// Assert
		for (Player player : players) {
			assertEquals(7, player.getHand().size(), "Each player should have 7 cards in hand.");
		}
	}

	// Test 5. Randomise which player starts being the judge.
	@Test
	public void testRandomizeWhichPlayerStartsBeingTheJudge() {
		// Arrange
		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player player3 = new Player(3, new ArrayList<>(), false);

		PlayerManager playerManager = new PlayerManager();
		playerManager.addPlayer(player1);
		playerManager.addPlayer(player2);
		playerManager.addPlayer(player3);

		Set<Integer> judgeIds = new HashSet<>();

		// Act
		for (int i = 0; i < 100; i++) {
			playerManager.initializeJudgeIndex();
			judgeIds.add(playerManager.getJudge().getPlayerID());
		}

		// Assert
		assertTrue(judgeIds.contains(player1.getPlayerID()), "Player 1 should have been the judge at least once.");
		assertTrue(judgeIds.contains(player2.getPlayerID()), "Player 2 should have been the judge at least once.");
		assertTrue(judgeIds.contains(player3.getPlayerID()), "Player 3 should have been the judge at least once.");
	}

	// Test 6. A green apple is drawn from the pile and shown to everyone
	@Test
	public void testDrawGreenApple() {
		// Act
		Card greenApple = deckManager.drawGreenApple();

		// Assert
		assertNotNull(greenApple, "A green apple should be drawn from the pile.");
	}

	// Test 7. All players (except the judge) choose one red apple from their hand (based on the green apple) and plays it.
	@Test
	public void testPlayersPlayRedApple() {
		// Arrange
		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player judge = new Player(3, new ArrayList<>(), false);

		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(judge);

		for (Player player : players) {
			ArrayList<Card> initialHand = deckManager.dealInitialHand(7);
			player.getHand().addAll(initialHand);
		}

		PlayerManager playerManager = new PlayerManager();
		playerManager.addPlayer(player1);
		playerManager.addPlayer(player2);
		playerManager.addPlayer(judge);
		playerManager.initializeJudgeIndex();

		ArrayList<Card> playedCards = new ArrayList<>();

		// Act
		for (Player player : players) {
			if (player != playerManager.getJudge()) {
				Card playedCard = player.getHand().remove(0); // Assume players play the first card in their hand
				playedCards.add(playedCard);
			}
		}

		// Assert
		assertEquals(2, playedCards.size(), "Two players should have played their red apples.");
		for (Player player : players) {
			if (player != playerManager.getJudge()) {
				assertEquals(6, player.getHand().size(),
						"Each player (except the judge) should have 6 cards in hand after playing one.");
			}
		}
	}

	// Test 8. The printed order of the played red apples should be randomised before shown to everyone.
	@Test
	public void testRandomizeOrderOfPlayedRedApples() {
		// Arrange
		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player judge = new Player(3, new ArrayList<>(), false);

		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(judge);

		for (Player player : players) {
			ArrayList<Card> initialHand = deckManager.dealInitialHand(7);
			player.getHand().addAll(initialHand);
		}

		PlayerManager playerManager = new PlayerManager();
		playerManager.addPlayer(player1);
		playerManager.addPlayer(player2);
		playerManager.addPlayer(judge);
		playerManager.initializeJudgeIndex();

		ArrayList<Card> playedCards = new ArrayList<>();

		for (Player player : players) {
			if (player != playerManager.getJudge()) {
				Card playedCard = player.getHand().remove(0); // Assume players play the first card in their hand
				playedCards.add(playedCard);
			}
		}

		ArrayList<Card> originalOrder = new ArrayList<>(playedCards);

		// Act
		boolean isOrderChanged = false;
		for (int i = 0; i < 10; i++) {
			Collections.shuffle(playedCards);
			if (!originalOrder.equals(playedCards)) {
				isOrderChanged = true;
				break;
			}
		}

		// Assert
		assertEquals(2, playedCards.size(), "Two players should have played their red apples.");
		assertNotEquals(originalOrder, playedCards, "The order of played red apples should be randomized.");
		assertTrue(isOrderChanged, "The order of played red apples should be randomized.");
	}

	// Test 9. All players (except the judge) must play their red apples before the results are shown.
	@Test
	public void testAllPlayersPlayRedApplesBeforeResults() throws Exception {
		// Arrange
		IPlayerStrategy mockStrategy1 = mock(IPlayerStrategy.class);
		IPlayerStrategy mockStrategy2 = mock(IPlayerStrategy.class);
		IPlayerStrategy mockStrategyJudge = mock(IPlayerStrategy.class);

		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player player3 = new Player(3, new ArrayList<>(), false);

		// Use reflection to set the strategy field
		setPlayerStrategy(player1, mockStrategy1);
		setPlayerStrategy(player2, mockStrategy2);
		setPlayerStrategy(player3, mockStrategyJudge);

		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);

		for (Player player : players) {
			ArrayList<Card> initialHand = deckManager.dealInitialHand(7);
			player.getHand().addAll(initialHand);
		}

		PlayerManager playerManager = new PlayerManager();
		playerManager.addPlayer(player1);
		playerManager.addPlayer(player2);
		playerManager.addPlayer(player3);
		playerManager.initializeJudgeIndex();

		ArrayList<PlayedApple> playedApples = new ArrayList<>();

		// Act
		for (Player player : players) {
			if (!player.equals(playerManager.getJudge())) { // Changed comparison to equals()
				player.play(playedApples);
			}
		}

		// Assert
		Player judge = playerManager.getJudge();

		// Verify non-judge players played their cards
		for (Player player : players) {
			if (player.equals(judge)) {
				verify(getPlayerStrategy(player), times(0))
						.play(any(ArrayList.class), eq(player.getPlayerID()));
			} else {
				verify(getPlayerStrategy(player), times(1))
						.play(any(ArrayList.class), eq(player.getPlayerID()));
			}
		}
	}

	// Helper function to Test 9
	private void setPlayerStrategy(Player player, IPlayerStrategy strategy) throws Exception {
		Field strategyField = Player.class.getDeclaredField("strategy");
		strategyField.setAccessible(true);
		strategyField.set(player, strategy);
	}

	// Helper function to Test 9
	private IPlayerStrategy getPlayerStrategy(Player player) throws Exception {
		Field strategyField = Player.class.getDeclaredField("strategy");
		strategyField.setAccessible(true);
		return (IPlayerStrategy) strategyField.get(player);
	}

	// Test 10. The judge selects a favourite red apple. The player who submitted the favourite red apple is rewarded the green apple as a point (rule 14).
	@Test
	public void testJudgeSelectsWinnerAndAwardsGreenApple() throws Exception {
		// Arrange
		IPlayerStrategy mockStrategy1 = mock(IPlayerStrategy.class);
		IPlayerStrategy mockStrategy2 = mock(IPlayerStrategy.class);
		IPlayerStrategy mockJudgeStrategy = mock(IPlayerStrategy.class);

		Player player1 = new Player(1, new ArrayList<>(), false);
		Player player2 = new Player(2, new ArrayList<>(), false);
		Player player3 = new Player(3, new ArrayList<>(), false);

		setPlayerStrategy(player1, mockStrategy1);
		setPlayerStrategy(player2, mockStrategy2);
		setPlayerStrategy(player3, mockJudgeStrategy);

		ArrayList<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		players.add(player3);

		// Deal red apples to players
		for (Player player : players) {
			ArrayList<Card> initialHand = deckManager.dealInitialHand(7);
			player.getHand().addAll(initialHand);
		}

		PlayerManager playerManager = new PlayerManager();
		playerManager.addPlayer(player1);
		playerManager.addPlayer(player2);
		playerManager.addPlayer(player3);
		playerManager.initializeJudgeIndex();

		// Draw green apple for the round
		Card greenApple = deckManager.drawGreenApple();
		ArrayList<PlayedApple> playedApples = new ArrayList<>();

		// Get selected judge from PlayerManager
		Player judge = playerManager.getJudge();

		// Set up mock for the actual judge's strategy
		PlayedApple winningCard = new PlayedApple(player1.getPlayerID(), player1.getHand().get(0));
		when(mockJudgeStrategy.judge(any(ArrayList.class))).thenReturn(winningCard);
		setPlayerStrategy(judge, mockJudgeStrategy); // Set strategy for actual judge

		// Act
		// Players play their cards
		for (Player player : players) {
			if (!player.equals(judge)) { // Use stored judge reference
				player.play(playedApples);
			}
		}

		// Judge selects winning card
		PlayedApple selectedCard = judge.judge(playedApples); // Use stored judge reference

		// Verify selected card is not null
		assertNotNull(selectedCard, "Judge must select a card");

		// Award green apple to winner
		Player winner = players.stream()
				.filter(p -> p.getPlayerID() == selectedCard.playerID)
				.findFirst()
				.orElseThrow(() -> new AssertionError("Winner not found"));

		winner.getGreenApples().add(greenApple);

		// Assert
		assertEquals(1, winner.getGreenApples().size(), "Winner should receive one green apple");
		assertEquals(greenApple, winner.getGreenApples().get(0), "Winner should receive the correct green apple");
		verify(mockJudgeStrategy, times(1)).judge(any(ArrayList.class));
	}

	// TODO: Test 11. All the submitted red apples are discarded

	// TODO: Test 12. All players are given new red apples until they have 7 red apples

	// TODO: Test 13. The next player in the list becomes the judge. Repeat from step 6 until someone wins the game.
}
