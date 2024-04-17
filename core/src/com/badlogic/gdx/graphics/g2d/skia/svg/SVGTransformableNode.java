package com.badlogic.gdx.graphics.g2d.skia.svg;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public abstract class SVGTransformableNode extends SVGNode {
    static {
        Library.staticLoad();
    }


    public SVGTransformableNode(long ptr) {
        super(ptr);
    }
}