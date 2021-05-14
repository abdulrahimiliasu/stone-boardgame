package boardgame.model;

public record Position(int row, int col) {
    public String toString() {
        return String.format("(%d,%d)", row, col);
        }
}
