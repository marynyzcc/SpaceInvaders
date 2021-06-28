package Game.GameInterfaces;

import Exceptions.OffWorldException;

public interface IPlayerController {
	// PLAYER ACTIONS
	public void move (String direction, int numCells) throws OffWorldException;
	public boolean canShootLaser();
	public boolean canShootSuperLaser();
	public boolean hasShockwave();
	public boolean hasExecutedShockwave();
	public boolean canBuySuperLaser();
	// CALLBACKS
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableLaser();
	public void enableSuperLaser();
	
}
