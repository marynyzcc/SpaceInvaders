package Game;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

import Exceptions.OffWorldException;
import Game.Level;
import Game.GameObjects.AlienShip;
import Game.GameObjects.DestroyerAlien;
import Game.GameObjects.GameObject;
import Game.GameInterfaces.IPlayerController;
import Game.GameObjects.UCMShip;
import Game.Lists.GameObjectBoard;

public class Game implements IPlayerController {
	public final static int DIM_ROWS = 8;
	public final static int DIM_COLUMNS = 9;
	private int currentCycle;
	private Random rand;
	private Level level;
	private GameObjectBoard board;
	private UCMShip player;
	private boolean doExit;
	private BoardInitializer initializer;
	private boolean serializing = false;

	
	public Game (Level level, Random random) {
		this.rand = random;
		this.level = level;
		initializer = new BoardInitializer();
		initGame();
	}
	
	public void initGame () {
		currentCycle = 0;
		board = initializer.initialize(this, this.level);
		player = new UCMShip(this, DIM_ROWS-1, DIM_COLUMNS/2, 3);
		board.add(player);
	}
	
	public String toStringObjectAt(int r, int c) {
		 return board.toString(r, c);
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public void reset() {
		board.reset();
		initGame();
	}
	
	public void addObject(GameObject object) {
		board.add(object);
	}
	
	public void addObjectInPos(int row, int column, GameObject object) {
		board.addObjectInPos(row, column, object);
	}
	
	public String positionToString(int r, int c) {
		return board.toString(r, c);
	}
	
	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
		
	public boolean aliensWin() {
		return !player.IsAlive() || AlienShip.haveLanded();
	}
		
	private boolean playerWin () {
		return AlienShip.allDead();
	}
	
	public void update() {
		board.computerAction();
		board.update();
		currentCycle += 1;
	}
		
	public boolean isOnBoard(int r, int c) {
		return r >= 0 && c >= 0 && r < DIM_ROWS && 	c < DIM_COLUMNS;
	}
		
	public void exit() {
		doExit = true;
	}
	
	public void setPlayer(UCMShip p) {
		this.player = p;
	}
		
	public String infoToString() {
		return "Cycles: " + currentCycle + "\n" + player.stateToString() 
				+ "Remaining aliens: " + (AlienShip.getRemainingAliens()) + "\n";
	}
		
	public String getWinnerMessage () {
		if (playerWin()) return "Player win!";
		else if (aliensWin()) return "Aliens win!";
		else if (doExit) return "Player exits the game";
		else return "This should not happen";
	}
		
	// PLAYER ACTIONS
	public void move (String direction, int numCells) throws OffWorldException {
		 player.move(direction, numCells);
	}
		
	public boolean canBuySuperLaser() {
		return player.canBuySuperLaser();
	}
	
	public boolean canShootLaser() {
		return player.canShootLaser();
	}
		
	public boolean canShootSuperLaser() {
		return player.canShootSuperLaser();
	}
	
	public boolean hasShockwave() {
		return player.hasShockWave();
	}
		
	public boolean hasExecutedShockwave() {
		return player.hasExecutedShockwave();
	}
		
	// CALLBACKS
	public void receivePoints(int points) {
		player.addPoints(points);
	}
		
	public void enableShockWave() {
		player.enableShockWave();
	}
		
	public void enableLaser() {
		player.enableLaser();
	}
		
	public void enableSuperLaser() {
		player.enableSuperLaser();
	}
		
	//-----------------------------
		
	public void shootLaser() {
		addObject(player.ShootLaser());
	}
		
	public void shootSuperLaser() {
		addObject(player.ShootSuperLaser());
	}
		
	public void executeShockwave() {
		player.executeShockwave();
	}
		
	public void disableShockwave() {
		player.disableShockwave();
	}
	
	public void buySuperLaser() {
		player.buySuperLaser();
	}
			
	//SAVE
	public void save(BufferedWriter bw) throws IOException {
		bw.write(stringify());
	}
	
	public void setSerializing() {
		serializing = true;
	}
	
	public boolean isSerializing() {
		return serializing;
	}

	public String stringify() {
		String stringify = "— SpaceInvadersv2.0 —\n" + "\n" +
			   "G;" + this.currentCycle + "\n" + 
			   "L;" + this.level + "\n" + 
			   
		board.stringify();
		serializing = false;
		
		return stringify;
	}
	
	public DestroyerAlien getBombOwner(int ref) {
		return (DestroyerAlien) board.getLabelOwner(ref);  // ugly cast
	}
}