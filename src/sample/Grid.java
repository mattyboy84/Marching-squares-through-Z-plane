package sample;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Grid {
    Line line;

    public Grid(int x1, int y1, int x2, int y2, Group group) {

        this.line = new Line(x1, y1, x2, y2);
        group.getChildren().add(this.line);
        this.line.setOpacity(0.1);
    }
}
