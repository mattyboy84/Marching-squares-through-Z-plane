package sample;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Lines {
    Line line;
    int state;

    public Lines(int x1, int y1, int x2, int y2, Group group, int state) {

        this.line = new Line(x1, y1, x2, y2);
        this.state = state;
        group.getChildren().add(this.line);
        this.line.setStrokeWidth(3);

    }


    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
