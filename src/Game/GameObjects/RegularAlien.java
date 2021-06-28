package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;
import Game.Move;
import Game.GameInterfaces.IExecuteRandomActions;

public class RegularAlien extends AlienShip {
	protected static final int POINTS = 5;
	public static final int LIVE = 2;
	
	public RegularAlien(Game game, int r, int c, int l) {
		super(game, r, c, l);
	}
	
	public RegularAlien() {}
		
	public void computerAction() {
		if(IExecuteRandomActions.canConvertInExplosiveAlien(game)) {
			AlienShip eAlien = new ExplosiveAlien(game, this.row, this.column, 2, this.cyclesToMove, SHIPS_ON_BORDER, direction);
			game.addObjectInPos(this.row, this.column, eAlien);
			REMAINING_ALIENS -= 1;
		}
	}
	
	public void addPoints() {
		game.receivePoints(POINTS);
	}
	
	public String stringify() {
		return "R;" + this.row + "," + this.column + ";" + 
			   this.live + ";" + cyclesToMove + ";" + this.direction + ";" + SHIPS_ON_BORDER;
	}
	
	public String toString() {
		return "R[" + this.live + "]";
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		String[] words = stringFromFile.split(";");
		String[] coords = words[1].split(",");
		
		if(words[0].toUpperCase().equals("R") && verifier.verifyAlienShipString(stringFromFile, game2, LIVE)) {
			
			RegularAlien rAlien = new RegularAlien();
			rAlien.game = game2;
			rAlien.row = Integer.parseInt(coords[0]);
			rAlien.column = Integer.parseInt(coords[1]);
			rAlien.live = Integer.parseInt(words[2]);
			rAlien.cyclesToMove = Integer.parseInt(words[3]);
			rAlien.direction = Move.parse(words[4]);
			AlienShip.SHIPS_ON_BORDER = Integer.parseInt(words[5]);
			
			if(!game2.loading()) {
				game2.setLoading(true);
				REMAINING_ALIENS = 1;
			}
			else 
				REMAINING_ALIENS += 1;
				
			return rAlien;
		}
		
		return null;
	}
}