package com.badlogic.gdx.graphics.g2d.skia.shaper;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;


import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class FontRun {
    public final int end;
    public final Font font;


    public FontRun(int end, Font font) {
        this.end = end;
        this.font = font;
    }


    public long _getFontPtr() {
        try {
            return Native.getPtr(font);
        } finally {
            Reference.reachabilityFence(font);
        }
    }


}
