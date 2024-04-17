package com.badlogic.gdx.graphics.g2d.skia;


public enum MipmapMode {
    /**
     * ignore mipmap levels, sample from the "base"
     */
    NONE,

    /**
     * sample from the nearest level
     */
    NEAREST,

    /**
     * interpolate between the two nearest levels
     */
    LINEAR;

    public static final MipmapMode[] _values = values();
}
