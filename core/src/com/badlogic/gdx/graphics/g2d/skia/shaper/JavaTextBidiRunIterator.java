package com.badlogic.gdx.graphics.g2d.skia.shaper;

import java.text.*;
import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class JavaTextBidiRunIterator implements Iterator<BidiRun> {
    public final Bidi _bidi;
    public final int _runsCount;
    public int _run;

    public JavaTextBidiRunIterator(String text) {
        this(text, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
    }

    public JavaTextBidiRunIterator(String text, int flags) {
        _bidi = new Bidi(text, flags);
        _runsCount = _bidi.getRunCount();
        _run = -1;
    }

    @Override
    public BidiRun next() {
        _run++;
        return new BidiRun(_bidi.getRunLimit(_run), _bidi.getRunLevel(_run));
    }

    @Override
    public boolean hasNext() {
        return _run + 1 < _runsCount;
    }
}