package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class UCMShipSuperLaser extends Weapon {
	private static final int DAMAGE = 2;
	
	public UCMShipSuperLaser(Game game, int r, int c,int l) {
		super(game, r, c, l);
	}
	
	public UCMShipSuperLaser() {}
	
	public void move() {
		if(this.row >= 0)
			this.row--;
		else
			onDelete();
	}
	
	public boolean receiveBombAttack(int damage) {
		onDelete(); 
		return true;
	}
	
	public void onDelete() {
		this.live = 0;
		game.enableSuperLaser();
	}
	
	public boolean performAttack(GameObject other) {
		boolean performAttack;

		if(this.row == other.getRow() && this.column == other.getColumn()) 
			performAttack = other.receiveSuperLaserAttack(DAMAGE);
		else performAttack = false;
		
		if(performAttack) {
			this.row = 9;
			this.column = 9;
			onDelete();
		}
		
		return performAttack;
	}
	
	public void reset() {
		this.live = 0;
	}
	
	public String toString() {
		return "^^";
	}
	
	public String stringify() {
		return "X;" + this.row + "," + this.column;
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		if(verifier.verifyWeaponString(stringFromFile, game2)) {
			String[] words = stringFromFile.split(";");
			String[] coords = words[1].split(",");
			
			if(!words[0].toUpperCase().equals("X")) return null;
			
			UCMShipSuperLaser superlaser = new UCMShipSuperLaser(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), 1);
			
			this.game = game2;
			this.row = Integer.parseInt(coords[0]);
			this.column = Integer.parseInt(coords[1]);
					
			return superlaser;
		}
		else return null;
	}
}