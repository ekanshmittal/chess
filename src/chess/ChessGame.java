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
                        white_player.updatePiecePositions(move);
                        black_player.updatePiecePositions(move);
                    }
                }
            }

        }while (newMove != null);
        br.close();
    }
    
    public void applyMove(String move) {
    	this.white_player.updatePiecePositions(move);
    	this.black_player.updatePiecePositions(move);
    }

}
