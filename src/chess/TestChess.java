package chess;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TestChess {
	@Test
	public void testCanMoveTo(){
		Piece piece=new Piece("P", 'a', 2);
		assertEquals(true, piece.canMoveTo('a',3,false));
		assertEquals(false, piece.canMoveTo('e', 4, false));
	}
	
	@Test
	public void testMove(){
//        ChessGame cg = new ChessGame();
//        try {
//            cg.readFile(System.getProperty("user.dir")+"\\Adams.pgn");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       
	}
	
	@Test
	public void testMov(){
		ChessGame cg = new ChessGame();
		cg.applyMove("e4 e5");
		cg.applyMove("Nf3 Nc6");
		cg.applyMove("Bb5 a6");
		cg.applyMove("Ba4 Nf6");
		cg.applyMove("O-O Be7");
		
		cg.white_player.printPositions();
	    cg.black_player.printPositions();
	}
	@Test
	public void testPrint(){
		ChessGame cg = new ChessGame();
		cg.applyMove("e4 e5");
		cg.white_player.printPositions();
		cg.displayChessBoard();
	}
}
