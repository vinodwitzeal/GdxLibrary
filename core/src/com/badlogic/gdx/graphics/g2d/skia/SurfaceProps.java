package com.badlogic.gdx.graphics.g2d.skia;


import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class SurfaceProps {

    public final boolean _deviceIndependentFonts;


    public final PixelGeometry _pixelGeometry;

    public SurfaceProps(boolean _deviceIndependentFonts, PixelGeometry _pixelGeometry) {
        this._deviceIndependentFonts = _deviceIndependentFonts;
        this._pixelGeometry = _pixelGeometry;
    }

    public SurfaceProps() {
        this(false, PixelGeometry.UNKNOWN);
    }

    public SurfaceProps(PixelGeometry geo) {
        this(false, geo);
    }


    public int _getFlags() {
        return 0 | (_deviceIndependentFonts ? 1 : 0);
    }


    public int _getPixelGeometryOrdinal() {
        return _pixelGeometry.ordinal();
    }
}