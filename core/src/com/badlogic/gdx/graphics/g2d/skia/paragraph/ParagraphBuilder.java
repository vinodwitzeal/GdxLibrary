package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class ParagraphBuilder extends Managed {
    static {
        Library.staticLoad();
    }


    public ManagedString _text;

    public ParagraphBuilder(ParagraphStyle style, FontCollection fc) {
        super(_nMake(Native.getPtr(style), Native.getPtr(fc)), _FinalizerHolder.PTR);
        Stats.onNativeCall();
        Reference.reachabilityFence(style);
        Reference.reachabilityFence(fc);
    }

    public ParagraphBuilder pushStyle(TextStyle style) {
        try {
            Stats.onNativeCall();
            _nPushStyle(_ptr, Native.getPtr(style));
            return this;
        } finally {
            Reference.reachabilityFence(style);
        }
    }

    public ParagraphBuilder popStyle() {
        Stats.onNativeCall();
        _nPopStyle(_ptr);
        return this;
    }

    public ParagraphBuilder addText(String text) {
        Stats.onNativeCall();
        _nAddText(_ptr, text);
        if (_text == null)
            _text = new ManagedString(text);
        else
            _text.append(text);
        return this;
    }

    public ParagraphBuilder addPlaceholder(PlaceholderStyle style) {
        Stats.onNativeCall();
        _nAddPlaceholder(_ptr, style.getWidth(), style.getHeight(), style.getAlignment().ordinal(), style.getBaselineMode().ordinal(), style.getBaseline());
        return this;
    }

    public ParagraphBuilder setParagraphStyle(ParagraphStyle style) {
        try {
            Stats.onNativeCall();
            _nSetParagraphStyle(_ptr, Native.getPtr(style));
            return this;
        } finally {
            Reference.reachabilityFence(style);
        }
    }

    public Paragraph build() {
        try {
            Stats.onNativeCall();
            Paragraph paragraph = new Paragraph(_nBuild(_ptr), _text);
            _text = null;
            return paragraph;
        } finally {
            Reference.reachabilityFence(this);
        }
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nMake(long paragraphStylePtr, long fontCollectionPtr);

    public static native long _nGetFinalizer();

    public static native void _nPushStyle(long ptr, long textStylePtr);

    public static native void _nPopStyle(long ptr);

    public static native void _nAddText(long ptr, String text);

    public static native void _nAddPlaceholder(long ptr, float width, float height, int alignment, int baselineMode, float baseline);

    public static native void _nSetParagraphStyle(long ptr, long stylePtr);

    public static native long _nBuild(long ptr);
}