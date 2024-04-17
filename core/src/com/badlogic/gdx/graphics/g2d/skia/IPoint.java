package com.badlogic.gdx.graphics.g2d.skia;


public class IPoint {
    public static final IPoint ZERO = new IPoint(0, 0);


    public final int _x;


    public final int _y;

    public IPoint(int _x, int _y) {
        this._x = _x;
        this._y = _y;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public IPoint offset(int dx, int dy) {
        return new IPoint(_x + dx, _y + dy);
    }

    
    public IPoint offset( IPoint vec) {
        assert vec != null : "IPoint::offset expected other != null";
        return offset(vec._x, vec._y);
    }

    public boolean isEmpty() {
        return _x <= 0 || _y <= 0;
    }


    public static IPoint _makeFromLong(long l) {
        return new IPoint((int) (l >>> 32), (int) (l & 0xFFFFFFFF));
    }
}