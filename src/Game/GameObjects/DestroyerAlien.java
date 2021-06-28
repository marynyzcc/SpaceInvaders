package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;
import Game.Move;
import Game.GameInterfaces.IExecuteRandomActions;

public class DestroyerAlien extends AlienShip {
	private Bomb bomb;
	private boolean canShootBomb = true;
	private static final int POINTS = 10;
	public static final int LIVE = 1;
	
	public DestroyerAlien(Game game, int r, int c, int l) {
		super(game, r, c, l);
	}
	
	public DestroyerAlien() {}
	
	public boolean canShootBomb() {
		return this.canShootBomb;
	}
	
	public void enableBomb() {
		this.canShootBomb = true;
	}
	
	public void computerAction() {
		if(this.canShootBomb && IExecuteRandomActions.canGenerateRandomBomb(game)) {
			bomb = new Bomb(game, this.row, this.column, 1, this);
			game.addObject(bomb);
			this.canShootBomb = false;
		}
	}
			
	public void addPoints() {
		game.receivePoints(POINTS);
	}
		
	//SAVE
	private static int currentSerialNumber;

	private void initialiseLabelling() {
		currentSerialNumber = 1;
	}

	private String generateSerializingLabel() {
		label = currentSerialNumber;
		currentSerialNumber++;
		return labelRefSeparator + label;
	}
	
	public String toString() {
		return "D[" + this.live + "]";
	}
	
	public String stringify() {
		if (!game.isSerializing()) {
			game.setSerializing();
			initialiseLabelling();
		}
		
		return "D;" + this.row + "," + this.column + ";" + 
			this.live + ";" + cyclesToMove + ";" + this.direction + ";" + SHIPS_ON_BORDER + generateSerializingLabel();
	}
		
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		
		if(verifier.verifyDestroyerShipString(stringFromFile, game2, LIVE))  {
			String[] words = stringFromFile.split(";");
			String[] label = words[5].split(" - ");
			String[] coords = words[1].split(",");
			
			DestroyerAlien dAlien = new DestroyerAlien();
			dAlien.game = game2;
			dAlien.row = Integer.parseInt(coords[0]);
			dAlien.column = Integer.parseInt(coords[1]);
			dAlien.live = Integer.parseInt(words[2]);
			dAlien.cyclesToMove = Integer.parseInt(words[3]);
			dAlien.direction = Move.parse(words[4]);
			AlienShip.SHIPS_ON_BORDER = Integer.parseInt(label[0]); 
			dAlien.label = Integer.parseInt(label[1]);
									
			return dAlien;
		}
		
		return null;
	}
}