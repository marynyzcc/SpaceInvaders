package Game.GameObjects;

import Exceptions.FileContentsException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class GameObjectGenerator {
	
	private static GameObject[] availableGameObjects = {
		new UCMShip(),
		new Ovni(),
		new RegularAlien(),
		new DestroyerAlien(),
		new ExplosiveAlien(),
		new Shockwave(),
		new Bomb(),
		new UCMShipLaser(),
		new UCMShipSuperLaser()
	};
			
	public static GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws FileContentsException {
		GameObject gameObject = null;
		
		for (GameObject go: availableGameObjects) {
			gameObject = go.parse(stringFromFile, game, verifier);
			
			if (gameObject != null) break;
		}
	
		return gameObject;
	}
}