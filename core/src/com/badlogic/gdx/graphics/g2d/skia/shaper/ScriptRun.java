package com.badlogic.gdx.graphics.g2d.skia.shaper;


import com.badlogic.gdx.graphics.g2d.skia.*;

public class ScriptRun {
    public final int end;
    public final int scriptTag;

    public ScriptRun(int end, int scriptTag) {
        this.end = end;
        this.scriptTag = scriptTag;
    }

    public ScriptRun(int end, String script) {
        this(end, FourByteTag.fromString(script));
    }

    /**
     * Should be iso15924 codes.
     */
    public String getScript() {
        return FourByteTag.toString(scriptTag);
    }
}
