package com.badlogic.gdx.graphics.g2d.skia.svg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public abstract class SVGContainer extends SVGTransformableNode {
    static {
        Library.staticLoad();
    }


    public SVGContainer(long ptr) {
        super(ptr);
    }
}