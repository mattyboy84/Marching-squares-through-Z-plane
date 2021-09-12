package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {




    ArrayList<Points> points = new ArrayList<>();
    ArrayList<Lines> lines = new ArrayList<>();
    ArrayList<Grid> grid = new ArrayList<>();
    int res = 20;//default is 40
    //1; 2; 3; 4; 5; 6; 8; 10; 12; 15; 20; 24; 30; 40; 60 and 120
    int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    int cols = (WIDTH / res) + 1;
    int rows = (HEIGHT / res) + 1;
    float[][] colour = new float[cols][rows];
    Random random = new Random();
    SimplexNoise noise = new SimplexNoise(random.nextInt(100));

    Group group = new Group();
    Scene scene = new Scene(group, WIDTH, HEIGHT, Color.GRAY);

    float xoff = 0;
    float yoff = 0;
    float zoff = 0;

    @Override
    public void start(Stage stage) {


        for (int j = 0; j < cols; j++) {
            grid.add(new Grid(j * res, 0, j * res, HEIGHT, group));
        }
        for (int j = 0; j < rows; j++) {
            grid.add(new Grid(0, j * res, WIDTH, j * res, group));
        }
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case P:
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            System.out.print(colour[j][i] + " ");
                        }
                        System.out.println();
                    }
                    break;
                case C:
                    for (Points point : points) {
                        point.getCircle().setVisible(!point.getCircle().isVisible());
                    }
                    break;
                case L:
                    System.out.println("points size: " + points.size());

                    System.out.println("lines size: " + lines.size());
                    break;
            }
        });
        setUp();

        Timeline ThreeD = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            zoff = (float) (zoff + 0.01);
            xoff=0;
            yoff=0;
            for (Points point : points) {
                group.getChildren().remove(point.getCircle());
            }
            points.clear();
            for (Lines line : lines) {
                group.getChildren().remove(line.getLine());
            }
            lines.clear();
            setUp();

        }));
        ThreeD.setCycleCount(Timeline.INDEFINITE);
        ThreeD.play();


        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    private void setUp() {

        for (int i = 0; i < cols; i++) {
            xoff = (float) (xoff + 0.1);
            yoff = 0;
            for (int j = 0; j < rows; j++) {
                colour[i][j] = (float) noise.noise(xoff, yoff, zoff);
                points.add(new Points(points, lines, res, WIDTH, HEIGHT, cols, rows, colour, colour[i][j], i, j, group));
                yoff = (float) (yoff + 0.1);
            }
        }


        for (int i = 0; i < cols - 1; i++) {
            for (int j = 0; j < rows - 1; j++) {
                int x = i * res;
                int y = j * res;
                int offset = (int) (res * 0.5);
                int ax, ay, bx, by, cx, cy, dx, dy;
                ax = x + offset;
                ay = y;
                //
                bx = x + res;
                by = y + offset;
                //
                cx = x + offset;
                cy = y + res;
                //
                dx = x;
                dy = y + offset;


                int state = stateGetter(Math.ceil(colour[i][j + 1]), Math.ceil(colour[i + 1][j + 1]), Math.ceil(colour[i + 1][j]), Math.ceil(colour[i][j]));
                switch (state) {
                    case 1:
                    case 14:
                        lines.add(new Lines(cx, cy, dx, dy, group, state));
                        break;
                    case 2:
                    case 13:
                        lines.add(new Lines(bx, by, cx, cy, group, state));
                        break;
                    case 3:
                    case 12:
                        lines.add(new Lines(bx, by, dx, dy, group, state));
                        break;
                    case 4:
                    case 11:
                        lines.add(new Lines(ax, ay, bx, by, group, state));
                        break;
                    case 5:
                        lines.add(new Lines(bx, by, cx, cy, group, state));
                        lines.add(new Lines(ax, ay, dx, dy, group, state));
                        break;
                    case 6:
                    case 9:
                        lines.add(new Lines(ax, ay, cx, cy, group, state));
                        break;
                    case 7:
                    case 8:
                        lines.add(new Lines(ax, ay, dx, dy, group, state));
                        break;
                    case 10:
                        lines.add(new Lines(ax, ay, bx, by, group, state));
                        lines.add(new Lines(cx, cy, dx, dy, group, state));
                        break;
                    case 15:

                        break;
                }
            }
        }
    }

    private int stateGetter(double i, double i1, double i2, double i3) {
        return (int) ((i * 1) + (i1 * 2) + (i2 * 4) + (i3 * 8));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
