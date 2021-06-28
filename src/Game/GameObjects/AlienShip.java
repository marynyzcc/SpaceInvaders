package Game.GameObjects;

import Game.Game;
import Game.Move;

public class AlienShip extends EnemyShip {
	protected static int REMAINING_ALIENS = 0;
	private static boolean IS_IN_FINAL_ROW;
	public static int SHIPS_ON_BORDER;
	protected int cyclesToMove;
	private static int numCyclesToMoveOneCell;
	protected Move direction = Move.LEFT;	
	
	public AlienShip(Game game, int r, int c, int l) {
		super(game, r, c, l);
		
		REMAINING_ALIENS += 1;
		SHIPS_ON_BORDER = 0;
		numCyclesToMoveOneCell = game.getLevel().getNumCyclesToMoveOneCell();
		cyclesToMove = numCyclesToMoveOneCell-1;
	}
	
	public AlienShip() {}
	
	public void move() {
		 if(cyclesToMove == 0) {
			cyclesToMove = numCyclesToMoveOneCell-1;
			
			if(direction == Move.LEFT) moveLeft();
			else if(direction == Move.RIGHT) moveRight();
			
			if(this.row == Game.DIM_ROWS - 1) IS_IN_FINAL_ROW = true;
			
			if(this.column == 0 || this.column == Game.DIM_COLUMNS-1)
				setShipsOnBorder(REMAINING_ALIENS);
//				SHIPS_ON_BORDER = REMAINING_ALIENS;
		}
		else if(SHIPS_ON_BORDER > 0) {
			this.row++;
			if(this.row == Game.DIM_ROWS - 1) IS_IN_FINAL_ROW = true;
			
			direction = direction.flip();
			SHIPS_ON_BORDER -= 1;
			cyclesToMove = 0;
		}
		else cyclesToMove--;
	}

	public static int getRemainingAliens() {
		return REMAINING_ALIENS;
	}

	public static boolean allDead() {
		if(REMAINING_ALIENS == 0) return true;
		else return false;
	}

	public static boolean haveLanded() {
		return IS_IN_FINAL_ROW;
	}
	
	public void moveRight() {
		this.column++;
	}
	
	public void moveLeft() {
		this.column--;
	}
	
	public boolean receiveShockWaveAttack(int damage) {
		getDamage(damage);
		return true;
	}

	public void reset() {
		this.live = 0;
		REMAINING_ALIENS = 0;
	}
	
	public void setShipsOnBorder(int Aliens) {
		SHIPS_ON_BORDER = Aliens;
	}
	
	public void remove() {
		REMAINING_ALIENS -= 1;
	}
	
	public boolean isOut() {
		return false;
	}
}