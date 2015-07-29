package chess.src.chess;

import java.util.ArrayList;

public class Player {
	ArrayList <Piece> pieces;
    boolean isWhite;
    boolean won;

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
	
	private void castle (String move) {
		if ("O-O".equals(move.toUpperCase())){
			
            Piece rook = findPiece("R", 'h', -1);
            if (rook != null){
                rook.setX('f');
            }
            Piece king = findPiece("K", 'e', -1);
            if (king != null){
                king.setX('g');
            }
        }
        else {
            Piece rook = findPiece("R", 'a', -1);
            if (rook != null){
                rook.setX('d');
            }
            Piece king = findPiece("K", 'e', -1);
            if (king != null){
                king.setX('c');
            }

        }
	}

    private Piece findPiece(String pieceName, char xCoord, int yCoord) {
        for (Piece piece:this.pieces){
            if (!piece.isCaptured() && piece.getName().equals(pieceName) && (piece.getX() == xCoord || xCoord == 'm') && (piece.getY() == yCoord || yCoord == -1)){
                return piece;
            }
        }
        return null;
    }

    public void apply(String move) {
		Character first = move.charAt(0);

		String pieceName = "";
		if(Character.isUpperCase(first))
			pieceName = first.toString();
		else {
            pieceName = "P";
            move = "P" + move;
        }

        //handling check moves
        if (move.contains("+"))
            move = move.substring(0, move.length() - 1);

        //handling check-mate moves
        if (move.contains("#")){
            move = move.substring(0, move.length() - 1);
            won = true;
        }

        if(solveAmbiguity(move)){
            return;
        }

		if(pieceName.equals("O")) {
			castle(move);
			return;
		}

        //handling promotion moves
        if (move.contains("=")){
            promote(move);
            return;
        }
        
		String position = move.substring(move.length()-2);
		Character x = position.charAt(0);
		Integer y = Integer.parseInt(position.substring(1));

        //check if move contains a capture
        boolean capture = false;
        if (move.contains("x"))
            capture = true;

		for(Piece p: this.pieces) {
			if(p.getName().equals(pieceName) && !p.isCaptured() && p.canMoveTo(x, y, capture)) {

				p.setX(x);
				p.setY(y);
				break;
			}
		}
	}

    public boolean solveAmbiguity(String move){

        String xRemoved = "";
        boolean captured = false;
        char toX;
        int toY;
        String pieceName;
        if (move.contains("x")) {
            captured = true;
            xRemoved = move.substring(0, move.indexOf("x")) + move.substring(move.indexOf("x") + 1);
        }
        else
            xRemoved = move;

        toY = Integer.parseInt(xRemoved.charAt(xRemoved.length() - 1) + "");
        toX = xRemoved.charAt(xRemoved.length() - 2);
        pieceName = xRemoved.charAt(0) + "";

        char file = 'm';
        int rank = -1;
        xRemoved = xRemoved.substring(1,xRemoved.length() - 2);
        if (xRemoved.matches(".*[a-z]+.*")) {
            file = xRemoved.charAt(0);
            xRemoved = xRemoved.substring(1,xRemoved.length());
        }

        if (xRemoved.matches(".*[0-9]+")) {
            rank = Integer.parseInt(xRemoved.charAt(0) + "");
            xRemoved = xRemoved.substring(1,xRemoved.length());
        }

        if (file == 'm' && rank == -1 )
            return false;
        Piece piece = findPiece(pieceName, file, rank);


        piece.setX(toX);
        piece.setY(toY);

        return true;
    }

    private void promote(String move) {
        String[] promote_pieces = move.split("=");
        String oldPiece = promote_pieces[0].charAt(0) + "";
        char xCoord = promote_pieces[0].charAt(1);
        int yCoord = Integer.parseInt(promote_pieces[0].charAt(1) + "");
        String newPiece = promote_pieces[1].charAt(1) + "";

        boolean capture = false;
        if (move.contains("x"))
            capture = true;

        if (move.contains("#"))
            won = true;

        for(Piece p: this.pieces) {
            if(p.getName().equals(oldPiece) && !p.isCaptured() && p.canMoveTo(xCoord, yCoord, capture)) {
                p.setName(newPiece);
                p.setX(xCoord);
                p.setY(yCoord);
                break;
            }
        }
    }

    void printPositions () {
		for (Piece p: this.pieces) {
			if(!p.isCaptured())
				System.out.println(p);
		}
		System.out.println();
	}


    public void updatePiecePositions(String player_move) {
        String[] indv_moves = player_move.trim().split(" ");
        if (isWhite){
            if (indv_moves.length > 1 && indv_moves[1].contains("x"))
                capture(indv_moves[1].split("x")[1]);

            //update for the own move
            apply(indv_moves[0]);
        }
        else {
            //update if any captured
            if (indv_moves[0].contains("x"))
                capture(indv_moves[0].split("x")[1]);

            //update for the own move
            if (indv_moves.length > 1)
                apply(indv_moves[1]);
        }
    }

    private void capture(String position) {
        Character xCoord = position.charAt(0);
        Integer yCoord = Integer.parseInt(position.charAt(1) + "");
        //check which Piece is in that position
        for (Piece piece:pieces){
            if (piece.getX() == xCoord && piece.getY() == yCoord){
                piece.capture();
            }
        }
    }




}
