package com.badlogic.gdx.graphics.g2d.skia;


public enum PathDirection {
    /**
     * Clockwise direction for adding closed contours.
     */
    CLOCKWISE,

    /**
     * Counter-clockwise direction for adding closed contours.
     */
    COUNTER_CLOCKWISE;

    public static final PathDirection[] _values = values();
}
