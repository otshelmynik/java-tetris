package tetris;

import gengine.GameEngine;
import tetris.sound.SoundManager;
import tetris.sound.Sound;

import java.awt.*;
import java.util.ArrayList;

public class Game extends GameEngine {
    public static final String TITLE = "Тетрис";
    public static final int BLOCK_SIZE = 26;
    public static final int BOARD_WIDTH_BLOCKS = 10;
    public static final int BOARD_HEIGHT_BLOCKS = 20;
    public static final int PADDING_TOP = 3;
    public static final int PADDING_SIDE = 6;
    public static final int PADDING_BOTTOM = 1;

    private int width = BLOCK_SIZE * BOARD_WIDTH_BLOCKS + BLOCK_SIZE * PADDING_SIDE * 2;
    private int height = BLOCK_SIZE * BOARD_HEIGHT_BLOCKS + BLOCK_SIZE * PADDING_TOP + BLOCK_SIZE * PADDING_BOTTOM;
    private Figure figure;
    private Figure nextFigure;
    private Point startPointFigure;
    private Point startPointNextFigure;

    private Block[][] blocks;
    private float speed = 10;
    private float timer;
    private KeyManager keyManager;
    private SoundManager sound;

    private int score;
    private int level = 1;


    private boolean drop = false;
    private float dropSpeed = 1;
    private float dropTimer;

    public Game() {
        createWindow(TITLE, width, height);
        sound = new SoundManager();
        keyManager = new KeyManager();
        getWindow().getFrame().addKeyListener(keyManager);

        figure = new Figure();
        figure.moveToBoard();
        nextFigure = new Figure();

        startPointNextFigure = new Point(PADDING_SIDE + BOARD_WIDTH_BLOCKS + 2, PADDING_TOP);
        startPointFigure = new Point(PADDING_SIDE, PADDING_TOP);

        blocks = new Block[BOARD_WIDTH_BLOCKS][BOARD_HEIGHT_BLOCKS];
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        drawLockedBlocks(g);
        figure.draw(g, BLOCK_SIZE, startPointFigure);
        figure.drawShadow(g, BLOCK_SIZE, startPointFigure, blocks);
        nextFigure.draw(g, BLOCK_SIZE, startPointNextFigure);
        drawLevelInfo(g);
    }

    @Override
    public void tick(float dt) {
        if (drop) {
            updateDrop(dt);
            return;
        }
        keyManager.tick();
        updateFall(dt);
        updateMove(dt);
    }

    private int getLevelMaxScore() {
        return level * 3000;
    }

    private void drawLevelInfo(Graphics g) {
        g.setColor(Color.WHITE);
        int x = (PADDING_SIDE / 2 - 1) * BLOCK_SIZE;
        int y = PADDING_TOP * BLOCK_SIZE;
        g.drawString("Уровень: " + level, x, y);
        g.drawString("Очков: " + score, x, y + BLOCK_SIZE);

    }

    private void drawLockedBlocks(Graphics g) {
        for (int x = 0; x < blocks.length; x++) {
            for (int y = 0; y < blocks[x].length; y++) {
                if (blocks[x][y] != null) {
                    blocks[x][y].draw(g, BLOCK_SIZE, startPointFigure);
                }
            }
        }
    }

    private void updateDrop(float  dt) {
        dropTimer += dt;
        if (dropTimer >= dropSpeed) {
            if (figure.checkFall(blocks)) {
                figure.fall();
                dropTimer -= dropSpeed;
            } else {
                fixFigure();
                drop = false;
            }
        }
    }

    private void updateMove(float dt) {
        if (keyManager.left) {
            if (figure.checkLeft(blocks)) {
                figure.moveLeft();
                keyManager.pressedLeft();
            }
        }
        if (keyManager.right) {
            if (figure.checkRight(blocks)) {
                figure.moveRight();
                keyManager.pressedRight();
            }
        }
        if (keyManager.up) {
            if (figure.checkRotate(blocks)) {
                figure.rotate();
                keyManager.pressedUp();
            }
        }
        if (keyManager.down) {
            if (figure.checkFall(blocks)) {
                figure.fall();
                keyManager.pressedDown();
            }
        }
        if (keyManager.space) {
            drop = true;
            keyManager.pressedSpace();
        }
    }

    private void fixFigure() {
        figure.lockBlocks(blocks);
        figure = new Figure(nextFigure.getFigureType());
        figure.moveToBoard();
        nextFigure = new Figure();
        sound.play(Sound.BANG);
        checkWin();
        checkLose();
    }

    private void checkLose() {
        if (!figure.check(blocks)) {
            sound.play(Sound.LOSE);
            level = 1;
            score = 0;
            speed = 10;
            for (int x = 0; x < blocks.length; x++) {
                for (int y = 0; y < blocks[x].length; y++) {
                    blocks[x][y] = null;
                }
            }
        }
    }

    private void checkWin() {
        ArrayList<Integer> winLines = new ArrayList<>();
        int winBlocks;
        for (int y = blocks[0].length - 1; y >= 0; y--) {
            winBlocks = 0;
            for (int x = 0; x < blocks.length; x++) {
                if (blocks[x][y] != null) winBlocks++;
            }
            if (winBlocks == blocks.length) {
                for (int x = 0; x < blocks.length; x++) {
                    blocks[x][y] = null;
                }
                winLines.add(y);
            }
        }

        if (winLines.size() > 0) {
            sound.play(Sound.WIN_LINES);
            updateScore(winLines.size());

            for (int i = 0; i < winLines.size(); i++) {
                int winLine = winLines.get(i) + i;
                // удаляем строку
                for (int x = 0; x < blocks.length; x++) {
                    blocks[x][winLine] = null;
                }

                // опускаем блоки
                for (int y = winLine - 1; y >= 0; y--) {
                    for (int x = 0; x < blocks.length; x++) {
                        if (y < blocks[0].length - 1) {
                            if (blocks[x][y] != null && blocks[x][y + 1] == null) {
                                blocks[x][y + 1] = new Block(blocks[x][y].getColor(), new Point(x, y + 1));
                                blocks[x][y] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Начисление очков
     * 1 линия — 100 очков,
     * 2 линии — 300 очков,
     * 3 линии — 700 очков,
     * 4 линии (то есть сделать Тетрис) — 1500 очков
     */
    private void updateScore(int winLines) {
        switch (winLines) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 700;
                break;
            case 4:
                score += 1500;
                break;
            default:
                break;
        }

        int maxScore = getLevelMaxScore();
        int diffScore = score - maxScore;
        if (diffScore >= 0) {
            level++;
            sound.play(Sound.WIN);
            speed--;
            if (speed <= 0) speed = 0.5f;
            score = diffScore;
        }
    }

    private void updateFall(float dt) {
        timer += dt;
        if (timer > speed) {
            if (figure.checkFall(blocks)) {
                figure.fall();
            } else {
                fixFigure();
            }

            timer = -speed;
        }
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);

        for (int y = PADDING_TOP; y <= (BOARD_HEIGHT_BLOCKS + 3); y++) {
            g.fillRect((PADDING_SIDE - 1) * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
            g.fillRect((PADDING_SIDE + BOARD_WIDTH_BLOCKS) * BLOCK_SIZE + 1, y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
        }

        for (int x = PADDING_SIDE; x < (PADDING_SIDE + BOARD_WIDTH_BLOCKS); x++) {
            g.fillRect(x * BLOCK_SIZE + 1, (PADDING_TOP + BOARD_HEIGHT_BLOCKS) * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
        }
    }
}
