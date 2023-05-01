package boardgame.model;

import javafx.scene.paint.Color;

public enum PlayerColor {
    RED,
    BLUE;

    public Color getColorCode() {
        return switch (this) {
            case RED ->  Color.rgb(250,15,15);
            case BLUE ->  Color.rgb(18,101,255);
        };
    }
}
