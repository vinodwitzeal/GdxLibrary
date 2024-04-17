package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class ParagraphStyle extends Managed {
    static {
        Library.staticLoad();
    }

    public ParagraphStyle() {
        super(_nMake(), _FinalizerHolder.PTR);
        Stats.onNativeCall();
    }

    @Override
    public boolean _nativeEquals(Native other) {
        try {
            Stats.onNativeCall();
            return _nEquals(_ptr, Native.getPtr(other));
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(other);
        }
    }

    public StrutStyle getStrutStyle() {
        try {
            Stats.onNativeCall();
            return new StrutStyle(_nGetStrutStyle(_ptr));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setStrutStyle(StrutStyle s) {
        try {
            Stats.onNativeCall();
            _nSetStrutStyle(_ptr, Native.getPtr(s));
            return this;
        } finally {
            Reference.reachabilityFence(s);
        }
    }

    public TextStyle getTextStyle() {
        try {
            Stats.onNativeCall();
            return new TextStyle(_nGetTextStyle(_ptr));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setTextStyle(TextStyle style) {
        try {
            Stats.onNativeCall();
            _nSetTextStyle(_ptr, Native.getPtr(style));
            return this;
        } finally {
            Reference.reachabilityFence(style);
        }
    }

    public Direction getDirection() {
        try {
            Stats.onNativeCall();
            return Direction._values[_nGetDirection(_ptr)];
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setDirection(Direction style) {
        Stats.onNativeCall();
        _nSetDirection(_ptr, style.ordinal());
        return this;
    }

    public Alignment getAlignment() {
        try {
            Stats.onNativeCall();
            return Alignment._values[_nGetAlignment(_ptr)];
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setAlignment(Alignment alignment) {
        Stats.onNativeCall();
        _nSetAlignment(_ptr, alignment.ordinal());
        return this;
    }

    public long getMaxLinesCount() {
        try {
            Stats.onNativeCall();
            return _nGetMaxLinesCount(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setMaxLinesCount(long count) {
        Stats.onNativeCall();
        _nSetMaxLinesCount(_ptr, count);
        return this;
    }

    public String getEllipsis() {
        try {
            Stats.onNativeCall();
            return _nGetEllipsis(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setEllipsis(String ellipsis) {
        Stats.onNativeCall();
        _nSetEllipsis(_ptr, ellipsis);
        return this;
    }

    public float getHeight() {
        try {
            Stats.onNativeCall();
            return _nGetHeight(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setHeight(float height) {
        Stats.onNativeCall();
        _nSetHeight(_ptr, height);
        return this;
    }

    public HeightMode getHeightMode() {
        try {
            Stats.onNativeCall();
            return HeightMode._values[_nGetHeightMode(_ptr)];
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle setHeightMode(HeightMode behavior) {
        Stats.onNativeCall();
        _nSetHeightMode(_ptr, behavior.ordinal());
        return this;
    }

    public Alignment getEffectiveAlignment() {
        try {
            Stats.onNativeCall();
            return Alignment._values[_nGetEffectiveAlignment(_ptr)];
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public boolean isHintingEnabled() {
        try {
            Stats.onNativeCall();
            return _nIsHintingEnabled(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public ParagraphStyle disableHinting() {
        Stats.onNativeCall();
        _nDisableHinting(_ptr);
        return this;
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native long _nMake();

    public static native boolean _nEquals(long ptr, long otherPtr);

    public static native long _nGetStrutStyle(long ptr);

    public static native void _nSetStrutStyle(long ptr, long stylePtr);

    public static native long _nGetTextStyle(long ptr);

    public static native void _nSetTextStyle(long ptr, long textStylePtr);

    public static native int _nGetDirection(long ptr);

    public static native void _nSetDirection(long ptr, int direction);

    public static native int _nGetAlignment(long ptr);

    public static native void _nSetAlignment(long ptr, int align);

    public static native long _nGetMaxLinesCount(long ptr);

    public static native void _nSetMaxLinesCount(long ptr, long maxLines);

    public static native String _nGetEllipsis(long ptr);

    public static native void _nSetEllipsis(long ptr, String ellipsis);

    public static native float _nGetHeight(long ptr);

    public static native void _nSetHeight(long ptr, float height);

    public static native int _nGetHeightMode(long ptr);

    public static native void _nSetHeightMode(long ptr, int v);

    public static native int _nGetEffectiveAlignment(long ptr);

    public static native boolean _nIsHintingEnabled(long ptr);

    public static native void _nDisableHinting(long ptr);
}