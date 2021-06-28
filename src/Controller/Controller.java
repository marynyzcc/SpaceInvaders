package Controller;

import Game.Game;
import View.GamePrinter;
import View.PrinterTypes;
import java.util.Scanner;
import Commands.Command;
import Commands.CommandGenerator;
import Exceptions.CommandExecuteException;
import Exceptions.CommandParseException;

public class Controller {
	private Game game;
	private Scanner in;
	private String PROMPT = "Command > ";
	private String causeOfException = "Cause of Exception: ";
	private static GamePrinter printer;
	
	public Controller(Game game) {
		this.game = game;
	}
	
	public void run() {
		in = new Scanner(System.in);
		PrinterTypes.BOARDPRINTER.getObject().setGame(this.game);
		printer = PrinterTypes.BOARDPRINTER.getObject();
				
		System.out.println(printer);
				
		while (!game.isFinished()) {
			try {
				System.out.print(PROMPT);
				String[] words = in.nextLine().toLowerCase().trim().split ("\\s+");
				Command command = CommandGenerator.parse(words);
						
				if (command.execute(game))
					System.out.println(printer);	
						
				} catch(CommandParseException | CommandExecuteException e) {
					System.out.println(causeOfException + e.fillInStackTrace());
				}
		}
		
		if(game.isFinished())
			System.out.println(game.getWinnerMessage());
	}
}