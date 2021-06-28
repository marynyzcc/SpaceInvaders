package Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;
import Exceptions.FileContentsException;
import Game.Game;

public class LoadCommand extends Command{
	private final static String name = "load";
	private final static String details = "loa[d] <fileName>";
	private final static String shortcut = "d";
	private final static String help = "loads a saved game.";
	private String fileName;
	private final static String HEADER = "— SpaceInvadersv2.0 —";
	
	public LoadCommand() {
		super(name, shortcut, details, help);
	}
	
	public boolean execute(Game game) throws CommandExecuteException {
		boolean execute = false;
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String header = br.readLine();
			String space = br.readLine();
			
			if(header != HEADER && !space.isEmpty())
				throw new FileContentsException("Invalid header, cannot load the file");
			
			execute = game.load(br);
			br.close();
			
		} catch(IOException e) {
			throw new CommandExecuteException(e.getMessage());
		} catch(FileContentsException e) {
			throw new CommandExecuteException(e.getMessage());
		}
		
		return execute;
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
		else 
			command = null;
		
		return command;
	}
}
