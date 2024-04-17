package com.badlogic.gdx.graphics.g2d.skia;


/**
 * Whether edge pixels draw opaque or with partial transparency.
 */
public enum FontEdging {
    /**
     * no transparent pixels on glyph edges
     */
    ALIAS,

    /**
     * may have transparent pixels on glyph edges
     */
    ANTI_ALIAS,

    /**
     * glyph positioned in pixel using transparency
     */
    SUBPIXEL_ANTI_ALIAS;

    public static final FontEdging[] _values = values();
}