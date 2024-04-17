package com.badlogic.gdx.graphics.g2d.skia;

import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public abstract class WStream extends Managed {
    public WStream(long ptr, long finalizer) {
        super(ptr, finalizer);
    }

    public WStream(long ptr, long finalizer, boolean managed) {
        super(ptr, finalizer, managed);
    }
}