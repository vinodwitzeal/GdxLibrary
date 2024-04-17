package com.badlogic.gdx.graphics.g2d.skia.shaper;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class TrivialFontRunIterator implements Iterator<FontRun> {
    public final int _length;
    public final Font _font;
    public boolean _atEnd;

    public TrivialFontRunIterator(String text, Font font) {
        _length = text.length();
        _font = font;
        _atEnd = _length == 0;
    }

    @Override
    public FontRun next() {
        _atEnd = true;
        return new FontRun(_length, _font);
    }

    @Override
    public boolean hasNext() {
        return !_atEnd;
    }
}