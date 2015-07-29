package chess;

/**
 * Created by test on 7/29/2015.
 */
public class Piece {
	private Character x;
	private Integer y;
	private String name;

	public Piece(Character x, Integer y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
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