package Commands;

import Game.Game;

public class ShockwaveCommand extends Command {
	
	protected final static String name = "shockwave";
	private final static String details = "shock[w]ave";
	protected final static String shortcut = "w";
	private final static String help = "UCM-Ship releases a shock wave.";
	
	public ShockwaveCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		boolean execute;
		
		if(!game.hasShockwave()) {
			System.out.println("Don't have shockwave!\n");
			execute = false;
		}
		else {
			game.executeShockwave();
			game.update();
			execute = true;
		}
		
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
}
