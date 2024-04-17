package com.badlogic.gdx.graphics.g2d.skia;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.impl.*;

/**
 * A utility proxy base class for implementing draw/paint filters.
 */
public abstract class PaintFilterCanvas extends Canvas {
    static {
        Library.staticLoad();
    }

    /**
     * @param unrollDrawable if needed to filter nested drawable content using this canvas (for drawables there is no paint to filter)
     */
    public PaintFilterCanvas( Canvas canvas, boolean unrollDrawable) {
        super(_nMake(Native.getPtr(canvas), unrollDrawable), true, canvas);
        Stats.onNativeCall();
        _nAttachToJava(_ptr);
        Stats.onNativeCall();
        Reference.reachabilityFence(canvas);
    }

    /**
     * Called with the paint that will be used to draw the specified type.
     * The implementation may modify the paint as they wish.
     * <p>
     * The result boolean is used to determine whether the draw op is to be
     * executed (true) or skipped (false).
     * <p>
     * Note: The base implementation calls onFilter() for top-level/explicit paints only.
     */
    public abstract boolean onFilter( Paint paint);

    public final boolean onFilter(long paintPtr) {
        Paint paint = new Paint(paintPtr, false);
        return onFilter(paint);
    }

    public native void _nAttachToJava(long canvasPtr);

    public static native long _nMake(long canvasPtr, boolean unrollDrawable);
}
