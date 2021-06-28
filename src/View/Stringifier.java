package View;

import Game.Game;

public class Stringifier extends GamePrinter {
		
	public void setGame(Game game) {
		this.game = game;
	}

	public String toString() {
		return game.stringify();
	}

}
