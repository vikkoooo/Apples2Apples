package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Apples2Apples;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class Apples2ApplesTest {

	private Apples2Apples game;

	@BeforeEach
	void setUp() {
		System.out.println("Starting setup");
		try {
			game = new Apples2Apples(0);
			System.out.println("Created game instance"); // I will never reach this line
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception thrown during setup: " + e.getMessage());
		}
		System.out.println("Setup complete");
		// Therefore, I will never reach these lists which I wanted to use in my tests
		game.redApples = new ArrayList<>();
		game.greenApples = new ArrayList<>();
		game.players = new ArrayList<>();
	}

	// TODO: Write tests
	// Test 1: Verify green apples are loaded from the file and added to the deck.
	@Test
	public void testGreenApplesLoaded() {
		try {
			// Use assertion to verify the green apples list is not empty
			assertFalse(game.greenApples.isEmpty(), "Green apples were not loaded from the file.");
		} catch (Exception e) {
			e.printStackTrace();
			// If an exception occurs, fail the test
			assertFalse(true, "Test failed due to exception: " + e.getMessage());
		}
	}

	// Test 2: Verify red apples are loaded from the file and added to the deck.
	// Test 3: Verify both decks are shuffled correctly.
	// Test 4: Verify that each player receives 7 red apples.
	// Test 5: Verify the judge is selected randomly at the start.

}
