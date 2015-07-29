package chess.src.chess;

import PayPal_java.Chess;

import java.io.*;

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
        boolean white_captured = false, black_captured = false;
        do {
            newMove = br.readLine();
            if (newMove != null) {
                newMove = newMove.replaceAll("[0-9]+\\.|\\n", "//");

                String player_moves[] = newMove.split("//");
                for (String move : player_moves) {
                    if (move != null) {
                        System.out.println(move);
                        white_player.updatePiecePositions(move);
                        white_player.updatePiecePositions(move);
                    }
                }
            }

        }while (newMove != null);
    }


    public static void main(String[] args){
        ChessGame cg = new ChessGame();
        try {
            cg.readFile("C:\\faltUi\\untitled\\src\\chess\\Adams.pgn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

   /* if(player_moves[0].contains("x"))
                black_captured = true;
            if (player_moves[1].contains("x"))
                white_captured = true;*/