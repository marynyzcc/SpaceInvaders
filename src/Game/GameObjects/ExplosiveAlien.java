package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;
import Game.Move;

public class ExplosiveAlien extends RegularAlien {
	private int numAdyacentCells = 8;
	private static final int DAMAGE = 1;
	private static final int POINTS = 5;
	public static final int LIVE = 2;
	
	public ExplosiveAlien(Game game, int r, int c, int l, int cycles, int shipsOnBorder, Move d) {
		super(game, r, c, l);
		this.cyclesToMove = cycles;
		SHIPS_ON_BORDER = shipsOnBorder;
		this.direction = d;
	}
	
	public ExplosiveAlien() {}
	
	public boolean performAttack(GameObject other) {
		boolean performAttack = false;
		boolean receiveAttack = false;
		
		if(!isAlive()) {			
			if((this.row-1 == other.getRow() && this.column-1 == other.getColumn()) ||
			   (this.row-1 == other.getRow() && this.column == other.getColumn()) ||
			   (this.row-1 == other.getRow() && this.column+1 == other.getColumn()) ||
			   (this.row == other.getRow() && this.column+1 == other.getColumn()) ||
			   (this.row+1 == other.getRow() && this.column+1 == other.getColumn()) ||
			   (this.row+1 == other.getRow() && this.column == other.getColumn()) ||
			   (this.row+1 == other.getRow() && this.column-1 == other.getColumn()) ||
			   (this.row == other.getRow() && this.column-1 == other.getColumn()) ) {
				
				other.receiveExplosiveAlienAttack(DAMAGE);
				receiveAttack = true;
			}
			else 
				receiveAttack = false;
			
			if(receiveAttack) numAdyacentCells--;
			
			if(numAdyacentCells == 0) 
				performAttack = true;
		}
		else performAttack = false;
		return performAttack;
	}
	
	public void addPoints() {
		game.receivePoints(POINTS);
	}
		
	public String toString() {
		return "E[" + this.live + "]";
	}
	
	public String stringify() {
		return "E;" + this.row + "," + this.column + ";" + 
			   this.live + ";" + cyclesToMove + ";" + this.direction + ";" + SHIPS_ON_BORDER;
	}
	
	public void computerAction() {}	
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		String[] words = stringFromFile.split(";");
		String[] coords = words[1].split(",");
		
		if(words[0].toUpperCase().equals("E") && verifier.verifyAlienShipString(stringFromFile, game2, LIVE)) {
			
			ExplosiveAlien eAlien = new ExplosiveAlien();
			eAlien.game = game2;
			eAlien.row = Integer.parseInt(coords[0]);
			eAlien.column = Integer.parseInt(coords[1]);
			eAlien.live = Integer.parseInt(words[2]);
			eAlien.cyclesToMove = Integer.parseInt(words[3]);
			eAlien.direction = Move.parse(words[4]);
			AlienShip.SHIPS_ON_BORDER = Integer.parseInt(words[5]);
			
			if(!game2.loading()) {
				game2.setLoading(true);
				REMAINING_ALIENS = 1;
			}
			else 
				REMAINING_ALIENS += 1;
				
			return eAlien;
		}
		
		return null;
	}
}