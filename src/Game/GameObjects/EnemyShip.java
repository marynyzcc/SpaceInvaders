package Game.GameObjects;

import Game.Game;
import Game.GameInterfaces.IAttack;

public class EnemyShip extends Ship implements IAttack {
	protected int move;
	
	public EnemyShip(Game g, int r, int c, int l) {
		super(g, r, c, l);
	}
	
	public EnemyShip() {}
		
	public boolean receiveLaserAttack(int damage) {
		getDamage(damage);
		return true;
	}
	
	public boolean receiveSuperLaserAttack(int damage) {
		getDamage(damage);
		return true;
	}
		
	public boolean receiveExplosiveAlienAttack(int damage) {
		getDamage(damage);
		return true;
	}
}