package puzzles.tipover;

import util.Coordinates;

/**
 * An extension of the Coordinates class to allow for a letter to be stored inside
 */
public class TipCoordinates extends Coordinates {
    private String letter;

    /**
     * Constructor
     * @param row int of the row where the coordinate lies
     * @param col int of the column where the coordinate lies
     * @param letter the string letter associated with the coordinate
     */
    public TipCoordinates(int row, int col, String letter) {
        super(row, col);
        this.letter = letter;
    }

    /**
     *
     * @return Returns the letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Sets the letter at the coordinate
     * @param letter String
     */
    public void setLetter(String letter) { this.letter = letter; }

    @Override
    public String toString() {
        return getLetter();
    }
}
