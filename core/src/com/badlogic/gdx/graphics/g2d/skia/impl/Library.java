package com.badlogic.gdx.graphics.g2d.skia.impl;

public class Library {
    public static volatile boolean _loaded = false;

    public static void staticLoad() {
        if (!_loaded)
            load();
    }

    public static synchronized void load() {
        if (_loaded) return;

//        System.loadLibrary("SkiaSharp");
        System.loadLibrary("gdx-skia");
        _loaded = true;
        _nAfterLoad();
    }

    public static native void _nAfterLoad();
}
