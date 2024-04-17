package com.badlogic.gdx.graphics.g2d.skia.shaper;


import com.badlogic.gdx.graphics.g2d.skia.*;

public class ShapingOptions {
    public static final ShapingOptions DEFAULT = new ShapingOptions(null, null, true, true, true);

    public final FontMgr _fontMgr;

    public final FontFeature[] _features;


    public final boolean _leftToRight;

    /**
     * If enabled, fallback font runs will not be broken by whitespace from original font
     */

    public final boolean _approximateSpaces;

    /**
     * If enabled, fallback font runs will not be broken by punctuation from original font
     */

    public final boolean _approximatePunctuation;

    public ShapingOptions(FontMgr _fontMgr, FontFeature[] _features, boolean _leftToRight, boolean _approximateSpaces, boolean _approximatePunctuation) {
        this._fontMgr = _fontMgr;
        this._features = _features;
        this._leftToRight = _leftToRight;
        this._approximateSpaces = _approximateSpaces;
        this._approximatePunctuation = _approximatePunctuation;
    }

    public ShapingOptions withFeatures(FontFeature[] features) {
        return new ShapingOptions(_fontMgr, features, _leftToRight, _approximateSpaces, _approximatePunctuation);
    }

    
    public ShapingOptions withFeatures(String featuresString) {
        return featuresString == null ? withFeatures((FontFeature[]) null) : withFeatures(FontFeature.parse(featuresString));
    }
}
