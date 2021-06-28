package Main;
import Game.*;
import java.util.*;
import Controller.Controller;

public class Main {
	private static Game game;
	private static Controller controller;
	private static Scanner scanner;
	private static Random random;
	private static Level level;
	private static String usage = "Usage: Main <EASY|HARD|INSANE> [seed]";
	private static String errorParseSeed = "the seed must be a number";
	private static String unknownLevel = "level must be one of: EASY, HARD, INSANE";
	
	public static void main(String[] args) {		
		
		scanner = new Scanner(System.in);
		boolean noErrorsInArgs = true;
		
		do {
			try {
				if(!noErrorsInArgs) {
					System.out.print("> ");
					args = scanner.nextLine().toLowerCase().trim().split ("\\s+");
				}
				
				if(args.length < 1 || args.length > 2) 
					 throw new IllegalArgumentException();
				
				if(!args[0].equalsIgnoreCase("EASY") && !args[0].equalsIgnoreCase("HARD") && !args[0].equalsIgnoreCase("INSANE")) {
					System.out.println(usage + unknownLevel);
					noErrorsInArgs = false;
				}
				
				else {
					level = Level.fromParam(args[0]);
					
					if(args.length == 1) {
						random = new Random();
						game = new Game(level, random);
					}
					else {
						random = new Random(Integer.parseInt(args[1]));
						game = new Game(level, random);
					}
					noErrorsInArgs = true;
				}
							
			} catch(NumberFormatException e) {
				System.out.println(usage + ": " + errorParseSeed);
				noErrorsInArgs = false;
			} catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
				System.out.println(usage);
				noErrorsInArgs = false;
			} 

		} while(!noErrorsInArgs);

		controller = new Controller(game);
		controller.run();
	}
}