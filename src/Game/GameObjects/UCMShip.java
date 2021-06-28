package Game.GameObjects;

import Exceptions.FileContentsException;
import Exceptions.OffWorldException;
import FileContentsVerifier.FileContentsVerifier;
import Game.Game;

public class UCMShip extends Ship {
	private static final int LIVE = 3;
	private int points;
	private boolean hasShockWave = false;
	private boolean hasExecutedShockwave = false;
	private boolean canShootLaser = true;
	private boolean canShootSuperLaser = true;
	private int NUM_SUPER_LASERS = 0;
	private static final int PRICE_SUPER_LASER = 20;
	private boolean weaponReleased = false;
	private String sW;
	
	public UCMShip(Game game, int r, int c, int l) {
		super(game, r, c, l);
		this.points = 0;
	}
	
	public UCMShip() {}
	

	public void move(String direction, int numCells) throws OffWorldException {
		
		if(direction.equalsIgnoreCase("r") || direction.equalsIgnoreCase("right"))
			 moveRight(numCells);
		else
			moveLeft(numCells);
	}
	
	private void moveRight(int posRight) throws OffWorldException {
		
		if(this.column + posRight <= 8)
			this.column = this.column + posRight;
		else throw new OffWorldException(OffWorldException.offWorld);
	}
	
	private void moveLeft(int posLeft) throws OffWorldException {
		
		if(this.column - posLeft >= 0)
			this.column = this.column - posLeft;
		else 
			throw new OffWorldException(OffWorldException.offWorld); 
	}
	
	public void addPoints(int p) {
    	this.points = points + p;
    }
		
    public int getPoints() {
    	return this.points;
    }
  	
	public boolean hasShockWave() {
		return this.hasShockWave;
	}
	
	public boolean canShootLaser() {
		if(this.canShootSuperLaser) return true;
		return false;
	}
	
	public boolean canShootSuperLaser() {
		if(this.canShootLaser && NUM_SUPER_LASERS != 0) 
			return true;
		else if(NUM_SUPER_LASERS == 0) {
			System.out.println("Don't have superlasers");
			return false;
		}
		else return false;
	}
	
	public boolean receiveBombAttack(int damage) {
		getDamage(damage);	
		return true;
	}
	
	public boolean receiveExplosiveAlienAttack(int damage) {
		getDamage(damage);
		return true;
	};
	
	public void executeShockwave() {
		this.hasExecutedShockwave = true;
		this.hasShockWave = false;
	}
	
	public boolean hasExecutedShockwave() {
		return this.hasExecutedShockwave;
	}
	
	public String toString() {
		if(!IsAlive())
			return UCMShipDestroyed();
		else
			return toStringAlive();
	}
	
	public void enableLaser() {
		this.canShootLaser = true;
		this.canShootSuperLaser = true;
		this.weaponReleased = false;
	}
	
	public void enableSuperLaser() {
		this.canShootSuperLaser = true;
		this.canShootLaser = true;
		this.weaponReleased = false;

	}
	
	public void enableShockWave() {
		this.hasShockWave = true;
	}
	
	public UCMShipLaser ShootLaser() {
		UCMShipLaser laser;
		laser = new UCMShipLaser(game, this.row, this.column, 1);
		this.canShootLaser = false;
		this.canShootSuperLaser = false;
		this.weaponReleased = true;
		return laser;
	}
	
	public UCMShipSuperLaser ShootSuperLaser() {
		UCMShipSuperLaser superlaser;
		superlaser = new UCMShipSuperLaser(game, this.row, this.column, 1);
		NUM_SUPER_LASERS -= 1;
		this.canShootSuperLaser = false;
		this.canShootLaser = false;
		this.weaponReleased = true;
		return superlaser;
	}
	
	public boolean canBuySuperLaser() {
		if(this.points >= PRICE_SUPER_LASER) return true;
		else  return false;
	}
	
	public void buySuperLaser() {
		NUM_SUPER_LASERS += 1;
		this.points = this.points - PRICE_SUPER_LASER;
	}
	
	public int getNumSuperLasers() {
		return NUM_SUPER_LASERS;
	}
	
	public boolean isOut() {
		return false;
	}
	
	//para que no se elimine de GameObjectBoard
	public boolean isAlive() {
		return true;
	}
	
	public boolean IsAlive() {
		return this.live > 0;
	}
	
	public void disableShockwave() {
		this.hasExecutedShockwave = false;
	}
	
	public void reset() {
		this.live = 0;
	}
	
	public String toStringAlive() {
		return "^__^";
	}
	
	public String UCMShipDestroyed() {
		return "!xx!";
	}
	
	public boolean isUCMShip() {
		return true;
	}

	public String stateToString() {
	
		if(this.hasShockWave) sW = "ON";
    	else sW = "OFF";
    		
    	return "Life: " + this.live + "\n" + "Points: " + this.points + "\n" + 
    		   "ShockWave: " + sW + "\n" + "Number of SuperLasers: " + NUM_SUPER_LASERS + "\n";
	}
	
	public String stringify() {
		return "P;" + this.row + "," + this.column + ";" + this.live + ";" +
				this.points + ";" + this.hasShockWave + ";" + this.NUM_SUPER_LASERS + ";" + weaponReleased;
	}
	
	public GameObject parse(String stringFromFile, Game game2, FileContentsVerifier verifier) throws FileContentsException {
		
		if(verifier.verifyPlayerString(stringFromFile, game2, LIVE)) {
			String[] words = stringFromFile.split(";");
			String[] coords = words[1].split(",");
			
			UCMShip player = new UCMShip(game2, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(words[2]));
			player.points = Integer.parseInt(words[3]);
			
			
			if(words[4].equalsIgnoreCase("true")) {
				player.hasShockWave = true;
				player.sW = "ON";
			}
			else {
				player.hasShockWave = false;
				player.sW = "OFF";
			}
			
			player.NUM_SUPER_LASERS = Integer.parseInt(words[5]);
			
			if(words[6].equalsIgnoreCase("true"))  {
				player.weaponReleased = true;
				player.canShootLaser = false;
				player.canShootSuperLaser = false;
			}
			else {
				player.weaponReleased = false;
				player.canShootLaser = true;
				player.canShootSuperLaser = true;
			}
			
			game2.setPlayer(player);
			
			return player;
		}
		else 
			return null;
	}
}