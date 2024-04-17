package com.badlogic.gdx.graphics.g2d.skia.svg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public abstract class SVGNode extends RefCnt {
    static {
        Library.staticLoad();
    }


    public SVGNode(long ptr) {
        super(ptr);
    }

    
    public SVGTag getTag() {
        try {
            Stats.onNativeCall();
            return SVGTag._values[_nGetTag(_ptr)];
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native int _nGetTag(long ptr);
}