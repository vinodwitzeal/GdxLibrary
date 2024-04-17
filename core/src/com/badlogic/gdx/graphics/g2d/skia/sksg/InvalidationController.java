package com.badlogic.gdx.graphics.g2d.skia.sksg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

/**
 * <p>Receiver for invalidation events.</p>
 * <p>Tracks dirty regions for repaint.</p>
 */
public class InvalidationController extends Managed {
    static {
        Library.staticLoad();
    }


    public InvalidationController(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public InvalidationController() {
        this(_nMake());
    }

    
    
    public InvalidationController invalidate(float left, float top, float right, float bottom,  Matrix33 matrix) {
        Stats.onNativeCall();
        _nInvalidate(_ptr, left, top, right, bottom, matrix == null ? Matrix33.IDENTITY._mat : matrix._mat);
        return this;
    }

    
    public Rect getBounds() {
        try {
            Stats.onNativeCall();
            return _nGetBounds(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public InvalidationController reset() {
        Stats.onNativeCall();
        _nReset(_ptr);
        return this;
    }

    public static native long _nGetFinalizer();

    public static native long _nMake();

    public static native void _nInvalidate(long ptr, float left, float top, float right, float bottom, float[] matrix);

    public static native Rect _nGetBounds(long ptr);

    public static native void _nReset(long ptr);
}