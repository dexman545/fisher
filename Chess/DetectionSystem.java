package Chess;

//detect various parts of endgame. 

public class DetectionSystem {
	
	public static boolean detectCheck() {
		
		boolean b = false;
		
		//get color to move
		String color = "w"; 
		if (Main.turn%2 == 0) {
			color = "w"; 
		} else color = "b";
		
		//Invert color to get the enemy of who is being checked for check
		String color2 = "w";
		if (color.contentEquals("b")) {
			color2 = "w";
		} 
		
		for (int i=0; i < 8 ; i++) {
		   	for (int j=0; j < 8 ; j++) {
		   		if (Board.board[i][j] != null) {
		   			String[] piece = Board.board[i][j].split("[.]+");
		   			String F1 = Main.getCharForNumber((i+1));
		   			String R1 = Integer.toString((j+1));
		     	
		   			if (piece[1].contentEquals("King")) {
		   				if (piece[0].contentEquals(color)) {
		   					for (int u=0; u < 8 ; u++) {
				    		   	for (int o=0; o < 8 ; o++) {
				    		   		String F2 = Main.getCharForNumber((u+1));
				    		     	String R2 = Integer.toString((o+1));
				    		     	//System.out.println(F2 + R2);
				    		     	//System.out.println(Movement.canMove(color2, F2 + R2, F1 + R1));
				    		     	b = Movement.canMove(color2, F2 + R2, F1 + R1) || b;
				    		   	} 
				    		   	
				    		}
		   				}
		     	
		   			} 
		   	}
		}
		}
		
		return b;
		
	}
	
    public static boolean detectStalemate() {
        boolean b = true;
        if (!detectCheck()) {
            for (int i=0; i < 8 ; i++) {
                for (int j=0; j < 8 ; j++) {
                    if (Board.board[i][j] != null) {
                        String[] piece = Board.board[i][j].split("[.]+");
                        String F1 = Main.getCharForNumber((i+1));
                        String R1 = Integer.toString((j+1));
                        
                        if (piece[0].contentEquals("w")) {
                            String color = "w";
                                    switch (piece[1]) {
                                    case "Pawn": 
                                    	int u = 1;
                                    	int staggeredIncrement = 0;
                                    	for (int z = 1; z < 3; z += ++staggeredIncrement%3 == 2 ? 1 : 0) {
                                    		String F2 = Main.getCharForNumber((i+u));
                                            String R2 = Integer.toString((j+1+z));
                                            if (Movement.canMove(color, F1 + R1, F2 + R2)) {
                                                b = false;
                                                //return b;
                                            } else u = u - 1;
                                    	}
                                    case "Rook":
                                    	u = 1;
                                    	for (int z = 0; z < 2; z++) {
                                    		String F2 = Main.getCharForNumber((i+u));
                                            String R2 = Integer.toString((j+1+z));
                                            if (Movement.canMove(color, F1 + R1, F2 + R2)) {
                                                b = false;
                                                //return b;
                                            } else u = u - 1;
                                    	}
                                    case "Bishop":
                                    	u = 1;
                                    	staggeredIncrement = 0;
                                    	for (int z = 1; z > -2; z -= ++staggeredIncrement%2 == 0 ? 2 : 0) {
                                    		String F2 = Main.getCharForNumber((i+u));
                                            String R2 = Integer.toString((j+1+z));
                                            System.out.println(F1 + R1 + " " + F2 + R2);
                                            if (Movement.canMove(color, F1 + R1, F2 + R2)) {
                                                b = false;
                                                //return b;
                                            } else u = u - 2;
                                    	}
                                    case "Queen":
                                    case "Knight":
                                    case "King":
                                    	
                                    }
                   
                        } 
                }
            }
            }
           
        }
       
        return b;
       
    }
	
	public static boolean detectMate() {
		if (detectCheck() && !escapePosibilities()) {
			return true;
		}
			
		return false;
		
	}
	
	public static boolean endGame() {
		if (detectStalemate() | detectMate()) {
			System.out.println("A mate has occurred!");
			return true;
		}
		//System.out.println(detectStalemate());
		return false;
	}
	
	//Checks whether or not check can be avoided
	public static boolean escapePosibilities() {
		String[][] tempBoard = Board.board;
		boolean canEscape = false;
		
		for (int i=0; i < 8 ; i++) {
		   	for (int j=0; j < 8 ; j++) {
		   		if (Board.board[i][j] != null) {
		   			String F1 = Main.getCharForNumber((i+1));
		   			String R1 = Integer.toString((j+1));
		   				
		   				
		   				for (int u=0; u < 8 ; u++) {
			    		   	for (int o=0; o < 8 ; o++) {
			    		   		String F2 = Main.getCharForNumber((u+1));
			    		     	String R2 = Integer.toString((o+1));
			    		     	Movement.movePiece(F1+R1, F2+R2);
			    		     	if (DetectionSystem.detectCheck()) {
			    		     		for (int h=0; h < 8 ; h++) {
			    		     			for (int g=0; g < 8 ; g++) {
			    		     				Board.board[h][g] = tempBoard[h][g];
			    		     				canEscape = !detectCheck() || canEscape;
			    		     			}
			    		     		}
			    		     	}
			    		   	}
		   				} 
		   		}
		   	}
		} return canEscape;
	}
		

}