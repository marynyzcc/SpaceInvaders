package Commands;

import Exceptions.CommandParseException;
import Game.Game;

public class ShootCommand extends Command {
	protected final static String name = "shoot";
	private final static String details = "[s]hoot ([s]uperlaser)";
	protected final static String shortcut = "s";
	private final static String help = "UCM-Ship launches a laser or a superlaser.";
	private String[] shootCommand;
	
	public ShootCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		boolean execute = false;
		
		if(shootCommand.length == 1) {		
			if(game.canShootLaser()) {
				game.shootLaser();
				game.update();
				execute = true;
			}
			else {
				System.out.println("You can't shoot a laser!\n");
				execute = false;
			}
		}
		else {	
			if(game.canShootSuperLaser()) {
				game.shootSuperLaser();
				execute = true;
				game.update();
			}
			else {
				System.out.println("You can't shoot a superlaser!\n");
				execute = false;
			}
		}
		return execute;
	}

	public Command parse(String[] commandWords) throws CommandParseException {
		Command command;
		shootCommand = commandWords;
		boolean match = matchCommandName(commandWords[0]);
		
		if(match) {
			try {
				command = this;
				
				if(commandWords.length < 1 || commandWords.length > 2)
					throw new IllegalArgumentException("Invalid number of arguments");
				
				if(commandWords.length == 2 && (!commandWords[1].equalsIgnoreCase("s") && 
					!commandWords[1].equalsIgnoreCase("superlaser")))
					throw new CommandParseException(CommandParseException.invalidWeapon);
					
			} catch (IllegalArgumentException e) {
				throw new CommandParseException(e.getMessage());
			}
		}
		else command = null;
	
		return command;
	}
}