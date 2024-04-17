package com.badlogic.gdx.graphics.g2d.skia;
public class GradientStyle {
    public static final int _INTERPOLATE_PREMUL = 1;
    public static GradientStyle DEFAULT = new GradientStyle(FilterTileMode.CLAMP, true, null);

    public final FilterTileMode _tileMode;
    public final boolean _premul;
    public final Matrix33 _localMatrix;

    public GradientStyle(FilterTileMode _tileMode, boolean _premul, Matrix33 _localMatrix) {
        this._tileMode = _tileMode;
        this._premul = _premul;
        this._localMatrix = _localMatrix;
    }

    public int _getFlags() {
        return 0 | (_premul ? _INTERPOLATE_PREMUL : 0);
    }

    public FilterTileMode getTileMode() {
        return _tileMode;
    }

    public float[] _getMatrixArray() {
        return _localMatrix == null ? null : _localMatrix.getMat();
    }
}
