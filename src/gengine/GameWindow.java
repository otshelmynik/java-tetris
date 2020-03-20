package gengine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameWindow {
    private JFrame frame;
    private Canvas canvas;
    private String title;
    private int width, height;

    private BufferStrategy b;
    private Graphics g;

    public GameWindow(String title, int width, int height) throws HeadlessException {
        this.title = title;
        this.width = width;
        this.height = height;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public void renderBegin() {
        if (canvas.getBufferStrategy() == null) {
            canvas.createBufferStrategy(3);
        }
        b = canvas.getBufferStrategy();
        g = b.getDrawGraphics();
        g.clearRect(0, 0, width, height);
    }

    public void renderEnd() {
        b.show();
        g.dispose();
    }

    public Graphics getGraphics() {
        return g;
    }

    public JFrame getFrame() {
        return frame;
    }
}
