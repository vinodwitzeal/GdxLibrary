package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;
import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public abstract class ManagedRunIterator<T> extends Managed implements Iterator<T> {
    static {
        Library.staticLoad();
    }


    public final ManagedString _text;


    public ManagedRunIterator(long ptr, ManagedString text, boolean manageText) {
        super(ptr, _FinalizerHolder.PTR);
        _text = manageText ? text : null;
    }

    @Override
    public void close() {
        super.close();
        if (_text != null)
            _text.close();
    }


    public int _getEndOfCurrentRun() {
        try {
            return _nGetEndOfCurrentRun(_ptr, Native.getPtr(_text));
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(_text);
        }
    }

    @Override
    public boolean hasNext() {
        try {
            return !_nIsAtEnd(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native void _nConsume(long ptr);

    public static native int _nGetEndOfCurrentRun(long ptr, long textPtr);

    public static native boolean _nIsAtEnd(long ptr);
}