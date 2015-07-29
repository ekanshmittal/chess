package chess;

import java.util.ArrayList;
import java.util.List;

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
			pieces.add(new Piece(ChessConstants.PAWN, (char)('a'+ i), offset*5 + 2, isWhite));
		}
		for(int i = 0; i < 2; ++i) {
			pieces.add(new Piece(ChessConstants.ROOK, (char)('a'+ i*7), offset*7 + 1, isWhite));
			pieces.add(new Piece(ChessConstants.KNIGHT, (char)('a'+ i*5 + 1), offset*7 + 1, isWhite));
			pieces.add(new Piece(ChessConstants.BISHOP, (char)('a'+ i*3 + 2), offset*7 + 1, isWhite));
		}
		pieces.add(new Piece(ChessConstants.KING, (char)('a'+ 4), offset*7 + 1, isWhite));
		pieces.add(new Piece(ChessConstants.QUEEN, (char)('a'+ 3), offset*7 + 1, isWhite));
	}
	
	private void castle (String move) {
		if ("O-O".equals(move.toUpperCase())){
			
            Piece rook = findPiece(ChessConstants.ROOK, 'h', -1);
            if (rook != null){
                rook.setX('f');
            }
            Piece king = findPiece(ChessConstants.KING, 'e', -1);
            if (king != null){
                king.setX('g');
            }
        }
        else {
            Piece rook = findPiece(ChessConstants.ROOK, 'a', -1);
            if (rook != null){
                rook.setX('d');
            }
            Piece king = findPiece(ChessConstants.KING, 'e', -1);
            if (king != null){
                king.setX('c');
            }

        }
	}

    private Piece findPiece(String pieceName, char xCoord, int yCoord) {
        for (Piece piece:this.pieces){
            if (piece.getName().equals(pieceName) && (piece.getX() == xCoord || xCoord == 'm') && (piece.getY() == yCoord || yCoord == -1)){
                return piece;
            }
        }
        return null;
    }
    
    private List<Piece> findAllPieces(String pieceName, char xCoord, int yCoord) {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece piece:this.pieces){
            if (piece.getName().equals(pieceName) && (piece.getX() == xCoord || xCoord == 'm') && (piece.getY() == yCoord || yCoord == -1)){
                pieces.add(piece);
            }
        }
        return pieces;
    }
    

    public void apply(String move) {
		Character first = move.charAt(0);

		String pieceName = "";
		if(Character.isUpperCase(first))
			pieceName = first.toString();
		else {
            pieceName = ChessConstants.PAWN;
            move = ChessConstants.PAWN + move;
        }

        //handling check moves
        if (move.contains("+"))
            move = move.substring(0, move.length() - 1);

        //handling check-mate moves
        if (move.contains("#")){
            move = move.substring(0, move.length() - 1);
            won = true;
        }
        
        if(pieceName.equals("O")) {
			castle(move);
			return;
		}

        if(solveAmbiguity(move)){
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

        boolean done = false;
        
		for(Piece p: this.pieces) {
			if(p.getName().equals(pieceName) && p.canMoveTo(x, y, capture)) {
				if(pieceName.equals("R"))  {
            		if(canThisRookMove(p, x, y, capture)==false)
            			continue;
            	}
				p.setX(x);
				p.setY(y);
				done = true;
				break;
			}
		}
		if(!done)
			System.out.println("ERROR!!" + move);
	}

    public boolean solveAmbiguity(String move){

        String xRemoved = "";
        char toX;
        int toY;
        boolean capture = false;
        String pieceName;
        if (move.contains("x")) {
        	capture = true;
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
        
        for(Piece p: findAllPieces(pieceName, file, rank)) {
        	if(p.getName().equals(pieceName) && p.canMoveTo(toX, toY, capture)) {
				if(pieceName.equals("R"))  {
            		if(canThisRookMove(p, toX, toY, capture)==false)
            			continue;
            	}
				p.setX(toX);
				p.setY(toY);
				break;
			}
        }
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
            if(p.getName().equals(oldPiece) && p.canMoveTo(xCoord, yCoord, capture)) {
                p.setName(newPiece);
                p.setX(xCoord);
                p.setY(yCoord);
                break;
            }
        }
    }

    void printPositions () {
		for (Piece p: this.pieces) {
			System.out.println(p);
		}
		System.out.println();
	}


    public void updatePiecePositions(String player_move) {
        String[] indv_moves = player_move.trim().split(" ");
        if (isWhite){
            apply(indv_moves[0]);
        }
        else {
            if (indv_moves.length > 1)
                apply(indv_moves[1]);
        }
    }
    
    public void doCapture(String player_move) {
    	String[] indv_moves = player_move.trim().split(" ");
    	
    	
    	if (isWhite){
    		if (indv_moves.length > 1 && indv_moves[1].contains("x"))
                capture(indv_moves[1].split("x")[1]);
        }
        else {
        	if (indv_moves.length > 1 && indv_moves[0].contains("x"))
                capture(indv_moves[0].split("x")[1]);
        }
        //update for the own move
    }
    
    private int distance(char x1, int y1, char x2, int y2) {
    	return Math.abs(x2 - x1) + Math.abs(y2-y1);
    }
    
    private int distance(Piece p1, Piece p2) {
    	return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    private boolean canThisRookMove(Piece p, Character x, Integer y, boolean capture) {
    	int myDist = distance(p.getX(), p.getY(), x, y);
    	for(Piece inbw: this.pieces) {
    		if(inbw.equals(p)|| (inbw.getX() == x && inbw.getY() == y))
    			continue;
    		if(distance(p, inbw) + distance(inbw.getX(), inbw.getY(), x, y) == myDist) {
    			System.out.println(inbw);
    			return false;
    		}
    	}
    	return true;
    }

    private void capture(String position) {
        Character xCoord = position.charAt(0);
        Integer yCoord = Integer.parseInt(position.charAt(1) + "");
        System.out.println("Capture called");
        //check which Piece is in that position
        System.out.println(xCoord + " " + yCoord);
        for (Piece piece:pieces){
            if (piece.getX() == xCoord && piece.getY() == yCoord){
            	System.out.println("Captured");
                pieces.remove(piece);
                return;
            }
        }
    }




}
