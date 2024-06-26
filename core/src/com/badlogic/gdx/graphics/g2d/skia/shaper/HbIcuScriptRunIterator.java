package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class HbIcuScriptRunIterator extends ManagedRunIterator<ScriptRun> {
    static {
        Library.staticLoad();
    }

    public HbIcuScriptRunIterator(ManagedString text, boolean manageText) {
        super(_nMake(Native.getPtr(text)), text, manageText);
        Stats.onNativeCall();
        Reference.reachabilityFence(text);
    }

    public HbIcuScriptRunIterator(String text) {
        this(new ManagedString(text), true);
    }

    @Override
    public ScriptRun next() {
        try {
            _nConsume(_ptr);
            return new ScriptRun(_getEndOfCurrentRun(), _nGetCurrentScriptTag(_ptr));
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native long _nMake(long textPtr);

    public static native int _nGetCurrentScriptTag(long ptr);
}