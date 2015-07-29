package chess;

import java.io.*;

public class ChessGame {
	public static void main(String[] args){
		Player white = new Player(true);
		Player black = new Player(false);
		white.apply("eb5");
		white.printPositions();
		black.printPositions();
	}
	
    Player white_player;
    Player black_player;

    public ChessGame(Player white_player,Player black_player){
        this.white_player = white_player;
        this.black_player = black_player;
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
