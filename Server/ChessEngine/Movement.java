package Server.ChessEngine;

//TODO CASTLING, YOU FOOL!
//after pawn/rook/king move, add ".hasMoved.ep"
public class Movement {

	// check to see if the piece can move to the desired square
	public static boolean canMove(String...args) {
		//args as color to move, space currently at, space to move to

	try {
		//Get the places from notation
		//f1 r1 are temp vars to get the characters in chess notation. F1 R1 are the initial, numeric locations for use in Board.board[][]
		String space = args[1].toUpperCase();
		char f1 = space.charAt(0);
		char r1 = space.charAt(1);
		int F1 = Main.convertCharToInt(f1);
		int R1 = Character.getNumericValue(r1) - 1;
		
		String[] sqrPiece = {null, null};
		
		String item = Board.board[F1][R1];
		String[] piece = item.split("[.]+");
        
        char f2 = args[2].toUpperCase().charAt(0);
		char r2 = args[2].charAt(1);
		int F2 = Main.convertCharToInt(f2);
		int R2 = Character.getNumericValue(r2) - 1;
		
		String sqrToMove = Board.board[F2][R2];
		if (sqrToMove != null) {
			 sqrPiece = sqrToMove.split("[.]+");
			
		}
//Fix var so it works when actually movement
		if (sqrPiece[0] == null) {
			sqrPiece[0] = "e";
		}
			
//Check if the piece can go to the requested square, and if it is that person's turn
		if (Main.turn%2 == 0  & args[0].contentEquals("w") & args[0].contentEquals(piece[0]) &  !sqrPiece[0].contentEquals("w") || sqrPiece[1].contentEquals("e")) {
			//System.out.println(piece[1] + " is trying to move");
			//Check if the moving piece is within rules
			switch (piece[1]) {
			case "Pawn":
				//check if pawn can go forward
				if (R1 < R2) {
					//checks for en passant
					if (R1 == 4) {
						if (F2 == (F1 + 1) | F2 == (F1 - 1)) {
							String[] pieceAtPossibility = Board.board[F2][5].split("[.]+");
							if (pieceAtPossibility.length == 4 & pieceAtPossibility[3].contentEquals("ep")) {
								Board.board[F2][5] = null;
								return true;
							}
						}
						
					} else if (testPawnMove(F1, R1, F2, R2)) {
						return true;
					} 
					
				} 
				return false;
			case "Bishop":
				 return testBishopMove(F1, R1, F2, R2);
				
			case "Rook":
				return testRookMove(F1, R1, F2, R2);

			case "Queen":
				return testRookMove(F1, R1, F2, R2) | testBishopMove(F1, R1, F2, R2);
					
			case "Knight":
				return testKnightMove(F1, R1, F2, R2);
					
			case "King":
				if (Math.abs(F1-F2) <= 1 & Math.abs(R1-R2) <= 1) {
					return testRookMove(F1, R1, F2, R2) | testBishopMove(F1, R1, F2, R2);
				} return false;
			default: 
				System.out.println("No piece found to move, please try again elsewhere");
				return false;
			
			} 
			
		} else if (Main.turn%2 != 0 & args[0].contentEquals("b") & piece[0].contentEquals(args[0]) & !sqrPiece[0].contentEquals("b")) {
			//System.out.println(piece[1] + " is trying to move");
			//Check if the moving piece is within rules
			switch (piece[1]) {
			case "Pawn":
				//check if pawn can go forward
				if (R1 > R2) {
					//checks for en passant
					if (R1 == 3) {
						if (F2 == (F1 + 1) | F2 == (F1 - 1)) {
							String[] pieceAtPossibility = Board.board[F2][2].split("[.]+");
							if (pieceAtPossibility.length == 4 & pieceAtPossibility[3].contentEquals("ep")) {
								Board.board[F2][2] = null;
								return true;
							}
						}
						
					} else if (testPawnMove(F1, R1, F2, R2)) {
						return true;
					} 
					
				} 
				return false;
			case "Bishop":
				return testBishopMove(F1, R1, F2, R2);
				
			case "Rook":
				return testRookMove(F1, R1, F2, R2);
			
			case "Queen":
				return testRookMove(F1, R1, F2, R2) | testBishopMove(F1, R1, F2, R2);
					
			case "Knight":
				return testKnightMove(F1, R1, F2, R2);
					
			case "King":
				if (Math.abs(F1-F2) <= 1 & Math.abs(R1-R2) <= 1) {
					return testRookMove(F1, R1, F2, R2) | testBishopMove(F1, R1, F2, R2);
				} return false;
			default: 
				System.out.println("No piece found to move, please try again elsewhere");
				return false;
			
			} 
			
		} else return false;
	}
	catch(ArrayIndexOutOfBoundsException ex) {
		return false;
	}
	catch(NullPointerException ex) {
		return false;
	}
}
	
	public static boolean testPawnMove(int...args) {
		//args in terms of F1, R1, F2, R2
		int F1 = args[0];
		int R1 = args[1];
		int F2 = args[2];
		int R2 = args[3];
		
		//Get if pawn has been moved
		String[] piece = Board.board[F1][R1].split("[.]+");
		
		//See what color piece is moving to make sure position mapping works
		int Y = 0;
		if (piece[0].contentEquals("w")){
			Y = 1;
		} else Y = -1;
		
		if (F1 == F2) {
			if (R2 == R1+(2 * Y)) {
				if (piece.length < 3) {
					//check if there is a piece in the way
                		if (Board.board[F2][R2] != null) {
                			return false;
                			
                		} 
                	} return true;
                	//ends checking
				
			} else if (R2 == R1 + Y) {
			int u = 1;
			for (int r = R2 - R1; r > u;) {
				if (Board.board[F1][R2] != null) {
					return false;
    			
				} else {
					u = u + 1;
				}
			} return true;
		}
		} else if (F2 == F1 + 1 | F2 == F1 - 1) {
			if (Board.board[F2][R2] != null & R2 == (R1+Y)) {
				return true;
			} else return false;
		} return false;
	}

	
	public static boolean testKnightMove(int...args) {
		//args in terms of F1, R1, F2, R2
				int F1 = args[0];
				int R1 = args[1];
				int F2 = args[2];
				int R2 = args[3];
		if (F2 - 2 == F1 | F2 + 2 == F1) {
			if (R2 - 1 == R1 | R2 + 1 == R1) {
				return true;
			}
		} else if (F2 - 1 == F1 | F2 + 1 == F1) {
			if (R2 - 2 == R1 | R2 + 2 == R1) {
				return true;
			}
		} else return false;
		return false;
		
	}

	public static boolean testBishopMove(int...args) {
		//args in terms of F1, R1, F2, R2
		int F1 = args[0];
		int R1 = args[1];
		int F2 = args[2];
		int R2 = args[3];
		
		if (Math.abs(F2-F1) == Math.abs(R2-R1)) {
			if (F2 > F1) {
				if (R2 > R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R2 - R1; r > u; i++ ) {
                		//int x = Math.max(0, (F2-i));
                		if (Board.board[F1+i][R1+i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
                } else if (R2 < R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R2 - R1; r > u; i++ ) {
                		//int x = Math.max(0, (F2-i));
                		if (Board.board[F1+i][R1-i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
                }
			} else if (F2 < F1) {
                if (R2 > R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R2 - R1; r > u; i++ ) {
                		//int x = Math.max(0, (F2-i));
                		if (Board.board[F1-i][R1+i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
                } else if (R2 < R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R2 - R1; r > u; i++ ) {
                		//int x = Math.max(0, (F2-i));
                		if (Board.board[F1-i][R1-i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
                }
			}
		} else return false;
		
	 return false;
}
	
	public static boolean testRookMove(int...args) {
		//args in terms of F1, R1, F2, R2
				int F1 = args[0];
				int R1 = args[1];
				int F2 = args[2];
				int R2 = args[3];
				
		if (F2 == F1 | R2 == R1) {
            if (F2 == F1) {
                if (R2 > R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R2 - R1; r > u; i++ ) {
                		
                		if (Board.board[F2][R1+i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
            
                }	else if (R2 < R1) {
                	int u = 1;
                	int i = 1;
                	for (int r = R1 - R2; r > u; i++ ) {
                		
                		if (Board.board[F2][R1-i] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
            
                }
            }
            else if (R2 == R1) {
                if (F2 > F1) {
                	int u = 1;
                	int i = 1;
                	for (int r = F2 - F1; r > u; i++ ) {
                		
                		if (Board.board[F2+i][R1] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
            
                }	else if (F2 < F1) {
                	int u = 1;
                	int i = 1;
                	for (int r = F1 - F2; r > u; i++ ) {
                		
                		if (Board.board[F2-i][R1] != null) {
                			return false;
                			
                		} else {
                			u = u + 1;
                		}
                	} return true;
            
                }
            }
    } return false;
		
	}
	
	//Actually move the piece; THIS IS NOT THE ONE TO USE, USED ONLY FOR TESTING THEORETICAL MOVEMENT
	
	public static void movePiece(String...args) {
		//Args in terms of Square1, Square2
		
		//parse args into usable ints
		String space = args[0].toUpperCase();
		char f1 = space.charAt(0);
		char r1 = space.charAt(1);
		int F1 = Main.convertCharToInt(f1);
		int R1 = Character.getNumericValue(r1) - 1;

        
        char f2 = args[1].toUpperCase().charAt(0);
		char r2 = args[1].charAt(1);
		int F2 = Main.convertCharToInt(f2);
		int R2 = Character.getNumericValue(r2) - 1;

		String sqrToMove = Board.board[F1][R1];
		
		//get colour to move
		String person = Main.colorToMove();
		
		//Begin moving piece
		if (canMove(person, args[0], args[1])) {
			Board.board[F2][R2] = (sqrToMove);
			Board.board[F1][R1] = null;
			
		}
		
	}

	//Actually move the piece
	public static void move(String...args) {
		//Args in terms of Square1, Square2
		

		String[][] tempBoard = Board.board;
			
		movePiece(args[0], args[1]);

		if (DetectionSystem.detectCheck()) {
			for (int i=0; i < Board.board.length ; i++) {
				for (int j=0; j < Board.board[i].length ; j++) {
					Board.board[i][j] = tempBoard[i][j];
				}
			}
			System.out.println("Move is illegal! Thou art checked!");
		} else {
			for (int i=0; i < Board.board.length ; i++) {
				for (int j=0; j < Board.board[i].length ; j++) {
					Board.board[i][j] = tempBoard[i][j];
				}
			}

			//parse args into usable ints
			String space = args[0].toUpperCase();
			char f1 = space.charAt(0);
			char r1 = space.charAt(1);
			int F1 = Main.convertCharToInt(f1);
			int R1 = Character.getNumericValue(r1) - 1;

			String[] sqrPiece = {null, null};

			char f2 = args[1].toUpperCase().charAt(0);
			char r2 = args[1].charAt(1);
			int F2 = Main.convertCharToInt(f2);
			int R2 = Character.getNumericValue(r2) - 1;

			String sqrToMove = Board.board[F1][R1];
			if (sqrToMove != null) {
				sqrPiece = sqrToMove.split("[.]+");

			}

			//get colour to move
			String person = Main.colorToMove();

			//Begin moving piece
			if (canMove(person, args[0], args[1])) {
				switch (sqrPiece[1]) {
					case "Pawn":
						if (sqrPiece.length == 3) {
							Board.board[F2][R2] = (sqrToMove);
							Board.board[F1][R1] = null;
							System.out.println("Moved!");
							break;
						} else {
							Board.board[F2][R2] = (sqrToMove + ".hasMoved.ep");
							Board.board[F1][R1] = null;
							System.out.println("Moved!");
							break;
						}
					case "Rook":
						Board.board[F2][R2] = (sqrToMove + ".hasMoved");
						Board.board[F1][R1] = null;
						System.out.println("Moved!");
						break;
					case "King":
						Board.board[F2][R2] = (sqrToMove + ".hasMoved");
						Board.board[F1][R1] = null;
						System.out.println("Moved!");
						break;
					default:
						Board.board[F2][R2] = sqrToMove;
						Board.board[F1][R1] = null;
						System.out.println("Moved!");
						break;
				}

			}
		}


		}
		
	}

