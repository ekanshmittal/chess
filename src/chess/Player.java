package chess.src.chess;

import java.util.ArrayList;
import java.util.List;

public class Player {
	ArrayList <Piece> pieces;
	ArrayList <Piece> otherPlayerPieces;
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
	

    public void addOtherPlayerPieces(ArrayList<Piece> opp) {
    	this.otherPlayerPieces = opp;
    }

	
	private void castle (String move) {
		if ("O-O".equals(move.toUpperCase())){
			
            Piece rook = findPiece(ChessConstants.ROOK, 'h', ChessConstants.RANK_DEFAULT);
            if (rook != null){
                rook.setX('f');
            }
            Piece king = findPiece(ChessConstants.KING, 'e', ChessConstants.RANK_DEFAULT);
            if (king != null){
                king.setX('g');
            }
        }
        else {
            Piece rook = findPiece(ChessConstants.ROOK, 'a', ChessConstants.RANK_DEFAULT);
            if (rook != null){
                rook.setX('d');
            }
            Piece king = findPiece(ChessConstants.KING, 'e', ChessConstants.RANK_DEFAULT);
            if (king != null){
                king.setX('c');
            }

        }
	}

    private Piece findPiece(String pieceName, char xCoord, int yCoord) {
        for (Piece piece:this.pieces){
            if (piece.getName().equals(pieceName) && (piece.getX() == xCoord || xCoord == ChessConstants.FILE_DEFAULT) && (piece.getY() == yCoord || yCoord == ChessConstants.RANK_DEFAULT)){
                return piece;
            }
        }
        return null;
    }
    
    private List<Piece> findAllPieces(String pieceName, char xCoord, int yCoord) {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Piece piece:this.pieces){
            if (piece.getName().equals(pieceName) && (piece.getX() == xCoord || xCoord == ChessConstants.FILE_DEFAULT) && (piece.getY() == yCoord || yCoord == ChessConstants.RANK_DEFAULT)){
                pieces.add(piece);
            }
        }
        return pieces;
    }
    

    public void apply(String move) {
		if(!Character.isUpperCase(move.charAt(0)))
            move = ChessConstants.PAWN + move;

        if (move.contains(ChessConstants.CHECK))
            move = move.substring(0, move.length() - 1);

        if (move.contains(ChessConstants.CHECKMATE)){
            move = move.substring(0, move.length() - 1);
            won = true;
        }
        
        if(move.charAt(0) == ChessConstants.CASTLE) {
			castle(move);
			return;
		}

        String toSetPieceName = null;
        if (move.contains(ChessConstants.PROMOTE)){
            toSetPieceName = move.substring(move.length() - 1);
            move = move.substring(0,move.length() - 2);
        }
                
        if(solveAmbiguity(move)){
            return;
        }

        boolean done = false;
        Piece piece = updatePiecePosition(this.pieces, move);
        if (piece != null){
            if (toSetPieceName != null)
                piece.setName(toSetPieceName);
            done = true;
        }

		if(!done)
			System.out.println("ERROR!!" + move);
	}

    public Piece updatePiecePosition(List<Piece> pieces, String move){
        String pieceName = move.charAt(0)+"";
        String position = move.substring(move.length()-2);
        Character toX = position.charAt(0);
        Integer toY = Integer.parseInt(position.substring(1));

        //check if move contains a capture
        boolean capture = false;
        if (move.contains(ChessConstants.CAPTURED))
            capture = true;
        for(Piece p: pieces) {
            if(p.getName().equals(pieceName) && p.canMoveTo(toX, toY, capture)) {
                if(pieceName.equals(ChessConstants.ROOK) && !canThisRookMove(p, toX, toY))
                    continue;

                p.setX(toX);
                p.setY(toY);

                return p;
            }
        }

        return null;
    }

    public boolean solveAmbiguity(String move){

        String xRemoved;
        if (move.contains(ChessConstants.CAPTURED))
            xRemoved = move.substring(0, move.indexOf(ChessConstants.CAPTURED)) + move.substring(move.indexOf(ChessConstants.CAPTURED) + 1);
        else
            xRemoved = move;


        char file = ChessConstants.FILE_DEFAULT;
        int rank = ChessConstants.RANK_DEFAULT;
        xRemoved = xRemoved.substring(1,xRemoved.length() - 2);
        if (xRemoved.matches(".*[a-z]+.*")) {
            file = xRemoved.charAt(0);
            xRemoved = xRemoved.substring(1,xRemoved.length());
        }

        if (xRemoved.matches(".*[0-9]+")) {
            rank = Integer.parseInt(xRemoved.charAt(0) + "");
        }

        if (file == ChessConstants.FILE_DEFAULT && rank == ChessConstants.RANK_DEFAULT )
            return false;

        updatePiecePosition(findAllPieces(move.charAt(0) + "", file, rank), move);
        return true;
    }

    public void updateAfterMove(String player_move) {
        String[] indv_moves = player_move.trim().split(" ");
        if (isWhite){
            apply(indv_moves[0]);
        }
        else {
            if (indv_moves.length > 1)
                apply(indv_moves[1]);
        }
    }
    
    public void updateIfCaptured(String player_move) {
    	String[] indv_moves = player_move.trim().split(" ");

    	if (isWhite){
    		if (indv_moves.length > 1 && indv_moves[1].contains(ChessConstants.CAPTURED))
                capture(indv_moves[1].split(ChessConstants.CAPTURED)[1]);
        }
        else {
        	if (indv_moves.length > 1 && indv_moves[0].contains(ChessConstants.CAPTURED))
                capture(indv_moves[0].split(ChessConstants.CAPTURED)[1]);
        }
    }
    
    private int distance(char x1, int y1, char x2, int y2) {
    	return Math.abs(x2 - x1) + Math.abs(y2-y1);
    }
    
    private int distance(Piece p1, Piece p2) {
    	return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    private boolean canThisRookMove(Piece p, Character x, Integer y) {
    	int myDist = distance(p.getX(), p.getY(), x, y);
    	ArrayList <Piece> allPieces = new ArrayList<Piece>(this.pieces);
    	allPieces.addAll(this.otherPlayerPieces);
    	for(Piece inbw: allPieces) {
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

        //check which Piece is in that position
        for (Piece piece:pieces){
            if (piece.getX() == xCoord && piece.getY() == yCoord){
                pieces.remove(piece);
                return;
            }
        }

        //En passant capture
        int offset = 1;
        if(!this.isWhite)
        	offset = -1;
        for (Piece piece:pieces){
            if (piece.getX() == xCoord && piece.getY() == yCoord + offset){
                pieces.remove(piece);
                return;
            }
        }
        
    }




}
