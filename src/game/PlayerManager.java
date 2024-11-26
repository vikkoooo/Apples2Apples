package src.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import src.player.Player;

public class PlayerManager implements IPlayerManager {
	private ArrayList<Player> players;
	private int currentJudgeIndex;

	public PlayerManager() {
		this.players = new ArrayList<Player>();
		this.currentJudgeIndex = 0;
	}

	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}

	@Override
	public void initializeJudgeIndex() {
		currentJudgeIndex = ThreadLocalRandom.current().nextInt(players.size());
	}

	@Override
	public Player getJudge() {
		return players.get(currentJudgeIndex);
	}

	@Override
	public void rotateJudge() {
		currentJudgeIndex = (currentJudgeIndex + 1) % players.size();
	}

	@Override
	public ArrayList<Player> getActivePlayers() {
		return new ArrayList<>(players);
	}
}
