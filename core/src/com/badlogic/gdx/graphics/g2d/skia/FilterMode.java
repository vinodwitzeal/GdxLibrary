package com.badlogic.gdx.graphics.g2d.skia;


public enum FilterMode {
    /**
     * single sample point (nearest neighbor)
     */
    NEAREST,

    /**
     * interporate between 2x2 sample points (bilinear interpolation)
     */
    LINEAR;

    public static final FilterMode[] _values = values();
}
