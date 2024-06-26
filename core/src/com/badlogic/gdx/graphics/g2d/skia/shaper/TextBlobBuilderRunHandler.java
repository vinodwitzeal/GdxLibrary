package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class TextBlobBuilderRunHandler<T> extends Managed implements RunHandler {
    static {
        Library.staticLoad();
    }


    public final ManagedString _text;


    public TextBlobBuilderRunHandler(ManagedString text, boolean manageText, float offsetX, float offsetY) {
        super(_nMake(Native.getPtr(text), offsetX, offsetY), _FinalizerHolder.PTR);
        _text = manageText ? text : null;
        Reference.reachabilityFence(text);
    }

    public TextBlobBuilderRunHandler(String text) {
        this(new ManagedString(text), true, 0, 0);
    }

    public TextBlobBuilderRunHandler(String text, Point offset) {
        this(new ManagedString(text), true, offset._x, offset._y);
    }

    @Override
    public void close() {
        super.close();
        if (_text != null)
            _text.close();
    }

    @Override
    public void beginLine() {
        throw new UnsupportedOperationException("beginLine");
    }

    @Override
    public void runInfo(RunInfo info) {
        throw new UnsupportedOperationException("runInfo");
    }

    @Override
    public void commitRunInfo() {
        throw new UnsupportedOperationException("commitRunInfo");
    }

    @Override
    public Point runOffset(RunInfo info) {
        throw new UnsupportedOperationException("runOffset");
    }

    @Override
    public void commitRun(RunInfo info, short[] glyphs, Point[] positions, int[] clusters) {
        throw new UnsupportedOperationException("commitRun");
    }

    @Override
    public void commitLine() {
        throw new UnsupportedOperationException("commitLine");
    }

    public TextBlob makeBlob() {
        try {
            Stats.onNativeCall();
            long ptr = _nMakeBlob(_ptr);
            return 0 == ptr ? null : new TextBlob(ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native long _nMake(long textPtr, float offsetX, float offsetY);

    public static native long _nMakeBlob(long ptr);
}