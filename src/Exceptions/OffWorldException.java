package Exceptions;

public class OffWorldException extends Exception {
	public static String offWorld = "Cannot perform move: ship too near border";
	
	public OffWorldException(String message) {
		super(message);
	}
}
