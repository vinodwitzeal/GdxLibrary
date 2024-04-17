package com.badlogic.gdx.graphics.g2d.skia;


public enum FilterQuality {
    /**
     * fastest but lowest quality, typically nearest-neighbor
     */
    NONE,
    /**
     * typically bilerp
     */
    LOW,
    /**
     * typically bilerp + mipmaps for down-scaling
     */
    MEDIUM,
    /**
     * slowest but highest quality, typically bicubic or bett
     */
    HIGH;

    public static final FilterQuality[] _values = values();
}
