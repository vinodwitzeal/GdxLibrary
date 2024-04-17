package com.badlogic.gdx.graphics.g2d.skia.shaper;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class TrivialBidiRunIterator implements Iterator<BidiRun> {
    public final int _length;
    public final int _level;
    public boolean _atEnd;

    public TrivialBidiRunIterator(String text, int level) {
        _length = text.length();
        _level = level;
        _atEnd = _length == 0;
    }

    @Override
    public BidiRun next() {
        _atEnd = true;
        return new BidiRun(_length, _level);
    }

    @Override
    public boolean hasNext() {
        return !_atEnd;
    }
}