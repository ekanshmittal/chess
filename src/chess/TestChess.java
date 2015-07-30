package chess.src.chess;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TestChess {
	@Test
	public void testCanMoveTo(){
		Piece piece=new Piece("P", 'a', 2, true);
		assertEquals(true, piece.canMoveTo('a',3,false));
		assertEquals(false, piece.canMoveTo('e', 4, false));
	}
	
	@Test
	public void testMove(){
        ChessGame cg = new ChessGame();
        try {
            cg.readFile(System.getProperty("user.dir")+"\\src\\chess\\complete.pgn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        cg.displayChessBoard();
	}
	
	@Test
	public void testMov(){

	}
	@Test
	public void testPrint(){
	}
}
