package chess;

/**
 * Created by test on 7/29/2015.
 */
public class Piece {
    char current_xCoord;
    int current_yCoord;
    String name;

    public Piece(char current_xCoord, int current_yCoord, String name){
        this.current_xCoord = current_xCoord;
        this.current_yCoord = current_yCoord;
        this.name = name;
    }

}
