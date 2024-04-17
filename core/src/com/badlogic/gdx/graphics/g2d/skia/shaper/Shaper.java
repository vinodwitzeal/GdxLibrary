package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;
import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

/**
 * Shapes text using HarfBuzz and places the shaped text into a
 * client-managed buffer.
 */
public class Shaper extends Managed {
    static {
        Library.staticLoad();
    }
    
    public static Shaper makePrimitive() {
        Stats.onNativeCall();
        return new Shaper(_nMakePrimitive());
    }
    
    public static Shaper makeShaperDrivenWrapper() {
        return makeShaperDrivenWrapper(null);
    }

   
    public static Shaper makeShaperDrivenWrapper(FontMgr fontMgr) {
        try {
            Stats.onNativeCall();
            return new Shaper(_nMakeShaperDrivenWrapper(Native.getPtr(fontMgr)));
        } finally {
            Reference.reachabilityFence(fontMgr);
        }
    }
    
    public static Shaper makeShapeThenWrap() {
        return makeShapeThenWrap(null);
    }
    
    public static Shaper makeShapeThenWrap(FontMgr fontMgr) {
        try {
            Stats.onNativeCall();
            return new Shaper(_nMakeShapeThenWrap(Native.getPtr(fontMgr)));
        } finally {
            Reference.reachabilityFence(fontMgr);
        }
    }
    
    public static Shaper makeShapeDontWrapOrReorder() {
        return makeShapeDontWrapOrReorder(null);
    }
    
    public static Shaper makeShapeDontWrapOrReorder(FontMgr fontMgr) {
        try {
            Stats.onNativeCall();
            return new Shaper(_nMakeShapeDontWrapOrReorder(Native.getPtr(fontMgr)));
        } finally {
            Reference.reachabilityFence(fontMgr);
        }
    }

    /**
     * <p>Only works on macOS</p>
     *
     * <p>WARN broken in m87 https://bugs.chromium.org/p/skia/issues/detail?id=10897</p>
     *
     * @return Shaper on macOS, throws UnsupportedOperationException elsewhere
     */
    public static Shaper makeCoreText() {
        Stats.onNativeCall();
        long ptr = _nMakeCoreText();
        if (ptr == 0)
            throw new UnsupportedOperationException("CoreText not available");
        return new Shaper(ptr);
    }
    
    public static Shaper make() {
        return make(null);
    }
    
    public static Shaper make(FontMgr fontMgr) {
        try {
            Stats.onNativeCall();
            return new Shaper(_nMake(Native.getPtr(fontMgr)));
        } finally {
            Reference.reachabilityFence(fontMgr);
        }
    }
    
    public TextBlob shape(String text, Font font) {
        return shape(text, font, ShapingOptions.DEFAULT, Float.POSITIVE_INFINITY, Point.ZERO);
    }
    
    public TextBlob shape(String text, Font font, float width) {
        return shape(text, font, ShapingOptions.DEFAULT, width, Point.ZERO);
    }
    
    public TextBlob shape(String text, Font font, float width,Point offset) {
        return shape(text, font, ShapingOptions.DEFAULT, width, offset);
    }

    public TextBlob shape(String text, Font font,  ShapingOptions opts, float width,  Point offset) {
        try {
            assert opts != null : "Can’t Shaper::shape with opts == null";
            Stats.onNativeCall();
            long ptr = _nShapeBlob(_ptr, text, Native.getPtr(font), opts, width, offset._x, offset._y);
            return 0 == ptr ? null : new TextBlob(ptr);
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(font);
        }
    }
    
    public Shaper shape(String text,
                        Font font,
                        ShapingOptions opts,
                        float width,
                        RunHandler runHandler) {
        try (ManagedString textUtf8 = new ManagedString(text);
             FontMgrRunIterator fontIter = new FontMgrRunIterator(textUtf8, false, font, opts);
             IcuBidiRunIterator bidiIter = new IcuBidiRunIterator(textUtf8, false, opts._leftToRight ? java.text.Bidi.DIRECTION_LEFT_TO_RIGHT : java.text.Bidi.DIRECTION_RIGHT_TO_LEFT);
             HbIcuScriptRunIterator scriptIter = new HbIcuScriptRunIterator(textUtf8, false);) {
            Iterator<LanguageRun> langIter = new TrivialLanguageRunIterator(text, Locale.getDefault().toLanguageTag());
            return shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
        }
    }
    
    public Shaper shape( String text,
                         Iterator<FontRun> fontIter,
                         Iterator<BidiRun> bidiIter,
                         Iterator<ScriptRun> scriptIter,
                         Iterator<LanguageRun> langIter,
                         ShapingOptions opts,
                        float width,
                         RunHandler runHandler) {
        try (ManagedString textUtf8 = new ManagedString(text);) {
            return shape(textUtf8, fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
        }
    }

    public Shaper shape( ManagedString textUtf8,
                         Iterator<FontRun> fontIter,
                         Iterator<BidiRun> bidiIter,
                         Iterator<ScriptRun> scriptIter,
                         Iterator<LanguageRun> langIter,
                         ShapingOptions opts,
                        float width,
                         RunHandler runHandler) {
        assert fontIter != null : "FontRunIterator == null";
        assert bidiIter != null : "BidiRunIterator == null";
        assert scriptIter != null : "ScriptRunIterator == null";
        assert langIter != null : "LanguageRunIterator == null";
        assert opts != null : "Can’t Shaper::shape with opts == null";
        Stats.onNativeCall();
        _nShape(_ptr, Native.getPtr(textUtf8), fontIter, bidiIter, scriptIter, langIter, opts, width, runHandler);
        return this;
    }


    public TextLine shapeLine(String text, Font font,  ShapingOptions opts) {
        try {
            assert opts != null : "Can’t Shaper::shapeLine with opts == null";
            Stats.onNativeCall();
            return new TextLine(_nShapeLine(_ptr, text, Native.getPtr(font), opts));
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(font);
        }
    }

    public TextLine shapeLine(String text, Font font) {
        return shapeLine(text, font, ShapingOptions.DEFAULT);
    }


    public Shaper(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native long _nMakePrimitive();

    public static native long _nMakeShaperDrivenWrapper(long fontMgrPtr);

    public static native long _nMakeShapeThenWrap(long fontMgrPtr);

    public static native long _nMakeShapeDontWrapOrReorder(long fontMgrPtr);

    public static native long _nMakeCoreText();

    public static native long _nMake(long fontMgrPtr);

    public static native long _nShapeBlob(long ptr, String text, long fontPtr, ShapingOptions opts, float width, float offsetX, float offsetY);

    public static native long _nShapeLine(long ptr, String text, long fontPtr, ShapingOptions opts);

    public static native void _nShape(long ptr, long textPtr, Iterator<FontRun> fontIter, Iterator<BidiRun> bidiIter, Iterator<ScriptRun> scriptIter, Iterator<LanguageRun> langIter, ShapingOptions opts, float width, RunHandler runHandler);
}
