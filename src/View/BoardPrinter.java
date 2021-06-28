package View;

import Game.Game;
import Game.MyStringUtils;

public class BoardPrinter extends GamePrinter {
	int numRows; 
	int numCols;
	String[][] board;
	final String space = " ";
		
	public void setGame(Game game) {
		this.game = game;
		this.numRows = Game.DIM_ROWS;
		this.numCols = Game.DIM_COLUMNS;	
		encodeGame(this.game);
	}
	
	private void encodeGame(Game game) {
		board = new String[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				board[i][j] = game.toStringObjectAt(i, j);
			}
		}
	}
	
	public String toString() {
		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		
		String rowDelimiter = MyStringUtils.repeat(hDelimiter, (numCols * (cellSize + 1)) - 1);
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineDelimiter);
		encodeGame(this.game);
		
		for(int i=0; i<numRows; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<numCols; j++) {
					str.append( MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
				}
				str.append(lineDelimiter);
		}
		return game.infoToString() + str.toString();
	}
}