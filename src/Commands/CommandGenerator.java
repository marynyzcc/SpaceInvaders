package Commands;

import Exceptions.CommandParseException;

public class CommandGenerator {
	private static Command[] availableCommands = {
			new MoveCommand(),
			new ShootCommand(),
			new ShockwaveCommand(),
			new ListCommand(),
			new ResetCommand(),
			new HelpCommand(),		
			new ExitCommand(),
			new UpdateCommand(),
			new BuySuperLaserCommand(),
			new StringifyCommand(),
			new ListPrintersCommand(), 
			new SaveCommand(), 
			new LoadCommand()
	};
	
	public static Command parse(String c[]) throws CommandParseException {
		boolean found = false;
		int i = 0;
		Command command = null;
	
		while(i < availableCommands.length && !found) {
			command = availableCommands[i].parse(c);
				
			if(command != null)
				found = true;
			else {
				found = false;
				i++;
			}
		}
			
		if(command == null) throw new CommandParseException(CommandParseException.unknownCommand);

		
		if(c[0].equalsIgnoreCase("h") || c[0].equalsIgnoreCase("help")) {
			for(Command j : availableCommands) 
				System.out.println(j.helpText());
			System.out.println("");
		}

		return command;
	}
}
