package Commands;

import Game.Game;
import View.GamePrinter;
import View.PrinterTypes;

public class StringifyCommand extends Command {
	private final static String name = "stringify";
	private final static String shortcut = "g";
	private final static String details = "strin[g]ify";
	private final static String help = "prints the game as plain text.";
	
	public StringifyCommand() {
		super(name, shortcut, details, help);
	}

	public boolean execute(Game game) {
		GamePrinter printer;
		
		PrinterTypes.STRINGIFIER.getObject().setGame(game);
		printer = PrinterTypes.STRINGIFIER.getObject();
		
		System.out.println(printer);
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