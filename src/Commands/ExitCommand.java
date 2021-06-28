package Commands;
import Game.Game;

public class ExitCommand extends Command {
	private final static String name = "exit";
	private final static String details = "[e]xit";
	private final static String shortcut = "e";
	private final static String help = "Terminates the program.";
	
	public ExitCommand() {
		super(name, shortcut, details, help);
	}	
	
	public boolean execute(Game game) {
		boolean execute = true;
		game.exit();
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