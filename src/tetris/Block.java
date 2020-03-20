package tetris;

import java.awt.*;

public class Block {
    private Color color;
    private Point point;
    private Point localPoint;

    public Block(Color color, Point point) {
        this.color = color;
        this.point = point;
    }

    public Block clone() {
        return new Block(color, new Point(point.x, point.y));
    }

    public void translate(int dx, int dy) {
        point.translate(dx, dy);
    }

    public void draw(Graphics g, int size, Point fromPoint) {
        int x = point.x * size + fromPoint.x * size + 1;
        int y = point.y * size + fromPoint.y * size + 1;
        g.setColor(color);
        g.fillRect(x, y, size - 2, size - 2);
    }

    public Point getPoint() {
        return point;
    }

    public Color getColor() {
        return color;
    }

    public void fall() {
        point.y++;
    }

    public boolean checkFall(Block[][] staticBlocks) {
        if ((point.y + 1) >= staticBlocks[0].length) return false;
        else if (staticBlocks[point.x][point.y + 1] != null) return false;
        else return true;
    }

    public void lock(Block[][] staticBlocks) {
        staticBlocks[point.x][point.y] = new Block(color, new Point(point.x, point.y));
    }

    public void moveLeft() {
        point.x--;
    }

    public void moveRight() {
        point.x++;
    }

    public boolean checkRight(Block[][] staticBlocks) {
        if ((point.x + 1) >= staticBlocks.length) return false;
        else if (staticBlocks[point.x + 1][point.y] != null) return false;
        else return true;
    }

    public boolean checkLeft(Block[][] staticBlocks) {
        if ((point.x - 1) < 0) return false;
        else if (staticBlocks[point.x - 1][point.y] != null) return false;
        else return true;
    }

    public boolean check(Block[][] board) {
        if (!checkLocationOnBoard(board)) return false;
        else if (board[point.x][point.y] != null) return false;
        else return true;
    }

    public boolean checkLocationOnBoard(Block[][] board) {
        if (point.x < 0 || point.x >= board.length || point.y < 0 || point.y >= board[0].length) return false;
        else return true;
    }
}
