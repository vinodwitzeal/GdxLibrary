package com.badlogic.gdx.graphics.g2d.skia;


public enum ContentChangeMode {
    /**
     * Discards surface on change.
     */
    DISCARD,

    /**
     * Preserves surface on change.
     */
    RETAIN;

    public static final ContentChangeMode[] _values = values();
}