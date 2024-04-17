package com.badlogic.gdx.graphics.g2d.skia;


/**
 * Level of glyph outline adjustment
 */
public enum FontHinting {
    /**
     * glyph outlines unchanged
     */
    NONE,

    /**
     * minimal modification to improve constrast
     */
    SLIGHT,

    /**
     * glyph outlines modified to improve constrast
     */
    NORMAL,

    /**
     * modifies glyph outlines for maximum constrast
     */
    FULL;

    public static final FontHinting[] _values = values();
}