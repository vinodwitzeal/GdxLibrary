package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;
import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class IcuBidiRunIterator extends ManagedRunIterator<BidiRun> {
    static {
        Library.staticLoad();
    }

    public IcuBidiRunIterator(ManagedString text, boolean manageText, int bidiLevel) {
        super(_nMake(Native.getPtr(text), bidiLevel), text, manageText);
        Stats.onNativeCall();
        Reference.reachabilityFence(text);
    }

    public IcuBidiRunIterator(String text, int bidiLevel) {
        this(new ManagedString(text), true, bidiLevel);
    }

    @Override
    public BidiRun next() {
        try {
            _nConsume(_ptr);
            return new BidiRun(_getEndOfCurrentRun(), _nGetCurrentLevel(_ptr));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native long _nMake(long textPtr, int bidiLevel);

    public static native int _nGetCurrentLevel(long ptr);
}