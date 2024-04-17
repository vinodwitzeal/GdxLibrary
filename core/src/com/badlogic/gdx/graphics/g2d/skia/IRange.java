package com.badlogic.gdx.graphics.g2d.skia;


public class IRange {

    public final int _start;


    public final int _end;

    public IRange(int _start, int _end) {
        this._start = _start;
        this._end = _end;
    }

    public static IRange _makeFromLong(long l) {
        return new IRange((int) (l >>> 32), (int) (l & 0xFFFFFFFF));
    }
}