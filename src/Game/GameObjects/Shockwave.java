package Game.GameObjects;

import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class Shockwave extends Weapon {
	private static final int DAMAGE = 1;
	private static int REMAINING_ALIENS_TO_ATTACK;
	
	public Shockwave(Game game, int r, int c, int l, int RemainingAliensToAttack) {
		super(game, r, c, l);
		REMAINING_ALIENS_TO_ATTACK = RemainingAliensToAttack;
	}
	
	public Shockwave() {}
		
	public boolean performAttack(GameObject other) {
		boolean performAttack;
		
		if(game.hasExecutedShockwave()) {
			if (REMAINING_ALIENS_TO_ATTACK == 0) {
				performAttack = true;
				game.disableShockwave();
				onDelete();
			}
			else if(other.receiveShockWaveAttack(DAMAGE)) {
					REMAINING_ALIENS_TO_ATTACK -= 1;
					performAttack = false;
			}
			else performAttack = false;
		}
		else performAttack = false;
		
		return performAttack;
	}
	
	public void reset() {
		onDelete();
	}
	
	public void onDelete() {
		this.live = 0;
	}
	
	public boolean isOut() {
		return false;
	}
	
	public boolean isAlive() {
		return this.live > 0;
	}
	
	public String toString() {
		return "";
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) {
		
		if(game2.hasShockwave()) {
			this.game = game2;
			this.row = -1;
			this.column = -1;
			this.live = 1;
			REMAINING_ALIENS_TO_ATTACK = AlienShip.REMAINING_ALIENS;
			
			return new Shockwave(game, row, column, live, REMAINING_ALIENS_TO_ATTACK);
		}
		else return null;
	}
	
}