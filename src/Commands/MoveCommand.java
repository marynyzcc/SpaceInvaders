package Commands;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.OffWorldException;
import Game.Game;

public class MoveCommand extends Command {
	
	protected final static String name = "move";
	private final static String details = "[m]ove <left|right><1|2>";
	protected final static String shortcut = "m";
	private final static String help = "Moves UCM-Ship to the indicated direction.";
	private String[] moveCommand;
	private static String failedMove = "Failed to move";
	private int numCells;
	
	public MoveCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean validMove = false;
		try {
			game.move(moveCommand[1], numCells);
			game.update();
			validMove = true;
			
		} catch(OffWorldException e) {
			System.out.println(failedMove);
			throw new CommandExecuteException(e.fillInStackTrace());
		}
			
		return validMove;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command command;
		boolean match = false;
		
		match = matchCommandName(commandWords[0]);
		
		if(match) {
			command = this;
			moveCommand = commandWords;
			
			if(commandWords.length != 3) {
				System.out.println(failedMove);
				throw new CommandParseException(CommandParseException.invalidNumberArgs);
			}
			
			if(!commandWords[1].equalsIgnoreCase("r") && !commandWords[1].equalsIgnoreCase("right") && 
			   !commandWords[1].equalsIgnoreCase("l") && !commandWords[1].equalsIgnoreCase("left")) {
				System.out.println(failedMove);
				throw new CommandParseException(CommandParseException.invalidDirection);
			}
			
			try {
				numCells = Integer.parseInt(commandWords[2]);
				
				if(numCells != 1 && numCells != 2) {
					System.out.println(failedMove);
					throw new CommandParseException(CommandParseException.invalidNumberOfCells);
				}
					
			} catch(NumberFormatException e) {
				throw new CommandParseException(CommandParseException.invalidArgument);
			}
		}
		
		else
			command = null;
		return command;
	}
}
