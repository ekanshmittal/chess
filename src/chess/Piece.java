package chess;

/**
 * Created by test on 7/29/2015.
 */
public class Piece {
	private String name;
	private Character x;
	private Integer y;

	public Piece(String name, Character x, Integer y) {
		this.name = name;
		this.x = x;
		this.y = y;
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