package chess;
import chess.Piece;

import java.util.ArrayList;

public class Player {
	ArrayList <Piece> pieces;
    boolean isWhite;
	public Player(boolean isWhite) {
        this.isWhite = isWhite;
		pieces = new ArrayList<Piece> ();
		int offset = 1;
		if(isWhite)
			offset = 0;
		for(int i = 0; i < 8; ++i) {
			pieces.add(new Piece("P", (char)('a'+ i), offset*5 + 2));
		}
		for(int i = 0; i < 2; ++i) {
			pieces.add(new Piece("R", (char)('a'+ i*7), offset*7 + 1));
			pieces.add(new Piece("N", (char)('a'+ i*5 + 1), offset*7 + 1));
			pieces.add(new Piece("B", (char)('a'+ i*3 + 2), offset*7 + 1));
		}
		pieces.add(new Piece("K", (char)('a'+ 4), offset*7 + 1));
		pieces.add(new Piece("Q", (char)('a'+ 3), offset*7 + 1));
	}
	
	public void apply(String move) {
		Character first = move.charAt(0);
		String pieceName = "";
		if(Character.isUpperCase(first))
			pieceName = first.toString();
		else
			pieceName = "P";
		String position = move.substring(move.length()-2);
		Character x = position.charAt(0);
		Integer y = Integer.parseInt(position.substring(1));
		for(Piece p: this.pieces) {
			if(p.getName().equals(pieceName) && !p.isCaptured() && p.canMoveTo(x, y)) {
				p.setX(x);
				p.setY(y);
				break;
			}
		}
	}
	
	void printPositions () {
		for (Piece p: this.pieces) {
			if(!p.isCaptured())
				System.out.println(p);
		}
	}


    public void updatePiecePositions(String player_move) {
        String[] indv_moves = player_move.trim().split(" ");
        if (isWhite){
            //update if any captured
            if (indv_moves[1].contains("x"))
                capture(indv_moves[1].split("x")[1]);

            //update for the own move
            apply(indv_moves[0]);
        }
        else {
            //update if any captured
            if (indv_moves[0].contains("x"))
                capture(indv_moves[0].split("x")[1]);

            //update for the own move
            apply(indv_moves[1]);
        }
    }

    private void capture(String position) {
        
    }


}
