package com.badlogic.gdx.graphics.g2d.skia;

public class FontVariationAxis {

    public final int _tag;
    public final float _minValue;
    public final float _defaultValue;
    public final float _maxValue;
    public final boolean _hidden;

    public FontVariationAxis(int _tag, float _minValue, float _defaultValue, float _maxValue, boolean _hidden) {
        this._tag = _tag;
        this._minValue = _minValue;
        this._defaultValue = _defaultValue;
        this._maxValue = _maxValue;
        this._hidden = _hidden;
    }

    public String getTag() {
        return FourByteTag.toString(_tag);
    }

    public FontVariationAxis(String tag, float min, float def, float max, boolean hidden) {
        this(FourByteTag.fromString(tag), min, def, max, hidden);
    }

    public FontVariationAxis(String tag, float min, float def, float max) {
        this(FourByteTag.fromString(tag), min, def, max, false);
    }
}