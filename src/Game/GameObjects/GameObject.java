package Game.GameObjects;

import Game.GameInterfaces.IAttack;
import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public abstract class GameObject implements IAttack {
	protected int row;
	protected int column;
	protected int live;
	protected Game game;
	
	public static final String labelRefSeparator = " - ";
	protected int label = 0;
	
	public GameObject(Game g, int r, int c, int l) {
		this.row = r;
		this.column = c;
		this.game = g;
		this.live = l;
	}
	
	public GameObject() {}
	
	public int getRow() {
		return this.row;
	}
	public int getColumn() {
		return this.column;
	}
	
	public boolean isAlive() {
		return this.live > 0;
	}
	public int getLive() {
		return this.live;
	}
	
	public boolean isOnPosition(int r, int c) {
		return this.row == r && this.column == c;
	}
	
	public void getDamage(int damage) {
		if(damage >= this.live) this.live = 0;
		else this.live = this.live - damage;
	}
	
	public boolean isOut() {
		return !game.isOnBoard(this.row, this.column);
	}
		
	public abstract void computerAction();
	public abstract void onDelete();
	public abstract void move();
	public abstract String toString();
	public abstract void reset();
	public abstract void addPoints();
	public abstract void remove();
	public abstract String stringify();
	protected abstract GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException;

	
	public boolean isUCMShip() {return false;}
	public int getLabel() {
		return label;
	}
	
	public void setLabel(int l) {
		this.label = l;
	}
	public boolean isOwner(int ref) {
		return label == ref;
	}
	
	public void setObject(Game g, int r, int c, int l) {
		this.row = r;
		this.column = c;
	}
}