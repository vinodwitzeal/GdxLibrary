package com.badlogic.gdx.graphics.g2d.skia.svg;


public class SVGPreserveAspectRatio {
    public final SVGPreserveAspectRatioAlign _align;
    public final SVGPreserveAspectRatioScale _scale;

    public SVGPreserveAspectRatio(SVGPreserveAspectRatioAlign _align, SVGPreserveAspectRatioScale _scale) {
        this._align = _align;
        this._scale = _scale;
    }

    public SVGPreserveAspectRatio(int align, int scale) {
        this(SVGPreserveAspectRatioAlign.valueOf(align), SVGPreserveAspectRatioScale._values[scale]);
    }

    public SVGPreserveAspectRatio() {
        this(SVGPreserveAspectRatioAlign.XMID_YMID, SVGPreserveAspectRatioScale.MEET);
    }

    public SVGPreserveAspectRatio(SVGPreserveAspectRatioAlign align) {
        this(align, SVGPreserveAspectRatioScale.MEET);
    }

    public SVGPreserveAspectRatio(SVGPreserveAspectRatioScale scale) {
        this(SVGPreserveAspectRatioAlign.XMID_YMID, scale);
    }
}