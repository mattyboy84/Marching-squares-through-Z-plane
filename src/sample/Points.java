package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Points {

    Circle circle;
    int i;
    int j;
    public Points(ArrayList<Points> points, ArrayList<Lines> lines, int res, int WIDTH, int HEIGHT, int cols, int rows, float[][] colour, float color, int i, int j, Group group) {
        this.circle = new Circle(i*res,j*res,(int) (res/4));
        color=(color+1)/2;
        color=color*255;
        int finalColour = (int) color;
        this.circle.setFill(Color.rgb(finalColour,finalColour,finalColour));
        this.i=i;
        this.j=j;
        this.circle.setOnMouseClicked(event -> {
            System.out.println("i: " + this.i  + " j: " + this.j);
        });
        group.getChildren().add(this.circle);

    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
