package Chess;

import java.io.*;
import Chess.Board;

public interface FileHandler {
	public static void read(String...args){
		//reads inputs
        String fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + "ABChess Save Games" + System.getProperty("file.separator") + args[0] + System.getProperty("file.separator") + args[1] + ".txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String breaks = "[ ]+";
                String[] readOut = line.split(breaks);
                for (int i=0; i < 8; i++)
        		   	for (int j=0; j < 8 ; j++)
        		     	Board.board[j][i] = readOut[i];
                System.out.println("Loaded Save");
            }   

            bufferedReader.close();         
        }
        
        catch(FileNotFoundException ex) {
            System.out.println("Unable to find save file.");  
            
        }
        
        catch(IOException ex) {
            System.out.println("Error reading save file.");     
            
        }
	}
	
	public static void save(String... args) {
		//args in terms of save folder, then the file to be save
		//Create save folder if it doesn't exist
		String saveFolder = System.getProperty("user.dir") + System.getProperty("file.separator") + "ABChess Save Games";
		File saveF = new File(saveFolder);
		if(!saveF.exists()) {
			saveF.mkdir();
		}
		
		//save the board to file and directory
		try {
			String[][] board = Board.board;
			String fileName = args[0]+".txt";
			String savePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "ABChess Save Games" + System.getProperty("file.separator") + args[0];
			File saveLocation = new File(savePath);
			    if(!saveLocation.exists()){
			         saveLocation.mkdir();
			         File myFile = new File(savePath, args[1] + ".txt");
			         PrintWriter fw = new PrintWriter(new FileWriter(myFile));
			         if (args[1].contentEquals("Board")) {
			         	for (int i=0; i < board.length ; i++)
			    		   		for (int j=0; j < board[i].length ; j++)
			    		   			fw.write(board[i][j]+ " ");
			         }
			         fw.close();
			     } else {
			    	 File myFile = new File(savePath, args[1] + ".txt");
			         PrintWriter fw = new PrintWriter(new FileWriter(myFile));
			         if (args[1].contentEquals("Board")) {
				         	for (int i=0; i < board.length ; i++)
				    		   		for (int j=0; j < board[i].length ; j++)
				    		   			fw.write(board[i][j]+ " ");
				     }
			         fw.close();
			    	 
			     }
            
            System.out.println("Game Saved");
        }
        
        catch(IOException ex) {
            System.out.println("Error writing to file '" + args[0] + "'");
            
        }
		catch(NullPointerException ex) {
			System.out.println("Input is not valid! Please fix!");
		}

}
}
