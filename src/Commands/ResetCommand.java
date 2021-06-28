package Commands;
import Game.Game;

public class ResetCommand extends Command {
	protected final static String name = "reset";
	private final static String details = "[r]eset";
	protected final static String shortcut = "r";
	private final static String help = "Starts a new game.";
	
	public ResetCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		boolean execute = true;
		game.reset();
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