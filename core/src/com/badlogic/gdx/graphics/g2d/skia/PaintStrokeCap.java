package com.badlogic.gdx.graphics.g2d.skia;


/**
 * Cap draws at the beginning and end of an open path contour.
 */
public enum PaintStrokeCap {
    /**
     * No stroke extension
     */
    BUTT,

    /**
     * adds circle
     */
    ROUND,

    /**
     * adds square
     */
    SQUARE;

    public static final PaintStrokeCap[] _values = values();
}
