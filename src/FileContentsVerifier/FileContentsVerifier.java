package FileContentsVerifier;

import Exceptions.FileContentsException;
import Game.Game;
import Game.Level;
import Game.Move;

public class FileContentsVerifier {
	
	public static final String separator1 = ";";
	public static final String separator2 = ",";
	public static final String labelRefSeparator = " - ";
//	private String foundInFileString= "";
//	
//	private void appendToFoundInFileString(String linePrefix) {
//		foundInFileString+= linePrefix;
//	}
		
	public boolean verifyCycleString(String cycleString) throws FileContentsException {
			String[] words= cycleString.split(separator1);
//			appendToFoundInFileString(words[0]);
			
			try {
				if(words.length != 2 || !words[0].toUpperCase().equals("G") || !verifyCurrentCycle(Integer.parseInt(words[1])))
					return false;
			} catch(NumberFormatException e) {
				throw new FileContentsException(FileContentsException.failedLoadingCycles);
			}
		return true;
	}

	public boolean verifyLevelString(String levelString) {
		String[] words = levelString. split (separator1);
//		appendToFoundInFileString(words[0]);
		
		if (words.length != 2 || !words[0].toUpperCase().equals("L") || !verifyLevel(Level.parse(words[1])))
			return false;
		return true;
	}
	
	public boolean verifyOvniString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
//		appendToFoundInFileString(words[0]);
		
		if (words.length != 3) return false;
		String[] coords = words[1].split(separator2);
			
		try {
			if (!words[0].toUpperCase().equals("O") || !verifyIfCoordsIsnumeric(coords[0] , coords[1]) || !verifyLives(Integer. parseInt(words[2]), armour))
				return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingOvni);
		}	
		return true;	
	}
		
	public boolean verifyPlayerString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
//		appendToFoundInFileString(words[0]);
		
		if (words.length != 7) return false;
			String[] coords = words[1].split (separator2);
			
		try {
			if (!words[0].toUpperCase().equals("P") 
				|| !verifyCoords(Integer. parseInt(coords[0]) , Integer. parseInt(coords[1]) , game)
				|| !verifyLives (Integer. parseInt(words[2]), armour)
				|| !verifyPoints (Integer. parseInt(words[3]))
				|| !verifyBool(words[4])
				|| !verifyNumSuperLasers(Integer.parseInt(words[5]))
				|| !verifyBool(words[6]))
				return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingPlayer);
		}
		return true;
	}
	
	// Don’t catch NumberFormatException.
	public boolean verifyAlienShipString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
//		appendToFoundInFileString(words[0]);
		
		if (words.length != 6) return false;
		
		String[] coords = words[1].split (separator2);
		
		try {
			if ( !verifyCoords(Integer. parseInt(coords[0]) , Integer. parseInt(coords[1]) , game)
				|| !verifyLives (Integer. parseInt(words[2]), armour)
				|| !verifyCycleToNextAlienMove(Integer.parseInt(words[3]), game.getLevel())
				|| !verifyDir (Move.parse(words[4])) 
				|| !verifyShipsOnBorder(words[5])) 
				return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingAliens);
		}
		return true;
	}
	
	public boolean verifyDestroyerShipString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		
//		appendToFoundInFileString(words[0]);
		
		if (!words[0].toUpperCase().equals("D") || words.length != 6) return false;
		String[] label = words[5].split(labelRefSeparator);
		String[] coords = words[1].split (separator2);
		
		if(label.length != 2) return false;
		
		try {
			if (!verifyCoords(Integer. parseInt(coords[0]) , Integer. parseInt(coords[1]) , game)
				|| !verifyLives (Integer. parseInt(words[2]), armour)
				|| !verifyCycleToNextAlienMove(Integer.parseInt(words[3]), game.getLevel())
				|| !verifyDir (Move.parse(words[4])) 
				|| !verifyShipsOnBorder(label[0])
				|| !verifyLabel(label[1])) 
					return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingAliens);
		}
		
		return true;
	}
	
	public boolean verifyWeaponString(String lineFromFile, Game game) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		
		if (words.length != 2) return false;
		
//		appendToFoundInFileString(words[0]);
		String[] coords = words[1].split (separator2);
		
		try {
			if (!verifyCoords(Integer.parseInt(coords[0]) , Integer.parseInt(coords[1]) , game))
				return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingWeapon);
		}
		return true;
	}
	
	public boolean verifyBombString(String lineFromFile, Game game) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		String[] label = words[1].split(labelRefSeparator);
		String[] coords = label[0].split(separator2);
		
		try {
			if (!words[0].toUpperCase().equals("B") 
				|| words.length != 2 
				|| !verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)	
				|| !verifyLabel(label[1])) return false;
		} catch(NumberFormatException e) {
			throw new FileContentsException(FileContentsException.failedLodingBomb);
		}
		
		return true;
	}
		
	public static boolean verifyLabel(String label) throws NumberFormatException {
		return Integer.parseInt(label) > 0;
	}
	public static boolean verifyCoords(int x, int y, Game game) {
		return game.isOnBoard(x, y);
	}
	
	public static boolean verifyIfCoordsIsnumeric(String x, String y) {
		try {
			Integer.parseInt(x);
			Integer.parseInt(y);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean verifyCurrentCycle(int currentCycle) {
		return currentCycle >= 0;
	}
	
	public static boolean verifyLevel(Level level) {
		return level != null;
	}
		
	public static boolean verifyDir(Move dir) {
		return dir != null;
	}
		
	public static boolean verifyLives(int live, int armour) {
		return 0 < live && live <= armour;
	}
		
	public static boolean verifyPoints(int points) {
		return points >= 0;
	}
		
	public static boolean verifyCycleToNextAlienMove(int cycle, Level level) {
		return 0 <= cycle && cycle <= level.getNumCyclesToMoveOneCell();
	}
	
	public static boolean verifyShipsOnBorder(String shipsOnBorder) throws NumberFormatException {
		return 0 <= Integer.parseInt(shipsOnBorder);
	}
		
	// parseBoolean converts any string different from "true " to false .
	public static boolean verifyBool(String boolString) {
		return boolString.equals("true") || boolString.equals("false");
	}
		
//	public boolean isLaserOnLoadedBoard() {
//		return foundInFileString.toUpperCase().contains("L");
//	}
//	
//	public boolean isSuperLaserOnLoadedBoard() {
//		return foundInFileString.toUpperCase().contains("X");
//	}
		
	// Use a regular expression to verify the string of concatenated prefixes found
//	public boolean verifyLines(String string) {
//		return foundInFileString.toUpperCase().contains(string);
//	}
		
//	// text explaining allowed concatenated prefixes
//	public String toString() {
//		// TO DO
//		return "";
//	}
	
	public boolean verifyNumSuperLasers(int numSuperLasers) {
		return numSuperLasers >= 0;
	}
}