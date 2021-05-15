package boardgame.model;

/**
 * Class for representing the position of a square on a board.
 */
public record Position(int row, int col) {
    public String toString() { return String.format("(%d,%d)", row, col); }
}
