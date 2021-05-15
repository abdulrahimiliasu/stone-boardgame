package boardgame.model;

/**
 * Class for representing the position of a square on a board.
 *
 * @param row row value of the square
 * @param col column value of the square
 */
public record Position(int row, int col) {
    /***
     * {@link java.lang.Class#toString()} method.
     */
    public String toString() { return String.format("(%d,%d)", row, col); }
}
