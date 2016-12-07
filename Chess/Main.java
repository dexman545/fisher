package Chess;

public class Main {
	
	public static int turn = 1;
	
	//Create win/loss variables. If either becomes false, game ends
	public static boolean wCanWin = true;
	public static boolean bCanWin = true;
	
	public static void newTurn() {
		turn = turn + 1;
		
		DetectionSystem.endGame();
		
		//DetectionSystem.escapePosibilities();
		
		//Check for ".ep" and remove it
		//This is for detecting if en peasant s possible
		for (int i=0; i < 8 ; i++)
		   	for (int j=0; j < 8 ; j++) {
		   		String item = Board.board[i][j];
				if (item != null) {
		   		String[] piece = item.split("[.]+");
		   		
		   		if (turn%2 == 0 & piece[0].contentEquals("w")) {
		   			if (piece.length > 3) {
		   				Board.board[i][j] = (piece[0] + "." + piece[1]+ "." + piece[2]);
		   				System.out.println("Corrected a Pawn!");
		   			}
		   	} else if (turn%2 != 0 & piece[0].contentEquals("b")) {
	   			if (piece.length > 3) {
	   				Board.board[i][j] = (piece[0] + "." + piece[1]+ "." + piece[2]);
	   				System.out.println("Corrected a Pawn!");
	   			}
		   	}
		   }
		   	}
		     	
	}
	
	public static void main(String...args) {
		Board.createBoard("new");
		//Movement.move("A2", "A4");
		//Board.board[4][0] = null;
		//Board.board[0][6] = "w.King";
		//Board.board[1][6] = "b.Queen";
		//newTurn();
		FileHandler.save("test", "Board");
		//check if endgame condition
		
	}

	public static void outputBoard() {
		for (int i=0; i < Board.board.length ; i++) {
		   	for (int j=0; j < Board.board[i].length ; j++) {
		     	System.out.println(Board.file[i] + "" + (j+1) + " " + Board.board[i][j]);
		   	}
		}
	}
	
	public static int convertCharToInt(char...args) {
		return Character.getNumericValue(args[0]) - 10;
	}
	
	//Get letter from number
	public static String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
	}
	
}
