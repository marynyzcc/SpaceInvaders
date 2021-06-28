package Exceptions;

public class CommandParseException extends Exception {

	public final static String unknownCommand = "Invalid command!\n";
	public final static String invalidNumberArgs = "Invalid number of arguments";
	
	//MoveCommand
	public final static String cantPerformMove = "Cannot perform move: ";
	public final static String invalidDirection = cantPerformMove + "Invalid direction to move, it has to be right or left";
	public final static String invalidArgument = cantPerformMove + "Third argument must be a number";
	public final static String invalidNumberOfCells = cantPerformMove + "UCMShip can move 1 or 2 cells only";
	
	//ShootCommand
	public final static String invalidWeapon = "Invalid weapon";
	
	//SaveCommand
	public final static String FileNameEmpty = "add the file name";
	

	public CommandParseException(String message){
		super(message);
	}
}
