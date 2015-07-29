package chess.src.chess;

/**
 * Created by test on 7/29/2015.
 */
public class Piece {
	private String name;
	private Character x;
	private Integer y;
	private boolean captured;

	public Piece(String name, Character x, Integer y) {
		this.name = name;
		this.x = x;
		this.y = y;
		captured = false;
	}
	
	public boolean isCaptured() {
		return this.captured;
	}

	public void capture() {
		this.captured = true;
	}

	public char getX() {
		return x;
	}

	public void setX(char x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

    public void setName(String name){
        this.name = name;
    }
	
	public boolean canMoveTo(Character x, Integer y, boolean isCaptureMove) {
		if (ChessConstants.PAWN.equals(name)) {
			if ((Math.abs(x - this.x) == 1 && isCaptureMove) || this.x == x) {
				if (this.y == 2 || this.y == 7) {
					return Math.abs(this.y - y) <= 2;
				} else {
					return Math.abs(this.y - y) == 1;
				}
			} else {
				return false;
			}
		} else if (ChessConstants.ROOK.equals(name)) {
			return this.x == x || this.y == y;
		} else if (ChessConstants.KNIGHT.equals(name)) {
			return (Math.abs(this.x - x) == 2 && Math.abs(this.y - y) == 1)
					|| (Math.abs(this.y - y) == 2 && Math.abs(this.x - x) == 1);
		} else if (ChessConstants.BISHOP.equals(name)) {
			return Math.abs(this.x - x) == Math.abs(this.y - y);
		}
		return true;
	}
	
	@Override
	public String toString() {
		return name + " " + x + y;
	}
}