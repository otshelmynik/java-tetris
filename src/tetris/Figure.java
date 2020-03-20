package tetris;

import java.awt.*;

public class Figure {
    private Block[] blocks;
    private FigureType figureType;
    private int rotations;
    private Block[] newBlocks;

    public Figure(FigureType figureType) {
        this.figureType = figureType;
        blocks = new Block[4];

        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(figureType.color, new Point(figureType.points[i].x, figureType.points[i].y));
        }
    }

    public Figure() {
        setRandomFigure();
    }

    public FigureType getFigureType() {
        return figureType;
    }

    private void setRandomFigure() {
        blocks = new Block[4];
        figureType = FigureType.getRandom();

        for (int i = 0; i < 4; i++) {
            blocks[i] = new Block(figureType.color, new Point(figureType.points[i].x, figureType.points[i].y));
        }
    }

    public void draw(Graphics g, int size, Point fromPoint) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(g, size, fromPoint);
        }
    }

    public void moveToBoard() {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].translate(3, 0);
        }
    }

    public void fall() {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].fall();
        }
    }

    public boolean checkFall(Block[][] staticBlocks) {
        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i].checkFall(staticBlocks))
                return false;
        }

        return true;
    }

    public void lockBlocks(Block[][] staticBlocks) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].lock(staticBlocks);
        }
    }

    public void moveLeft() {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].moveLeft();
        }
    }

    public void moveRight() {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].moveRight();
        }
    }

    public boolean checkLeft(Block[][] staticBlocks) {
        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i].checkLeft(staticBlocks))
                return false;
        }

        return true;
    }

    public boolean checkRight(Block[][] staticBlocks) {
        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i].checkRight(staticBlocks))
                return false;
        }

        return true;
    }

    public boolean checkRotate(Block[][] board) {
        newBlocks = new Block[4];
        for (int i = 0; i < blocks.length; i++) {
            newBlocks[i] = blocks[i].clone();
        }

        switch (figureType) {
            case I:
                switch (rotations % 4) {
                    case 0: case 2:
                        newBlocks[3].translate(-1, 1);
                        newBlocks[1].translate(1, -1);
                        newBlocks[0].translate(2, -2);
                        break;
                    case 1: case 3:
                        newBlocks[3].translate(1, -1);
                        newBlocks[1].translate(-1, 1);
                        newBlocks[0].translate(-2, 2);
                        break;
                }
                break;
            case J:
                switch (rotations % 4) {
                    case 0:
                        newBlocks[0].translate(1, -1);
                        newBlocks[2].translate(-1, 1);
                        newBlocks[3].translate(-2, 0);
                        break;
                    case 1:
                        newBlocks[0].translate(1, 1);
                        newBlocks[2].translate(-1, -1);
                        newBlocks[3].translate(0, -2);
                        break;
                    case 2:
                        newBlocks[0].translate(-1, 1);
                        newBlocks[2].translate(1, -1);
                        newBlocks[3].translate(2, 0);
                        break;
                    case 3:
                        newBlocks[0].translate(-1, -1);
                        newBlocks[2].translate(1, 1);
                        newBlocks[3].translate(0, 2);
                        break;
                }
                break;
            case L:
                switch (rotations % 4) {
                    case 0:
                        newBlocks[1].translate(0, -2);
                        newBlocks[2].translate(1, -1);
                        newBlocks[3].translate(-1, 1);
                        break;
                    case 1:
                        newBlocks[1].translate(2, 0);
                        newBlocks[2].translate(1, 1);
                        newBlocks[3].translate(-1, -1);
                        break;
                    case 2:
                        newBlocks[1].translate(0, 2);
                        newBlocks[2].translate(-1, 1);
                        newBlocks[3].translate(1, -1);
                        break;
                    case 3:
                        newBlocks[1].translate(-2, 0);
                        newBlocks[2].translate(-1, -1);
                        newBlocks[3].translate(1, 1);
                        break;
                }
                break;
            case T:
                switch (rotations % 4) {
                    case 0:
                        newBlocks[1].translate(1, 1);
                        newBlocks[2].translate(-1, -1);
                        newBlocks[3].translate(-1, -1);
                        break;
                    case 1:
                        newBlocks[1].translate(1, -1);
                        newBlocks[2].translate(1, -1);
                        newBlocks[3].translate(-1, 1);
                        break;
                    case 2:
                        newBlocks[1].translate(-1, -1);
                        newBlocks[2].translate(1, 1);
                        newBlocks[3].translate(1, 1);
                        break;
                    case 3:
                        newBlocks[1].translate(-1, 1);
                        newBlocks[2].translate(-1, 1);
                        newBlocks[3].translate(1, -1);
                        break;
                }
                break;
            case S:
                switch (rotations % 4) {
                    case 0: case 2:
                        newBlocks[1].translate(0, -2);
                        newBlocks[2].translate(-1, -1);
                        newBlocks[3].translate(-1, 1);
                        break;
                    case 1: case 3:
                        newBlocks[1].translate(0, 2);
                        newBlocks[2].translate(1, 1);
                        newBlocks[3].translate(1, -1);
                        break;
                }
                break;
            case Z:
                switch (rotations % 4) {
                    case 0: case 2:
                        newBlocks[1].translate(1, -1);
                        newBlocks[2].translate(-1, -1);
                        newBlocks[3].translate(-2, 0);
                        break;
                    case 1: case 3:
                        newBlocks[1].translate(-1, 1);
                        newBlocks[2].translate(1, 1);
                        newBlocks[3].translate(2, 0);
                        break;
                }
                break;
        }
        return checkNewBlocks(board);
    }

    public void rotate() {
        for (int i = 0; i < newBlocks.length; i++) {
            blocks[i] = newBlocks[i].clone();
        }
        rotations++;
    }

    private boolean checkNewBlocks(Block[][] board) {
        for (int i = 0; i < newBlocks.length; i++) {
            if (!newBlocks[i].check(board)) return false;
        }
        return true;
    }

    public void drawShadow(Graphics g, int blockSize, Point fromPoint, Block[][] board) {
        g.setColor(Color.GRAY);
        for (int i = 0; i < blocks.length; i++) {
            int newY = blocks[i].getPoint().y;
            newY++;
            while (newY < board[0].length) {
                if (
                        board[blocks[i].getPoint().x][newY] == null &&
                        isFreePosition(blocks[i].getPoint().x, newY)
                ) {
                    int x = blocks[i].getPoint().x * blockSize + fromPoint.x * blockSize + 1;
                    int y = newY * blockSize + fromPoint.y * blockSize + 1;
                    g.fillRect(x, y, blockSize - 2, blockSize - 2);
                }
                newY++;
            }
        }
    }

    private boolean isFreePosition(int x, int y) {
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].getPoint().y == y && blocks[i].getPoint().x == x) return false;
        }
        return true;
    }

    public boolean check(Block[][] board) {
        for (int i = 0; i < blocks.length; i++) {
            if (board[blocks[i].getPoint().x][blocks[i].getPoint().y] != null) return false;
        }

        return true;
    }
}
