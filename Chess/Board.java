package Chess;
//Use JavaFX for GUI/render
public interface Board {
	
	public static String[][] board = new String[8][8];
	public static String[] file = {"A", "B", "C", "D", "E", "F", "G", "H"};
	
	public static void createBoard(String...args) {
	try{
		if (args[0] == "new") {
	
	//Create board of null values
			//	String[][] board = new String[8][8];
	//Create black pieces		
				board[0][7] = "b.Rook";	board[1][7] = "b.Knight";	board[2][7] = "b.Bishop";	board[3][7] = "b.Queen";	board[4][7] = "b.King";	board[5][7] = "b.Bishop";	board[6][7] = "b.Knight";	board[7][7] = "b.Rook";
	
				for ( int i=0; i < 8; i++ ) {
		
					board[i][6] = "b.Pawn";
		
		}
	//Create white pieces
				board[0][0] = "w.Rook";	board[1][0] = "w.Knight";	board[2][0] = "w.Bishop";	board[3][0] = "w.Queen";	board[4][0] = "w.King"; board[5][0] = "w.Bishop";	board[6][0] = "w.Knight";	board[7][0] = "w.Rook";
	
				for ( int i=0; i < 8; i++ ) {
		
					board[i][1] = "w.Pawn";
		
				}

	/*
//Output default positions	
		for (int i=0; i < board.length ; i++)
		   	for (int j=0; j < board[i].length ; j++)
		     	System.out.println(file[i] + "" + (j+1) + " " + board[i][j]);
		*/
	Main.newTurn();
	//load the game args[2] is the file to be loaded from the save folder, either Board, time, or turn
	} else if (args[0] == "load"){
		System.out.println("Loading..."+ args[2]);
		FileHandler.read(args[1], args[2]);
		
	} else System.out.println("I don't know what to do!");
	
	}
	catch(NullPointerException ex) {
		System.out.println("Input is not valid! Please fix!");
	}
}
}
