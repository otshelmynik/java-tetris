package tetris.sound;

import java.net.URL;

public enum Sound {
    BANG("bang.wav"),
    WIN("win.wav"),
    LOSE("lose.wav"),
    WIN_LINES("win-lines.wav");

    public URL fileName;

    Sound(String fileName) {
        this.fileName = getClass().getClassLoader().getResource(fileName);
        //this.fileName = fileName;
    }
}
