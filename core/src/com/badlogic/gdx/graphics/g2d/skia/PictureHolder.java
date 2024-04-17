package com.badlogic.gdx.graphics.g2d.skia;

public class PictureHolder {
    public Picture picture;
    public int width,height;
    public PictureHolder(Picture picture,int width,int height){
        this.picture=picture;
        this.width=width;
        this.height=height;
    }
}
