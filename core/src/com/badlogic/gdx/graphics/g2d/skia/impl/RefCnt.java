package com.badlogic.gdx.graphics.g2d.skia.impl;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;


public abstract class RefCnt extends Managed {
    protected RefCnt(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    protected RefCnt(long ptr, boolean allowClose) {
        super(ptr, _FinalizerHolder.PTR, allowClose);
    }

    public int getRefCount() {
        try {
            Stats.onNativeCall();
            return _nGetRefCount(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, s.length() - 1) + ", refCount=" + getRefCount() + ")";
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native int _nGetRefCount(long ptr);
}