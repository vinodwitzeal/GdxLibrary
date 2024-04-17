package com.badlogic.gdx.graphics.g2d.skia;


public enum FilterBlurMode {
    /**
     * fuzzy inside and outside
     */
    NORMAL,
    /**
     * solid inside, fuzzy outside
     */
    SOLID,
    /**
     * nothing inside, fuzzy outside
     */
    OUTER,
    /**
     * fuzzy inside, nothing outside
     */
    INNER;

    public static final FilterBlurMode[] _values = values();
}
