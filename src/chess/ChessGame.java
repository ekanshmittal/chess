package chess;

import java.io.*;
import java.util.Arrays;

public class ChessGame {

	Player white_player;
	Player black_player;
	boolean isWhite = true;

	public ChessGame() {
		white_player = new Player(isWhite);
		black_player = new Player(!isWhite);
	}

	public void readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(fileName)));
		String newMove;
		do {
			newMove = br.readLine();
			if (newMove != null) {
				newMove = newMove.replaceAll("[0-9]+\\.|\\n", "*");
				String init_player_moves[] = newMove.split("\\*");
				String[] player_moves = Arrays.copyOfRange(init_player_moves,
						1, init_player_moves.length);
				for (String move : player_moves) {
					if (move != null) {
						white_player.updatePiecePositions(move);
						black_player.updatePiecePositions(move);
					}
				}
			}

		} while (newMove != null);
		br.close();
	}

	public void applyMove(String move) {
		this.white_player.updatePiecePositions(move);
		this.black_player.updatePiecePositions(move);
	}

	public void displayChessBoard() {
		BoardSquare[][] board = new BoardSquare[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new BoardSquare();
			}
		}
		for (Piece piece : white_player.pieces) {
			if (!piece.isCaptured()) {
				board[8 - (piece.getY())][piece.getX() - 'a'].pieceName = "W_"
						+ piece.getName();
			}
		}
		for (Piece piece : black_player.pieces) {
			if (!piece.isCaptured()) {
				board[8 - (piece.getY())][piece.getX() - 'a'].pieceName = "B_"
						+ piece.getName();
			}
		}
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				System.out.println("   A   B   C   D   E   F   G   H ");
			}
			for (int j = 0; j < 8; j++) {
				if (j == 0) {
					System.out.print(i + 1 + " ");
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
