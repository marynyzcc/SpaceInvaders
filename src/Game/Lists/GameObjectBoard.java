package Game.Lists;

import java.io.BufferedWriter;
import java.io.IOException;

import Game.GameObjects.GameObject;

public class GameObjectBoard {
	private GameObject[] objects;
	private int currentObjects;
	
	public GameObjectBoard (int height, int width) {
		objects = new GameObject[height*width];
		this.currentObjects = 0;
	}
	
	private int getCurrentObjects() {
		return this.currentObjects;
	}
	
	public void add(GameObject object) {
		objects[this.currentObjects] = object;
		currentObjects++;
	}
		
	private int getIndex(int r, int c) {
		boolean found = false;
		int index = 0;
		
		while(index < this.currentObjects && !found) {
			if(objects[index].getRow() == r && objects[index].getColumn() == c) 
				found = true;
			else
				index++;
		}
		
		if(!found) index = -1;
		
		return index;
	}
	
	private void remove(GameObject object) {
		int pos = getIndex(object.getRow(), object.getColumn());
		
		if(pos == this.currentObjects-1)
			objects[pos] = null;
		else { 
			for(int i = pos; i < currentObjects-1; i++) 
				objects[i] = objects[i+1];				
		
			objects[currentObjects-1] = null; 
		}
		this.currentObjects--;
	}
	
	public void update() {
		for(int i = this.currentObjects-1; -1 < i; i--) {
			//1.Move
			if(objects[i].isOut()) objects[i].onDelete();
			else 
				objects[i].move();

			//2.CheckAttacks
				checkAttacks(objects[i]);
		}
		//3.RemoveDead
		removeDead();
	}
	
	private void checkAttacks(GameObject object) {
		boolean attacks = false;
		int i = 0;
		
		while(i < this.currentObjects && !attacks) {
			GameObject other = objects[i];
			
			if(object != other && object.performAttack(other)) {
				attacks = true;
				i++;
			}
			else
				i++;
		}
	}
	
	public void computerAction() {
		for(int i = 0; i < this.currentObjects; i++)
			objects[i].computerAction();
	}
	
	private void removeDead() {
		int i = 0;
		while(i < this.currentObjects) {
			if(!objects[i].isAlive()) {
				objects[i].addPoints();
				objects[i].remove();
				remove(objects[i]);
			}
			else i++;
		}	
	}
	
	public void reset() {
		for(int i = 0; i < currentObjects; i++)
			objects[i].reset();
		
		int j = 0;
		while(j < this.currentObjects) {
			if(!objects[j].isAlive()) 
				remove(objects[j]);
			else j++;
		}
	}
	
	public void resetToLoad() {
		for(int i = 0; i < currentObjects; i++)
			objects[i].reset();
		
		int j = 0;
		boolean  remove = false;
		
		while(j < this.currentObjects) {
			if(!objects[j].isAlive()) {
				remove(objects[j]);
				remove = true;
			}
			else if(objects[j].isUCMShip()) {
				remove(objects[j]);
				remove = true;
			}
			else remove = false;
			
			if(!remove) j++;
		}
	}
		
	public void addObjectInPos(int r, int c, GameObject object) {
		int index = getIndex(r,c);
		objects[index] = null;
		objects[index] = object;
	}
	
	public String toString(int r, int c) {
		boolean occupied = false;
		int i = 0;
		String object = "";
		
		while(i < getCurrentObjects() && !occupied) {
			if(objects[i].isOnPosition(r, c)) {
				object = objects[i].toString();
				occupied = true;
			}
			else i++;
		}
		
		if(!occupied) object = "";
		
		return object;
	}
	
	public String stringify() {
		String stringify = "";
		for(int i = 0; i < this.currentObjects; i++) {
			
				stringify = stringify + objects[i].stringify() + "\n" ;
			
		}
		return stringify;
	}
	

	//SAVE
	public void saveGame(BufferedWriter bw) throws IOException {
		for(int i = 0; i < this.currentObjects; i++) {
			bw.write(objects[i].stringify());
		}	
	}
	
	public GameObject getLabelOwner(int ref) {
		for (int i=0; i<currentObjects; i++) {
			if (objects[i].isOwner(ref))
				return objects[i];
		}
		return null;
	}
	
}