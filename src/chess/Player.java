package chess;

import java.util.ArrayList;

public class Player {
	ArrayList <Piece> pieces;
	Player(boolean isWhite) {
		pieces = new ArrayList<Piece> ();
		int offset = 1;
		if(isWhite)
			offset = 0;
		for(int i = 0; i < 8; ++i) {
			pieces.add(new Piece("P", (char)('a'+ i), offset*5 + 2));
		}
		for(int i = 0; i < 2; ++i) {
			pieces.add(new Piece("R", (char)('a'+ i*7), offset*8));
			pieces.add(new Piece("N", (char)('a'+ i*5 + 1), offset*8));
			pieces.add(new Piece("B", (char)('a'+ i*3 + 2), offset*8));
		}
		pieces.add(new Piece("K", (char)('a'+ 4), offset*8));
		pieces.add(new Piece("Q", (char)('a'+ 3), offset*8));
	}
	
	void apply(String move) {
		pieces.get(0).setX('d');
		pieces.get(1).delete();
	}
	
	void printPositions () {
		for (Piece p: this.pieces) {
			if(!p.isDeleted())
				System.out.println(p);
		}
	}

    public void updatePiecePositions(String player_move, boolean captured) {
    }
    
}
