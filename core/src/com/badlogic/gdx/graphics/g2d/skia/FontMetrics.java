package com.badlogic.gdx.graphics.g2d.skia;


public class FontMetrics {
    /**
     * greatest extent above origin of any glyph bounding box, typically negative; deprecated with variable fonts
     */
    public final float _top;

    /**
     * distance to reserve above baseline, typically negative
     */
    public final float _ascent;

    /**
     * distance to reserve below baseline, typically positive
     */
    public final float _descent;

    /**
     * greatest extent below origin of any glyph bounding box, typically positive; deprecated with variable fonts
     */
    public final float _bottom;

    /**
     * distance to add between lines, typically positive or zero
     */
    public final float _leading;

    /**
     * average character width, zero if unknown
     */
    public final float _avgCharWidth;

    /**
     * maximum character width, zero if unknown
     */
    public final float _maxCharWidth;

    /**
     * greatest extent to left of origin of any glyph bounding box, typically negative; deprecated with variable fonts
     */
    public final float _xMin;

    /**
     * greatest extent to right of origin of any glyph bounding box, typically positive; deprecated with variable fonts
     */
    public final float _xMax;

    /**
     * height of lower-case 'x', zero if unknown, typically negative
     */
    public final float _xHeight;

    /**
     * height of an upper-case letter, zero if unknown, typically negative
     */
    public final float _capHeight;

    /**
     * underline thickness
     */
    
    public final Float _underlineThickness;

    /**
     * distance from baseline to top of stroke, typically positive
     */
    
    public final Float _underlinePosition;

    /**
     * strikeout thickness
     */
    
    public final Float _strikeoutThickness;

    /**
     * distance from baseline to bottom of stroke, typically negative
     */
    
    public final Float _strikeoutPosition;

    public FontMetrics(float _top, float _ascent, float _descent, float _bottom, float _leading, float _avgCharWidth, float _maxCharWidth, float _xMin, float _xMax, float _xHeight, float _capHeight, Float _underlineThickness, Float _underlinePosition, Float _strikeoutThickness, Float _strikeoutPosition) {
        this._top = _top;
        this._ascent = _ascent;
        this._descent = _descent;
        this._bottom = _bottom;
        this._leading = _leading;
        this._avgCharWidth = _avgCharWidth;
        this._maxCharWidth = _maxCharWidth;
        this._xMin = _xMin;
        this._xMax = _xMax;
        this._xHeight = _xHeight;
        this._capHeight = _capHeight;
        this._underlineThickness = _underlineThickness;
        this._underlinePosition = _underlinePosition;
        this._strikeoutThickness = _strikeoutThickness;
        this._strikeoutPosition = _strikeoutPosition;
    }

    public float getHeight() {
        return _descent - _ascent;
    }
}