package com.badlogic.gdx.graphics.g2d.skia;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;


import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class ManagedString extends Managed {
    static {
        Library.staticLoad();
    }


    public ManagedString(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }

    public ManagedString(String s) {
        this(_nMake(s));
        Stats.onNativeCall();
    }

    @Override
    public String toString() {
        try {
            Stats.onNativeCall();
            return _nToString(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public ManagedString insert(int offset,  String s) {
        Stats.onNativeCall();
        _nInsert(_ptr, offset, s);
        return this;
    }

    
    
    public ManagedString append( String s) {
        Stats.onNativeCall();
        _nAppend(_ptr, s);
        return this;
    }

    
    
    public ManagedString remove(int from) {
        Stats.onNativeCall();
        _nRemoveSuffix(_ptr, from);
        return this;
    }

    
    
    public ManagedString remove(int from, int length) {
        Stats.onNativeCall();
        _nRemove(_ptr, from, length);
        return this;
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nMake(String s);

    public static native long _nGetFinalizer();

    public static native String _nToString(long ptr);

    public static native void _nInsert(long ptr, int offset, String s);

    public static native void _nAppend(long ptr, String s);

    public static native void _nRemoveSuffix(long ptr, int from);

    public static native void _nRemove(long ptr, int from, int length);
}