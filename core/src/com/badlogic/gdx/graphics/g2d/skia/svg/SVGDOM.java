package com.badlogic.gdx.graphics.g2d.skia.svg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class SVGDOM extends RefCnt {
    static {
        Library.staticLoad();
    }

    public SVGDOM( Data data) {
        this(_nMakeFromData(Native.getPtr(data)));
        Stats.onNativeCall();
        Reference.reachabilityFence(data);
    }

    
    public SVGSVG getRoot() {
        try {
            Stats.onNativeCall();
            long ptr = _nGetRoot(_ptr);
            return ptr == 0 ? null : new SVGSVG(ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    /**
     * Deprecated. Use getRoot().intrinsicSize() instead
     */
    
    @Deprecated
    public Point getContainerSize() {
        try {
            return _nGetContainerSize(_ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public SVGDOM setContainerSize(float width, float height) {
        Stats.onNativeCall();
        _nSetContainerSize(_ptr, width, height);
        return this;
    }

    
    
    public SVGDOM setContainerSize(Point size) {
        Stats.onNativeCall();
        _nSetContainerSize(_ptr, size._x, size._y);
        return this;
    }

    // sk_sp<SkSVGNode>* findNodeById(const char* id);

    
    
    public SVGDOM render( Canvas canvas) {
        try {
            Stats.onNativeCall();
            _nRender(_ptr, Native.getPtr(canvas));
            return this;
        } finally {
            Reference.reachabilityFence(canvas);
        }
    }


    public SVGDOM(long ptr) {
        super(ptr);
    }

    public static native long _nMakeFromData(long dataPtr);

    public static native long _nGetRoot(long ptr);

    public static native Point _nGetContainerSize(long ptr);

    public static native void _nSetContainerSize(long ptr, float width, float height);

    public static native void _nRender(long ptr, long canvasPtr);
}