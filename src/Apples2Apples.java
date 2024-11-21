package src;

public class Apples2Apples {
	public static void main(String[] args) {
		try {
			GameManager gameManager;
			if (args.length == 0) {
				gameManager = new GameManager(0);
			} else {
				int numberOfOnlinePlayers = Integer.parseInt(args[0]);
				gameManager = new GameManager(numberOfOnlinePlayers);
			}
			gameManager.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}