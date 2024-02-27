package library.gdx.drawables.box;

import library.gdx.shaders.BoxShader;

public class BoxRadius {
    protected float topLeft=0.0f,topRight=0.0f,bottomRight=0.0f,bottomLeft=0.0f;

    public BoxRadius(float topLeft,float topRight,float bottomRight,float bottomLeft){
        this.topLeft=topLeft;
        this.topRight=topRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
    }

    public BoxRadius(float radius){
        this(radius,radius,radius,radius);
    }

    public BoxRadius(){
        this(0.0f);
    }

    public void set(BoxRadius boxRadius){
        set(boxRadius.topLeft,boxRadius.topRight, boxRadius.bottomRight,boxRadius.bottomLeft);
    }

    public void set(float topLeft,float topRight,float bottomRight,float bottomLeft){
        this.topLeft=topLeft;
        this.topRight=topRight;
        this.bottomRight=bottomRight;
        this.bottomLeft=bottomLeft;
    }

    public void apply(BoxShader shader){
        shader.setRadius(topLeft,topRight,bottomRight,bottomLeft);
    }
}
