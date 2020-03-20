package gengine;

import java.awt.*;

public abstract class GameEngine extends GameLoop {
    private GameWindow window;
    private String title;
    private int width, height;
    private boolean showFps = false;

    public void createWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        window = new GameWindow(title, width, height);
    }

    public GameWindow getWindow() {
        return window;
    }

    public void setShowFps(boolean showFps) {
        this.showFps = showFps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void tick(float dt);
    public abstract void draw(Graphics g);

    @Override
    void update(float dt) {
        if (showFps) window.setTitle(title + " (fps: "+(int)getRealFps()+")");
        tick(dt);
    }

    @Override
    void render() {
        window.renderBegin();
        draw(window.getGraphics());
        window.renderEnd();
    }
}
