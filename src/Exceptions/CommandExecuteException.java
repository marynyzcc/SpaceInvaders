package Exceptions;

public class CommandExecuteException extends Exception {
	public final static String saveError = "An error ocurred while saving the game, cannot save the game";
	
	public CommandExecuteException(Throwable throwable) {
		super(throwable);
	}

	public CommandExecuteException(String message) {
		super(message);
	}
}
