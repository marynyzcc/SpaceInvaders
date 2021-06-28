package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class Bomb extends Weapon {
	private DestroyerAlien dAlien;
	private static final int DAMAGE = 1;
	
	public Bomb(Game game, int r, int c, int l, DestroyerAlien destroyerAlien) {
		super(game, r, c, l);
		this.live = l;
		this.dAlien = destroyerAlien;
	}
	
	public Bomb() {}
	
	public void move() {
		if(isOut()) 
			onDelete();
		else
			this.row++;
	}
	
	public void onDelete() {
		this.live = 0;
		
		if(dAlien != null)
			dAlien.enableBomb();
	}
	
	public boolean receiveLaserAttack(int damage) {
		onDelete();
		return true;
	}
	
	public boolean receiveSuperLaserAttack(int damage) {
		onDelete();
		return true;
	}
	
	public boolean performAttack(GameObject other) {
		boolean performAttack;
		if(this.row == other.getRow() && this.column == other.getColumn()) 
			performAttack = other.receiveBombAttack(DAMAGE);
		else performAttack = false;
		
		if(performAttack) {
			this.row = 9;
			this.column = 9;
			
			onDelete();
		}
		
		return performAttack;
	}
	
	public String toString() {
		return ".";
	}	
		
	public void remove() {
		onDelete();
	}
	
	public void reset() {
		onDelete();
	}
		
	//SAVE
	// call to getLabel assumes owner already serialized (so label already generated)
	public String generateSerialRef() {
		return labelRefSeparator + dAlien.getLabel();
	}

	public String stringify() {
		return "B;" + this.row + "," + this.column + generateSerialRef();
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		
		if(verifier.verifyBombString(stringFromFile, game2)) {
			String[] words = stringFromFile.split(";");
			String[] label = words[1].split(" - ");
			String[] coords = label[0].split(",");
			this.label = Integer.parseInt(label[1]);
		
			return createInstance(game2,Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
		}
		return null;
	}

	// call to getBombOwner assumes owner already deserialized (so ref points to valid object)
	public Bomb createInstance(Game game, int r, int c) {
		return new Bomb(game, r, c, 1, game.getBombOwner(this.label));
	}
	
}