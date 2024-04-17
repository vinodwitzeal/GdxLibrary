package com.badlogic.gdx.graphics.g2d.skia.shaper;


public class BidiRun {
    public final int end;

    /**
     * The unicode bidi embedding level (even ltr, odd rtl)
     */
    public final int level;

    public BidiRun(int end, int level) {
        this.end = end;
        this.level = level;
    }
}
