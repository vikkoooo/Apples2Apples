package src.game;

import java.util.ArrayList;
import src.player.Player;

public class GameRules implements IGameRules {
	@Override
	public boolean isGameOver(ArrayList<Player> players) {
		for (Player player : players) {
			if (player.getGreenApples().size() >= getWinningScore(players.size())) {
				System.out.println("Player ID " + player.getGreenApples().size() + " won the game!");
				return true;
			}
		}
		return false;
	}

	@Override
	public int getWinningScore(int playerCount) {
		if (playerCount <= 4)
			return 8;
		if (playerCount == 5)
			return 7;
		if (playerCount == 6)
			return 6;
		if (playerCount == 7)
			return 5;
		return 4; // 8+ players
	}
}