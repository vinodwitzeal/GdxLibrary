package com.badlogic.gdx.graphics.g2d.skia;

import java.util.regex.*;


public class FontVariation {
    public static final FontVariation[] EMPTY = new FontVariation[0];

    public final int _tag;
    public final float _value;

    public FontVariation(int _tag, float _value) {
        this._tag = _tag;
        this._value = _value;
    }

    public FontVariation(String feature, float value) {
        this(FourByteTag.fromString(feature), value);
    }

    public String getTag() {
        return FourByteTag.toString(_tag);
    }

    public String toString() {
        return getTag() + "=" + _value;
    }


    public static final Pattern _splitPattern = Pattern.compile("\\s+");


    public static final Pattern _variationPattern = Pattern.compile("(?<tag>[a-z0-9]{4})=(?<value>\\d+)");

    public static FontVariation parseOne(String s) {
        Matcher m = _variationPattern.matcher(s);
        if (!m.matches())
            throw new IllegalArgumentException("Can’t parse FontVariation: " + s);
        float value = Float.parseFloat(m.group("value"));
        return new FontVariation(m.group("tag"), value);
    }

    public static FontVariation[] parse(String s) {
        return _splitPattern.splitAsStream(s).map(FontVariation::parseOne).toArray(FontVariation[]::new);
    }
}
