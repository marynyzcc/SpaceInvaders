package Commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Game.Game;

public class SaveCommand extends Command {
	private final static String name = "save";
	private final static String shortcut = "v";
	private final static String details = "sa[v]e <fileName>";
	private final static String help = "saves the game with the name fileName";
	private String fileName;
	
	public SaveCommand() {
		super(name, shortcut, details, help);
	}

	public boolean execute(Game game) throws CommandExecuteException {
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName + ".dat"))) {
			game.save(bw);
			bw.close();
			
		} catch(IOException e) {
			throw new CommandExecuteException(CommandExecuteException.saveError);
		} 
		
		System.out.println("Game successfully saved in file " + fileName + ".dat. Use the load command to reload it\n");
		
		return false;
	}
	
	public Command parse(String[] commandWords) throws CommandParseException {
		Command command;
		
		boolean match = matchCommandName(commandWords[0]);
		
		if(match) {
			try {
				command = this;
				
				if(commandWords.length != 2)
					throw new IllegalArgumentException();
				
				fileName = commandWords[1];
				
			} catch(IllegalArgumentException e) {
				throw new CommandParseException(CommandParseException.FileNameEmpty);
			} 
		}
		else command = null;
		
		return command;
	}
}