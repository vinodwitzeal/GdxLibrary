package com.badlogic.gdx.graphics.g2d.skia.svg;


import com.badlogic.gdx.graphics.g2d.skia.*;

public class SVGLengthContext {
    public final float _width;
    public final float _height;
    public final float _dpi;

    public SVGLengthContext(float _width, float _height, float _dpi) {
        this._width = _width;
        this._height = _height;
        this._dpi = _dpi;
    }

    public SVGLengthContext(float width, float height) {
        this(width, height, 90);
    }

    public SVGLengthContext( Point size) {
        this(size._x, size._y, 90);
    }

    public float resolve( SVGLength length,  SVGLengthType type) {
        switch (length._unit) {
            case NUMBER:
                return length._value;
            case PX:
                return length._value;
            case PERCENTAGE:
                switch (type) {
                    case HORIZONTAL:
                        return length._value * _width / 100f;
                    case VERTICAL:
                        return length._value * _height / 100f;
                    case OTHER:
                        // https://www.w3.org/TR/SVG11/coords.html#Units_viewport_percentage
                        return (float) (length._value * Math.hypot(_width, _height) / Math.sqrt(2.0) / 100.0);
                }
            case CM:
                return length._value * _dpi / 2.54f;
            case MM:
                return length._value * _dpi / 25.4f;
            case IN:
                return length._value * _dpi;
            case PT:
                return length._value * _dpi / 72.272f;
            case PC:
                return length._value * _dpi * 12f / 72.272f;
            default:
                throw new IllegalArgumentException("Unknown SVGLengthUnit: " + length._unit);
        }
    }

    
    public Rect resolveRect( SVGLength x,  SVGLength y,  SVGLength width,  SVGLength height) {
        return Rect.makeXYWH(resolve(x, SVGLengthType.HORIZONTAL),
                resolve(y, SVGLengthType.VERTICAL),
                resolve(width, SVGLengthType.HORIZONTAL),
                resolve(height, SVGLengthType.VERTICAL));
    }
}