package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class FontMgrRunIterator extends ManagedRunIterator<FontRun> {
    static {
        Library.staticLoad();
    }

    public FontMgrRunIterator(ManagedString text, boolean manageText, Font font, ShapingOptions opts) {
        super(_nMake(Native.getPtr(text), Native.getPtr(font), opts), text, manageText);
        Stats.onNativeCall();
        Reference.reachabilityFence(text);
        Reference.reachabilityFence(font);
        Reference.reachabilityFence(opts);
    }

    public FontMgrRunIterator(String text, Font font, ShapingOptions opts) {
        this(new ManagedString(text), true, font, opts);
    }

    public FontMgrRunIterator(String text, Font font) {
        this(new ManagedString(text), true, font, ShapingOptions.DEFAULT);
    }

    @Override
    public FontRun next() {
        try {
            _nConsume(_ptr);
            return new FontRun(_getEndOfCurrentRun(), new Font(_nGetCurrentFont(_ptr)));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native long _nMake(long textPtr, long fontPtr, ShapingOptions opts);

    public static native long _nGetCurrentFont(long ptr);
}