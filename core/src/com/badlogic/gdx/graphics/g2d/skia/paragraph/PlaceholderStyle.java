package com.badlogic.gdx.graphics.g2d.skia.paragraph;


public class PlaceholderStyle {
    public final float width;
    public final float height;
    public final PlaceholderAlignment alignment;
    public final BaselineMode baselineMode;

    /**
     * <p>Distance from the top edge of the rect to the baseline position. This
     * baseline will be aligned against the alphabetic baseline of the surrounding
     * text.</p>
     *
     * <p>Positive values drop the baseline lower (positions the rect higher) and
     * small or negative values will cause the rect to be positioned underneath
     * the line. When baseline == height, the bottom edge of the rect will rest on
     * the alphabetic baseline.</p>
     */
    public final float baseline;

    public PlaceholderStyle(float width, float height, PlaceholderAlignment alignment, BaselineMode baselineMode, float baseline) {
        this.width = width;
        this.height = height;
        this.alignment = alignment;
        this.baselineMode = baselineMode;
        this.baseline = baseline;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public PlaceholderAlignment getAlignment() {
        return alignment;
    }

    public BaselineMode getBaselineMode() {
        return baselineMode;
    }

    public float getBaseline() {
        return baseline;
    }
}