package com.badlogic.gdx.graphics.g2d.skia;

public interface SkiaRenderDelegate {
    void onRender(Canvas canvas,int width,int height,long nanoTime);
}
