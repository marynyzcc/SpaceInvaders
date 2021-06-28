package Game;

import Game.Lists.GameObjectBoard;
import Game.GameObjects.AlienShip;
import Game.GameObjects.DestroyerAlien;
import Game.GameObjects.RegularAlien;
import Game.GameObjects.Ovni;

public class BoardInitializer {
	private Level level;
	private GameObjectBoard board;
	private Game game;
	private int posRowRegularAlien;
	private AlienShip aShip;
	
	public GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new GameObjectBoard(Game.DIM_ROWS, Game.DIM_COLUMNS);
		
		initializeOvni() ;
		initializeRegularAliens();
		initializeDestroyerAliens();
		return board;
	}
	
	private void initializeOvni () {
		Ovni ovni = new Ovni(this.game, 0, 9, 1);
		board.add(ovni);		
	}
	
	private void initializeRegularAliens () {
		int numRowsRegularAliens = this.level.getNumRowsOfRegularAliens();
		this.posRowRegularAlien = 1;
		
		for(int i = 0; i < numRowsRegularAliens; i++) {
			int posColumRegularAlien = 3;
			
			for(int j = 0; j < level.getNumRegularAliensPerRow(); j++) {
				aShip = new RegularAlien(this.game, this.posRowRegularAlien, posColumRegularAlien, 2);
				board.add(aShip);
				posColumRegularAlien++;
			}
			this.posRowRegularAlien++;
		}
	}
	private void initializeDestroyerAliens () {
		int posColumnDestroyerAlien;
		int posRowDestroyerAlien = posRowRegularAlien;
		
		if(level.getNumDestroyerAliens() == 2) {
			posColumnDestroyerAlien = 4;
			
			for(int i = 0; i < level.getNumDestroyerAliens(); i++) {
				aShip = new DestroyerAlien(this.game, posRowDestroyerAlien, posColumnDestroyerAlien, 1);
				board.add(aShip);
				posColumnDestroyerAlien++;
			}
		}
		else {
			posColumnDestroyerAlien = 3;
			
			for(int i = 0; i < level.getNumDestroyerAliens(); i++) {
				aShip = new DestroyerAlien(this.game, posRowDestroyerAlien, posColumnDestroyerAlien, 1);
				board.add(aShip);
				posColumnDestroyerAlien++;
			}
		}	
	}
}