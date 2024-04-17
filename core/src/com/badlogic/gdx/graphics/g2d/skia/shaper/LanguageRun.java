package com.badlogic.gdx.graphics.g2d.skia.shaper;


public class LanguageRun {
    public final int end;

    /**
     * Should be BCP-47, c locale names may also work.
     */
    public final String language;

    public LanguageRun(int end, String language) {
        this.end = end;
        this.language = language;
    }
}
