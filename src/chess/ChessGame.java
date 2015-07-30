package chess.src.chess;

import java.io.*;
import java.util.Arrays;

public class ChessGame {

	Player white_player;
	Player black_player;
	boolean isWhite = true;

	public ChessGame() {
		white_player = new Player(isWhite);
		black_player = new Player(!isWhite);
		white_player.addOtherPlayerPieces(black_player.pieces);
		black_player.addOtherPlayerPieces(white_player.pieces);
	}

	public void readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(fileName)));
		String newMove;
		int count = 1;
		do {
			newMove = br.readLine();
            //System.out.println(newMove);
			if (newMove != null) {
				newMove = newMove.replaceAll("[0-9]+\\.|\\n", "*");
				String init_player_moves[] = newMove.split("\\*");
				String[] player_moves = Arrays.copyOfRange(init_player_moves,
						1, init_player_moves.length);
				
				for (String move : player_moves) {
					if (move != null) {
						white_player.updateAfterMove(move);
						black_player.updateIfCaptured(move);
						black_player.updateAfterMove(move);
						white_player.updateIfCaptured(move);
					}
					System.out.println(count);
					displayChessBoard();
					count++;
				}
			}

		} while (newMove != null);
		br.close();
	}

	public void displayChessBoard() {
		BoardSquare[][] board = new BoardSquare[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new BoardSquare();
			}
		}
		for (Piece piece : white_player.pieces) {
			if(board[8 - (piece.getY())][piece.getX() - 'a'].pieceName != null)
				System.out.println("ERROR MORE THAN 1 PIECE");
			board[8 - (piece.getY())][piece.getX() - 'a'].pieceName = "W_"
					+ piece.getName();
		}
		for (Piece piece : black_player.pieces) {
			
			if(board[8 - (piece.getY())][piece.getX() - 'a'].pieceName != null)
				System.out.println("ERROR MORE THAN 1 PIECE");
			board[8 - (piece.getY())][piece.getX() - 'a'].pieceName = "B_"
					+ piece.getName();
			
		}
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				System.out.println("   A   B   C   D   E   F   G   H ");
			}
			for (int j = 0; j < 8; j++) {
				if (j == 0) {
					System.out.print(8 - i + " ");
				}
				if (board[i][j].pieceName != null) {
					System.out.print(board[i][j].pieceName + " ");
				} else {
					System.out.print("    ");
				}
			}
			System.out.println();
		}
	}

	private class BoardSquare {
		String pieceName;
	}
}
