package library.gdx.drawables.box;

import library.gdx.shaders.BoxShader;

public class BoxSize {
    protected float width,height;
    protected float smoothness=1.0f;

    public BoxSize(float width,float height){
        this.width=width;
        this.height=height;
    }

    public BoxSize(){
        this(0.0f,0.0f);
    }


    public void apply(BoxShader shader){
        shader.setSize(width,height);
        shader.setSmoothness(smoothness);
    }

    public void set(BoxSize boxSize){
        set(boxSize.width,boxSize.height,boxSize.smoothness);
    }

    public void set(float width,float height,float smoothness){
        this.width=width;
        this.height=height;
        this.smoothness=smoothness;
    }
}
