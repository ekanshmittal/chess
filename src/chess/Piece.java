package chess;

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

	public boolean canMoveTo(Character x, Integer y) {
		if ("P".equals(name)) {

		} else if ("R".equals(name)) {
		} else if ("N".equals(name)) {
		} else if ("B".equals(name)) {
		} else if ("K".equals(name)) {
		} else if ("Q".equals(name)) {
		}

		return true;
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

	@Override
	public String toString() {
		return name + " " + x + y;
	}
}