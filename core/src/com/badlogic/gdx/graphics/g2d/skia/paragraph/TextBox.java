package com.badlogic.gdx.graphics.g2d.skia.paragraph;


import com.badlogic.gdx.graphics.g2d.skia.Rect;

public class TextBox {
    public final Rect rect;
    public final Direction direction;

    public TextBox(Rect rect, Direction direction) {
        this.rect = rect;
        this.direction = direction;
    }

    public TextBox(float l, float t, float r, float b, int direction) {
        this(Rect.makeLTRB(l, t, r, b), Direction._values[direction]);
    }
}
