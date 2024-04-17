package com.badlogic.gdx.graphics.g2d.skia;


public class Point {
    public static final Point ZERO = new Point(0, 0);


    public final float _x;


    public final float _y;


    public Point(float _x, float _y) {
        this._x = _x;
        this._y = _y;
    }

    public static float[] flattenArray(Point[] pts) {
        if (pts == null) return null;
        float[] arr = new float[pts.length * 2];
        for (int i = 0; i < pts.length; ++i) {
            arr[i * 2] = pts[i]._x;
            arr[i * 2 + 1] = pts[i]._y;
        }
        return arr;
    }

    public static Point[] fromArray(float[] pts) {
        if (pts == null) return null;
        assert pts.length % 2 == 0 : "Expected " + pts.length + " % 2 == 0";
        Point[] arr = new Point[pts.length / 2];
        for (int i = 0; i < pts.length / 2; ++i)
            arr[i] = new Point(pts[i * 2], pts[i * 2 + 1]);
        return arr;
    }

    public Point offset(float dx, float dy) {
        return new Point(_x + dx, _y + dy);
    }

    public Point offset(Point vec) {
        assert vec != null : "Point::offset expected other != null";
        return offset(vec._x, vec._y);
    }

    public Point scale(float scale) {
        return scale(scale, scale);
    }

    public Point scale(float sx, float sy) {
        return new Point(_x * sx, _y * sy);
    }

    public boolean isEmpty() {
        return _x <= 0 || _y <= 0;
    }
}