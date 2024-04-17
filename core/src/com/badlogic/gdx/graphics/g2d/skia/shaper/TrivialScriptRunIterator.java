package com.badlogic.gdx.graphics.g2d.skia.shaper;

import java.util.*;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class TrivialScriptRunIterator implements Iterator<ScriptRun> {
    public final int _length;
    public final String _script;
    public boolean _atEnd;

    public TrivialScriptRunIterator(String text, String script) {
        _length = text.length();
        _script = script;
        _atEnd = _length == 0;
    }

    @Override
    public ScriptRun next() {
        _atEnd = true;
        return new ScriptRun(_length, _script);
    }

    @Override
    public boolean hasNext() {
        return !_atEnd;
    }
}