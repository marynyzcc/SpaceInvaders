package Commands;

import Game.Game;

public class BuySuperLaserCommand extends Command {
	protected final static String name = "buy";
	private final static String details = "[b]uy";
	protected final static String shortcut = "b";
	private final static String help = "Buys a superlaser.";
	
	public BuySuperLaserCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		boolean execute = game.canBuySuperLaser();
		
		if(execute) {
			game.buySuperLaser();
			game.update();
		}
		else System.out.println("Don't have enough points!\n");
		return execute;
	}
	
	public Command parse(String[] commandWords) {
		Command command;
		boolean match = false;
		
		match = matchCommandName(commandWords[0]);
		
		if(match) {
			command = this;
		}
		else
			command = null;
		return command;
	}
	
	protected boolean matchCommandName(String n) {
		return shortcut.equalsIgnoreCase(n) || name.equalsIgnoreCase(n);
	}
	
	public String helpText() {
		return details + " : " + help;
	}
}
