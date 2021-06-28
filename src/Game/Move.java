package Game;

public enum Move {
	LEFT, RIGHT, UP, DOWN, NONE;
	
	public Move flip() {
		if(this == LEFT) return RIGHT;
		if(this == RIGHT) return LEFT;
		return this;
	}

	public static Move parse(String string) {
		for(Move move: Move.values())
			if(move.name().equalsIgnoreCase(string)) return move;
		return null;
	}	
}
