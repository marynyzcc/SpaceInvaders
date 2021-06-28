package Game.GameObjects;
import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class UCMShipLaser extends Weapon {
	private static final int DAMAGE = 1;
	
	public UCMShipLaser(Game game, int r, int c, int l) {
		super(game, r, c, l);
	}
	
	public UCMShipLaser() {}
	
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
		game.enableLaser();
	}
	
	public boolean performAttack(GameObject other) {
		boolean performAttack;
		
		if(this.row == other.getRow() && this.column == other.getColumn()) 
			 performAttack = other.receiveLaserAttack(DAMAGE);
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
		return "oo";
	}
	
	public String stringify() {
		return "L;" + this.row + "," + this.column;
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		
		if(verifier.verifyWeaponString(stringFromFile, game2)) {
			String[] words = stringFromFile.split(";");
			String[] coords = words[1].split(",");
			
			if(!words[0].toUpperCase().equals("L")) return null;
			
			UCMShipLaser laser = new UCMShipLaser(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), 1);
					
			return laser;
		}
		else return null;
	}
}