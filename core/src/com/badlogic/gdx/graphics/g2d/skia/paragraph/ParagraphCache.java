package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class ParagraphCache extends Native {
    static {
        Library.staticLoad();
    }

    public void abandon() {
        try {
            _validate();
            Stats.onNativeCall();
            _nAbandon(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void reset() {
        try {
            _validate();
            Stats.onNativeCall();
            _nReset(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public boolean updateParagraph(Paragraph paragraph) {
        try {
            _validate();
            Stats.onNativeCall();
            return _nUpdateParagraph(_ptr, Native.getPtr(paragraph));
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(paragraph);
        }
    }

    public boolean findParagraph(Paragraph paragraph) {
        try {
            _validate();
            Stats.onNativeCall();
            return _nFindParagraph(_ptr, Native.getPtr(paragraph));
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(paragraph);
        }
    }

    public void printStatistics() {
        try {
            _validate();
            Stats.onNativeCall();
            _nPrintStatistics(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public void setEnabled(boolean value) {
        try {
            _validate();
            Stats.onNativeCall();
            _nSetEnabled(_ptr, value);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public int getCount() {
        try {
            _validate();
            Stats.onNativeCall();
            return _nGetCount(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }


    public ParagraphCache(FontCollection owner, long ptr) {
        super(ptr);
        this._owner = owner;
    }


    public final FontCollection _owner;


    public void _validate() {
        try {
            if (Native.getPtr(_owner) == 0)
                throw new IllegalStateException("ParagraphCache needs owning FontCollection to be alive");
        } finally {
            Reference.reachabilityFence(_owner);
        }
    }

    public static native void _nAbandon(long ptr);

    public static native void _nReset(long ptr);

    public static native boolean _nUpdateParagraph(long ptr, long paragraphPtr);

    public static native boolean _nFindParagraph(long ptr, long paragraphPtr);

    public static native void _nPrintStatistics(long ptr);

    public static native void _nSetEnabled(long ptr, boolean value);

    public static native int _nGetCount(long ptr);
}