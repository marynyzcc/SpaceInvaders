package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;
import Game.GameInterfaces.IExecuteRandomActions;

public class Ovni extends EnemyShip {
	private boolean enable;
	private Shockwave shockwave;
	private static final int LIVE = 1;
	
	public Ovni(Game game, int r, int c, int l) {
		super(game, r, c, l);
	}
	
	public Ovni() {}
	
	public void move() {
		if(this.enable) {
			if(this.column == 0) {
				this.column = 9;
				this.enable = false;
			}
			else
				this.column--;
		}
	}
	
	public void computerAction() {
		if(!this.enable && IExecuteRandomActions.canGenerateRandomOvni(this.game)) 
			this.enable = true;
	}
	
	public boolean receiveLaserAttack(int damage) {
		onDelete();		
		return true;
	}
	
	public boolean receiveSuperLaserAttack(int damage) {
		onDelete();
		return true;
	}
	
	public boolean receiveExplosiveAlienAttack(int damage) {
		onDelete();
		return true;
	}
	
	public void onDelete() {
		shockwave = new Shockwave(game, -1, -1, 1, AlienShip.getRemainingAliens());
		game.addObject(shockwave);
		game.enableShockWave();
		this.column = 9;
		this.enable = false;
	}
	

	
	public boolean isOut() {
		boolean isOut = !game.isOnBoard(this.row, this.column);
		
		if(isOut && this.column == 9 && this.enable)
			isOut = false;
		else if(isOut && this.column == -1) {
			this.enable = false;
			this.column = 9;
		}
		return false;
	}
	
	public void reset() {
		this.live = 0;
	}
	
	public String toString() {
		return "O[" + this.live + "]";
	}	
	
	public String stringify() {
		return "O;" + this.row + "," + this.column + ";" + this.live;
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		
		if(verifier.verifyOvniString(stringFromFile, game2, LIVE)) {
			String[] words = stringFromFile.split(";");
			String[] coords = words[1].split(",");
			
			Ovni ovni = new Ovni(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2]));
			
			if(ovni.column >= 0 && ovni.column < 9)
				this.enable = true;
			else
				this.enable = false;
			
//			this.game = game2;
//			this.row = Integer.parseInt(coords[0]);
//			this.column = Integer.parseInt(coords[1]);
//			this.live = Integer.parseInt(words[2]);
//			
//			if(game.isOnBoard(this.row, this.column))
//				this.enable = true;
//			else
//				this.enable = false;
			
			return ovni;
		}
		else 
			return null;
	}
}