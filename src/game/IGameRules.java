package src.game;

import src.player.Player;
import java.util.ArrayList;

public interface IGameRules {
	boolean isGameOver(ArrayList<Player> players);

	int getWinningScore(int playerCount);
}