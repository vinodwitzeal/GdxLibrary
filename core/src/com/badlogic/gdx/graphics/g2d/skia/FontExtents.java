package com.badlogic.gdx.graphics.g2d.skia;


public class FontExtents {
    public final float _ascender;
    public final float _descender;
    public final float _lineGap;

    public FontExtents(float _ascender, float _descender, float _lineGap) {
        this._ascender = _ascender;
        this._descender = _descender;
        this._lineGap = _lineGap;
    }

    public float getAscenderAbs() {
        return Math.abs(_ascender);
    }

    public float getLineHeight() {
        return -_ascender + _descender + _lineGap;
    }
}
