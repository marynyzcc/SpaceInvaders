package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class Ship extends GameObject {
	
	public Ship(Game g, int r, int c, int l) {
		super(g, r, c, l);
	}
	public Ship() {}
	
	public void computerAction() {}
	public void onDelete() {}
	public void move() {}
	public String toString() {	return null;}
	public void reset() {}
	public void addPoints() {}
	public void remove() {}
	public String stringify() {return null;}
	public boolean isOwner(int ref) {return false;}
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {return null;}
}
