package boardgame.model;
import java.util.List;

public record Player(List<Position> positions, PlayerColor color) {}
