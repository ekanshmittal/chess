package chess;

import java.io.IOException;

import org.junit.Test;

public class TestChess {
	@Test
	public void testCanMoveTo(){
		
	}
	
	@Test
	public void testMove(){
		Player white = new Player(true);
		Player black = new Player(false);
		white.apply("eb5");
		white.printPositions();
		black.printPositions();
        ChessGame cg = new ChessGame();
        try {
            cg.readFile("C:\\faltUi\\untitled\\src\\chess\\Adams.pgn");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
