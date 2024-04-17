package com.badlogic.gdx.graphics.g2d.skia.paragraph;

import com.badlogic.gdx.graphics.g2d.skia.*;

public class Shadow {
    public final int color;
    public final float offsetX;
    public final float offsetY;
    public final double blurSigma;

    public Shadow(int color, float offsetX, float offsetY, double blurSigma) {
        this.color = color;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.blurSigma = blurSigma;
    }

    public Point getOffset() {
        return new Point(offsetX, offsetY);
    }

    public int getColor() {
        return color;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public double getBlurSigma() {
        return blurSigma;
    }
}
