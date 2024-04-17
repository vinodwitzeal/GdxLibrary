package com.badlogic.gdx.graphics.g2d.skia.paragraph;


public class PositionWithAffinity {
    public final int position;
    public final Affinity affinity;


    public PositionWithAffinity(int position, Affinity affinity) {
        this.position = position;
        this.affinity = affinity;
    }

    public int getPosition() {
        return position;
    }

    public Affinity getAffinity() {
        return affinity;
    }
}
