package Commands;

import Game.Game;

public class HelpCommand extends Command {
	
	protected final static String name = "help";
	private final static String details = "[h]elp";
	protected final static String shortcut = "h";
	private final static String help = "shows this help.";
	
	public HelpCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		return false;
	}

	public Command parse(String[] commandWords) {
		Command command;
		boolean match = false;
		
		match = matchCommandName(commandWords[0]);
		
		if(match) command = this;
		else command = null;
		return command;
	}
}
