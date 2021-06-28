package Commands;
import Game.Game;

public class UpdateCommand extends Command {
	protected final static String name = "none";
	private final static String details = "[n]one";
	protected final static String shortcut = "n";
	private final static String help = "Skips one cycle.";
	
	public UpdateCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) {
		game.update();
		return true;
	}
	
	public Command parse(String[] commandWords) {
		Command command;
		boolean match = false;
		
		match = matchCommandName(commandWords[0]);
		
		if(match) command = this;
		else command = null;
		
		return command;
	}
	
	protected boolean matchCommandName(String n) {
		return shortcut.equalsIgnoreCase(n) || name.equalsIgnoreCase(n) || n.equalsIgnoreCase("");
	}
}