package Commands;

import Game.Game;
import View.PrinterTypes;

public class ListPrintersCommand extends Command {
	protected final static String name = "listPrinters";
	private final static String details = "list[P]rinters";
	protected final static String shortcut = "p";
	private final static String help = "shows the available printing modes.";
	
	public ListPrintersCommand() {
		super(name, shortcut, details, help);
	}

	public boolean execute(Game game) {
		System.out.println(PrinterTypes.printerHelp(game));
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