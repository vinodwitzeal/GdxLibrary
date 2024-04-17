package com.badlogic.gdx.graphics.g2d.skia.svg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class SVGSVG extends SVGContainer {
    static {
        Library.staticLoad();
    }


    public SVGSVG(long ptr) {
        super(ptr);
    }

    
    public SVGLength getX() {
        try {
            Stats.onNativeCall();
            return _nGetX(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public SVGLength getY() {
        try {
            Stats.onNativeCall();
            return _nGetY(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public SVGLength getWidth() {
        try {
            Stats.onNativeCall();
            return _nGetWidth(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public SVGLength getHeight() {
        try {
            Stats.onNativeCall();
            return _nGetHeight(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public SVGPreserveAspectRatio getPreserveAspectRatio() {
        try {
            Stats.onNativeCall();
            return _nGetPreserveAspectRatio(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public Rect getViewBox() {
        try {
            Stats.onNativeCall();
            return _nGetViewBox(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public Point getIntrinsicSize( SVGLengthContext lc) {
        try {
            Stats.onNativeCall();
            return _nGetIntrinsicSize(_ptr, lc._width, lc._height, lc._dpi);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setX( SVGLength length) {
        try {
            Stats.onNativeCall();
            _nSetX(_ptr, length._value, length._unit.ordinal());
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setY( SVGLength length) {
        try {
            Stats.onNativeCall();
            _nSetY(_ptr, length._value, length._unit.ordinal());
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setWidth( SVGLength length) {
        try {
            Stats.onNativeCall();
            _nSetWidth(_ptr, length._value, length._unit.ordinal());
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setHeight( SVGLength length) {
        try {
            Stats.onNativeCall();
            _nSetHeight(_ptr, length._value, length._unit.ordinal());
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setPreserveAspectRatio( SVGPreserveAspectRatio ratio) {
        try {
            Stats.onNativeCall();
            _nSetPreserveAspectRatio(_ptr, ratio._align._value, ratio._scale.ordinal());
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGSVG setViewBox( Rect viewBox) {
        try {
            Stats.onNativeCall();
            _nSetViewBox(_ptr, viewBox._left, viewBox._top, viewBox._right, viewBox._bottom);
            return this;
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native SVGLength _nGetX(long ptr);

    public static native SVGLength _nGetY(long ptr);

    public static native SVGLength _nGetWidth(long ptr);

    public static native SVGLength _nGetHeight(long ptr);

    public static native SVGPreserveAspectRatio _nGetPreserveAspectRatio(long ptr);

    public static native Rect _nGetViewBox(long ptr);

    public static native Point _nGetIntrinsicSize(long ptr, float width, float height, float dpi);

    public static native void _nSetX(long ptr, float value, int unit);

    public static native void _nSetY(long ptr, float value, int unit);

    public static native void _nSetWidth(long ptr, float value, int unit);

    public static native void _nSetHeight(long ptr, float value, int unit);

    public static native void _nSetPreserveAspectRatio(long ptr, int align, int scale);

    public static native void _nSetViewBox(long ptr, float l, float t, float r, float b);
}