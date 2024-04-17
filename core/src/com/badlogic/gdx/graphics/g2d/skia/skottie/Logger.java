package com.badlogic.gdx.graphics.g2d.skia.skottie;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

/**
 * <p>A Logger subclass can be used to receive
 * {@link AnimationBuilder} parsing errors and warnings.</p>
 */
public abstract class Logger extends RefCnt {
    static {
        Library.staticLoad();
    }

    public Logger() {
        super(_nMake());
        Stats.onNativeCall();
        Stats.onNativeCall();
        _nInit(_ptr);
    }

    public abstract void log(LogLevel level, String message,  String json);

    public static native long _nMake();

    public native void _nInit(long ptr);
}