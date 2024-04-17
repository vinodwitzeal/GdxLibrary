package com.badlogic.gdx.graphics.g2d.skia;

import java.io.*;
import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class OutputWStream extends WStream {
    static {
        Library.staticLoad();
    }


    public final OutputStream _out;


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public OutputWStream(OutputStream out) {
        super(_nMake(out), _FinalizerHolder.PTR);
        Stats.onNativeCall();
        _out = out;
    }

    public static native long _nGetFinalizer();

    public static native long _nMake(OutputStream out);
}
