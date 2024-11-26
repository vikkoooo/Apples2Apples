package src.game;

import java.util.ArrayList;

import src.player.Player;

public interface IPlayerManager {
	void addPlayer(Player player);

	void initializeJudgeIndex();

	Player getJudge();

	void rotateJudge();

	ArrayList<Player> getActivePlayers();
}
