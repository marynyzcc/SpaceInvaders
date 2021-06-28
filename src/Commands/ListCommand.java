package Commands;

import Game.Game;

public class ListCommand extends Command {
	protected final static String name = "list";
	private final static String details = "[l]ist";
	protected final static String shortcut = "l";
	private final static String help = "Prints the list of available ships.";
	
	public ListCommand() {
		super(name, shortcut, details, help);
	}
	
	
	public boolean execute(Game game) {
		System.out.println("[R]egular ship: Points: 5 - Harm: 0 - Shield: 2\n" + 
						   "[E]xplosive ship: Points: 5 - Harm: 1 - Shield: 2\n" +
						   "[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1\n" + 
						   "[O]vni: Points: 25 - Harm: 0 - Shield: 1\n" + 
				   		   "^__^ : Harm: 1 - Shield: 3\n");
		return false;
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
