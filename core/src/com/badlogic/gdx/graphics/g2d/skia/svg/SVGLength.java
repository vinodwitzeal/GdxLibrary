package com.badlogic.gdx.graphics.g2d.skia.svg;


public class SVGLength {
    public final float _value;
    public final SVGLengthUnit _unit;

    public SVGLength(float _value, SVGLengthUnit _unit) {
        this._value = _value;
        this._unit = _unit;
    }

    public SVGLength(float value, int unit) {
        this(value, SVGLengthUnit._values[unit]);
    }

    public SVGLength(float value) {
        this(value, SVGLengthUnit.NUMBER);
    }
}