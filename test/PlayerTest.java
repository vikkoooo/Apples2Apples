package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import src.cards.Card;
import src.cards.RedApple;
import src.network.PlayerConnection;
import src.player.Player;
import src.player.BotPlayerStrategy;
import src.player.OfflinePlayerStrategy;
import src.player.OnlinePlayerStrategy;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Tests for Players might be unnecessary, but we need to make sure that we can
// create Players successfully, before we can continue with test cases 4-15 in
// Apples2ApplesTest.java
class PlayerTest {

	@Mock
	private PlayerConnection mockConnection;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testOfflinePlayerInitialization() {
		// Arrange
		int playerID = 1;
		ArrayList<Card> initialHand = new ArrayList<>();
		initialHand.add(new RedApple("Card1"));
		initialHand.add(new RedApple("Card2"));

		// Act
		Player player = new Player(playerID, initialHand, false);

		// Assert
		assertEquals(playerID, player.getPlayerID());
		assertEquals(2, player.getHand().size());
		assertTrue(player.getStrategy() instanceof OfflinePlayerStrategy);
	}

	@Test
	void testBotPlayerInitialization() {
		// Arrange
		int playerID = 2;
		ArrayList<Card> initialHand = new ArrayList<>();
		initialHand.add(new RedApple("Card1"));
		initialHand.add(new RedApple("Card2"));

		// Act
		Player player = new Player(playerID, initialHand, true);

		// Assert
		assertEquals(playerID, player.getPlayerID());
		assertEquals(2, player.getHand().size());
		assertTrue(player.getStrategy() instanceof BotPlayerStrategy);
	}

	@Test
	void testOnlinePlayerInitialization() {
		// Arrange
		int playerID = 3;

		// Act
		Player player = new Player(playerID, false, mockConnection);

		// Assert
		assertEquals(playerID, player.getPlayerID());
		assertEquals(0, player.getHand().size());
		assertTrue(player.getStrategy() instanceof OnlinePlayerStrategy);
	}

	@Test
	void testAddCard() {
		// Arrange
		int playerID = 4;
		Player player = new Player(playerID, new ArrayList<>(), false);
		Card card = new RedApple("Card1");

		// Act
		player.addCard(card);

		// Assert
		assertEquals(1, player.getHand().size());
		assertEquals(card, player.getHand().get(0));
	}
}