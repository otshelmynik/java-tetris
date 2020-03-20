package tetris;

import java.awt.event.KeyEvent;

public class KeyManager extends gengine.KeyManager {
    public boolean up, down, left, right, space;

    public void tick() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
    }

    public void pressedUp() {
        setKey(KeyEvent.VK_UP, false);
    }

    public void pressedDown() {
        setKey(KeyEvent.VK_DOWN, false);
    }

    public void pressedLeft() {
        setKey(KeyEvent.VK_LEFT, false);
    }

    public void pressedRight() {
        setKey(KeyEvent.VK_RIGHT, false);
    }

    public void pressedSpace() {
        setKey(KeyEvent.VK_SPACE, false);
    }
}
