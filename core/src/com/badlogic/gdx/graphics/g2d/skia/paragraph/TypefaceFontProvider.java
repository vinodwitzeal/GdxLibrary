package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class TypefaceFontProvider extends FontMgr {
    static {
        Library.staticLoad();
    }

    public TypefaceFontProvider() {
        super(_nMake());
        Stats.onNativeCall();
    }

    public TypefaceFontProvider registerTypeface(Typeface typeface) {
        return registerTypeface(typeface, null);
    }

    public TypefaceFontProvider registerTypeface(Typeface typeface, String alias) {
        try {
            Stats.onNativeCall();
            _nRegisterTypeface(_ptr, Native.getPtr(typeface), alias);
            return this;
        } finally {
            Reference.reachabilityFence(typeface);
        }
    }

    public static native long _nMake();

    public static native long _nRegisterTypeface(long ptr, long typefacePtr, String alias);
}