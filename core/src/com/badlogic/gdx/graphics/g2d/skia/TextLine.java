package com.badlogic.gdx.graphics.g2d.skia;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.impl.*;
import com.badlogic.gdx.graphics.g2d.skia.shaper.*;

public class TextLine extends Managed {
    static {
        Library.staticLoad();
    }


    public TextLine(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }
    public static TextLine make(String text, Font font) {
        return make(text, font, ShapingOptions.DEFAULT);
    }
    
    public static TextLine make(String text, Font font, ShapingOptions opts) {
        try (Shaper shaper = Shaper.makeShapeDontWrapOrReorder();) {
            return shaper.shapeLine(text, font, opts);
        }
    }

    public float getAscent() {
        Stats.onNativeCall();
        try {
            return _nGetAscent(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getCapHeight() {
        Stats.onNativeCall();
        try {
            return _nGetCapHeight(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getXHeight() {
        Stats.onNativeCall();
        try {
            return _nGetXHeight(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getDescent() {
        Stats.onNativeCall();
        try {
            return _nGetDescent(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getLeading() {
        Stats.onNativeCall();
        try {
            return _nGetLeading(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getWidth() {
        Stats.onNativeCall();
        try {
            return _nGetWidth(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public float getHeight() {
        Stats.onNativeCall();
        try {
            return _nGetHeight(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    public TextBlob getTextBlob() {
        Stats.onNativeCall();
        try {
            long res = _nGetTextBlob(_ptr);
            return res == 0 ? null : new TextBlob(res);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public short[] getGlyphs() {
        Stats.onNativeCall();
        try {
            return _nGetGlyphs(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * @return [x0, y0, x1, y1, ...]
     */
    public float[] getPositions() {
        Stats.onNativeCall();
        try {
            return _nGetPositions(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * @param x coordinate in px
     * @return UTF-16 offset of glyph
     */
    public int getOffsetAtCoord(float x) {
        try {
            Stats.onNativeCall();
            return _nGetOffsetAtCoord(_ptr, x);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * @param x coordinate in px
     * @return UTF-16 offset of glyph strictly left of coord
     */
    public int getLeftOffsetAtCoord(float x) {
        try {
            Stats.onNativeCall();
            return _nGetLeftOffsetAtCoord(_ptr, x);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * @param offset UTF-16 character offset
     * @return glyph coordinate
     */
    public float getCoordAtOffset(int offset) {
        try {
            Stats.onNativeCall();
            return _nGetCoordAtOffset(_ptr, offset);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * <p>Returns the number of intervals that intersect bounds.
     * bounds describes a pair of lines parallel to the text advance.
     * The return array size is a multiple of two, and is at most twice the number of glyphs in
     * the the blob.</p>
     *
     * @param lowerBound lower line parallel to the advance
     * @param upperBound upper line parallel to the advance
     * @return intersections; may be null
     */
    public float[] getIntercepts(float lowerBound, float upperBound) {
        return getIntercepts(lowerBound, upperBound, null);
    }

    /**
     * <p>Returns the number of intervals that intersect bounds.
     * bounds describes a pair of lines parallel to the text advance.
     * The return array size is a multiple of two, and is at most twice the number of glyphs in
     * the the blob.</p>
     *
     * @param lowerBound lower line parallel to the advance
     * @param upperBound upper line parallel to the advance
     * @param paint      specifies stroking, PathEffect that affects the result; may be null
     * @return intersections; may be null
     */
    public float[] getIntercepts(float lowerBound, float upperBound, Paint paint) {
        try (TextBlob blob = getTextBlob()) {
            return blob == null ? null : blob.getIntercepts(lowerBound, upperBound, paint);
        } finally {
            Reference.reachabilityFence(this);
            Reference.reachabilityFence(paint);
        }
    }

    public static native long _nGetFinalizer();

    public static native float _nGetAscent(long ptr);

    public static native float _nGetCapHeight(long ptr);

    public static native float _nGetXHeight(long ptr);

    public static native float _nGetDescent(long ptr);

    public static native float _nGetLeading(long ptr);

    public static native float _nGetWidth(long ptr);

    public static native float _nGetHeight(long ptr);

    public static native long _nGetTextBlob(long ptr);

    public static native short[] _nGetGlyphs(long ptr);

    public static native float[] _nGetPositions(long ptr);

    public static native float[] _nGetRunPositions(long ptr);

    public static native float[] _nGetBreakPositions(long ptr);

    public static native int[] _nGetBreakOffsets(long ptr);

    public static native int _nGetOffsetAtCoord(long ptr, float x);

    public static native int _nGetLeftOffsetAtCoord(long ptr, float x);

    public static native float _nGetCoordAtOffset(long ptr, int offset);
}