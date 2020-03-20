package tetris;

import java.awt.*;
import java.util.Random;

public enum FigureType {
    I(Color.CYAN, new Point[]{
            new Point(0,0),
            new Point(1,0),
            new Point(2,0),
            new Point(3,0),
    }),
    J(Color.BLUE, new Point[]{
            new Point(0,0),
            new Point(1,0),
            new Point(2,0),
            new Point(2,1),
    }),
    L(Color.ORANGE, new Point[]{
            new Point(1,0),
            new Point(0,1),
            new Point(0,0),
            new Point(2,0),
    }),
    O(Color.YELLOW, new Point[]{
            new Point(0,0),
            new Point(1,0),
            new Point(0,1),
            new Point(1,1),
    }),
    S(Color.GREEN, new Point[]{
            new Point(1,0),
            new Point(0,1),
            new Point(1,1),
            new Point(2,0),
    }),
    T(Color.MAGENTA, new Point[]{
            new Point(1,0),
            new Point(0,0),
            new Point(1,1),
            new Point(2,0),
    }),
    Z(Color.RED, new Point[]{
            new Point(1,0),
            new Point(0,0),
            new Point(1,1),
            new Point(2,1),
    });

    public Color color;
    public Point[] points;

    FigureType(Color color, Point[] points) {
        this.color = color;
        this.points = points;
    }

    public static FigureType getRandom() {
        Random r = new Random(System.nanoTime());
        return values()[r.nextInt(values().length)];
    }
}
