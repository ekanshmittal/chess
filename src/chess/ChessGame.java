package chess;

import java.io.*;
import java.util.Arrays;

public class ChessGame {

	
    Player white_player;
    Player black_player;
    boolean isWhite = true;

    public ChessGame(){
        white_player = new Player(isWhite);
        black_player = new Player(!isWhite);

    }

    public void readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        String newMove;
        do {
            newMove = br.readLine();
            if (newMove != null) {
                newMove = newMove.replaceAll("[0-9]+\\.|\\n", "*");
                String init_player_moves[] = newMove.split("\\*");
                String[] player_moves = Arrays.copyOfRange(init_player_moves, 1, init_player_moves.length);
                for (String move : player_moves) {
                    if (move != null) {
//                        System.out.println(move);
                        white_player.updatePiecePositions(move);
                        black_player.updatePiecePositions(move);
                    }
                }
            }

        }while (newMove != null);
        br.close();
        white_player.printPositions();
        black_player.printPositions();

    }


    public static void main(String[] args){
    	Player white = new Player(true);
		Player black = new Player(false);
		white.apply("eb5");
		white.printPositions();
		black.printPositions();
        ChessGame cg = new ChessGame();
        try {
            cg.readFile(System.getProperty("user.dir")+"\\Adams.pgn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

   /* if(player_moves[0].contains("x"))
                black_captured = true;
            if (player_moves[1].contains("x"))
                white_captured = true;*/
