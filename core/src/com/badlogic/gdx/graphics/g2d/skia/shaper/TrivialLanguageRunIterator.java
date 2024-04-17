package com.badlogic.gdx.graphics.g2d.skia.shaper;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class TrivialLanguageRunIterator implements Iterator<LanguageRun> {
    public final int _length;
    public final String _language;
    public boolean _atEnd;

    public TrivialLanguageRunIterator(String text, String language) {
        _length = text.length();
        _language = language;
        _atEnd = _length == 0;
    }

    @Override
    public LanguageRun next() {
        _atEnd = true;
        return new LanguageRun(_length, _language);
    }

    @Override
    public boolean hasNext() {
        return !_atEnd;
    }
}