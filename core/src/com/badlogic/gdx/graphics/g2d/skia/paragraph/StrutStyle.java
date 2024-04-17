package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class StrutStyle extends Managed {
    static {
        Library.staticLoad();
    }


    public StrutStyle(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    public StrutStyle() {
        this(_nMake());
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

    public String[] getFontFamilies() {
        try {
            Stats.onNativeCall();
            return _nGetFontFamilies(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setFontFamilies(String[] families) {
        Stats.onNativeCall();
        _nSetFontFamilies(_ptr, families);
        return this;
    }

    public FontStyle getFontStyle() {
        try {
            Stats.onNativeCall();
            return new FontStyle(_nGetFontStyle(_ptr));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setFontStyle(FontStyle style) {
        Stats.onNativeCall();
        _nSetFontStyle(_ptr, style._value);
        return this;
    }

    public float getFontSize() {
        try {
            Stats.onNativeCall();
            return _nGetFontSize(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setFontSize(float value) {
        Stats.onNativeCall();
        _nSetFontSize(_ptr, value);
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

    public StrutStyle setHeight(float value) {
        Stats.onNativeCall();
        _nSetHeight(_ptr, value);
        return this;
    }

    public float getLeading() {
        try {
            Stats.onNativeCall();
            return _nGetLeading(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setLeading(float value) {
        Stats.onNativeCall();
        _nSetLeading(_ptr, value);
        return this;
    }

    public boolean isEnabled() {
        try {
            Stats.onNativeCall();
            return _nIsEnabled(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setEnabled(boolean value) {
        Stats.onNativeCall();
        _nSetEnabled(_ptr, value);
        return this;
    }

    public boolean isHeightForced() {
        try {
            Stats.onNativeCall();
            return _nIsHeightForced(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setHeightForced(boolean value) {
        Stats.onNativeCall();
        _nSetHeightForced(_ptr, value);
        return this;
    }

    public boolean isHeightOverridden() {
        try {
            Stats.onNativeCall();
            return _nIsHeightOverridden(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public StrutStyle setHeightOverridden(boolean value) {
        Stats.onNativeCall();
        _nSetHeightOverridden(_ptr, value);
        return this;
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native long _nMake();

    public static native boolean _nEquals(long ptr, long otherPtr);

    public static native String[] _nGetFontFamilies(long ptr);

    public static native void _nSetFontFamilies(long ptr, String[] families);

    public static native int _nGetFontStyle(long ptr);

    public static native void _nSetFontStyle(long ptr, int value);

    public static native float _nGetFontSize(long ptr);

    public static native void _nSetFontSize(long ptr, float value);

    public static native float _nGetHeight(long ptr);

    public static native void _nSetHeight(long ptr, float value);

    public static native float _nGetLeading(long ptr);

    public static native void _nSetLeading(long ptr, float value);

    public static native boolean _nIsEnabled(long ptr);

    public static native void _nSetEnabled(long ptr, boolean value);

    public static native boolean _nIsHeightForced(long ptr);

    public static native void _nSetHeightForced(long ptr, boolean value);

    public static native boolean _nIsHeightOverridden(long ptr);

    public static native void _nSetHeightOverridden(long ptr, boolean value);
}