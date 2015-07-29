package chess;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class TestChess {
	@Test
	public void testCanMoveTo(){
		Piece piece=new Piece("P", 'A', 2);
		boolean canMoveTo = piece.canMoveTo('A',3,false);
		assertEquals(true,canMoveTo);
	}
	
	@Test
	public void testMove(){
        ChessGame cg = new ChessGame();
        try {
            cg.readFile(System.getProperty("user.dir")+"\\Adams.pgn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        cg.white_player.printPositions();
        cg.black_player.printPositions();
	}
}
