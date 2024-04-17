package com.badlogic.gdx.graphics.g2d.skia.paragraph;


public class DecorationStyle {
    public static final DecorationStyle NONE = new DecorationStyle(false, false, false, true, 0xFF000000, DecorationLineStyle.SOLID, 1f);


    public final boolean _underline;

    public final boolean _overline;

    public final boolean _lineThrough;

    public final boolean _gaps;
    public final int _color;
    public final DecorationLineStyle _lineStyle;
    public final float _thicknessMultiplier;


    public DecorationStyle(boolean underline, boolean overline, boolean lineThrough, boolean gaps, int color, DecorationLineStyle lineStyle, float thicknessMultiplier) {
        this._underline = underline;
        this._overline = overline;
        this._lineThrough = lineThrough;
        this._gaps = gaps;
        this._color = color;
        this._lineStyle = lineStyle;
        this._thicknessMultiplier = thicknessMultiplier;
    }

    public DecorationStyle(boolean underline, boolean overline, boolean lineThrough, boolean gaps, int color, int lineStyle, float thicknessMultiplier) {
        this(underline, overline, lineThrough, gaps, color, DecorationLineStyle._values[lineStyle], thicknessMultiplier);
    }

    public boolean hasUnderline() {
        return _underline;
    }

    public boolean hasOverline() {
        return _overline;
    }

    public boolean hasLineThrough() {
        return _lineThrough;
    }

    public boolean hasGaps() {
        return _gaps;
    }
}