package chess;

import java.io.*;

public class ChessGame {
    Player white_player;
    Player black_player;

    public ChessGame(){
        white_player = new Player();
        black_player = new Player();
    }

    public void readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        String newMove;
        boolean white_captured = false, black_captured = false;
        do {
            newMove = br.readLine();
            String player_moves[] = newMove.split(" ");
            if(player_moves[0].contains("x"))
                black_captured = true;
            if (player_moves[1].contains("x"))
                white_captured = true;

            white_player.updatePiecePositions(newMove, white_captured);
            white_player.updatePiecePositions(newMove, black_captured);
        }while (newMove != null);
    }



}
