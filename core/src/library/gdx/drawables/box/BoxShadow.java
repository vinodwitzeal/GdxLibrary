package library.gdx.drawables.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import library.gdx.shaders.BoxShader;

public class BoxShadow {

    protected Vector2 offset =new Vector2();

    protected Color color =Color.valueOf("#000000");
    protected float smoothness =0.0f;

    public BoxShadow(){

    }

    public BoxShadow(Vector2 offset,Color color,float smoothness){
        this.offset.set(offset);
        this.color.set(color);
        this.smoothness=smoothness;
    }

    public BoxShadow(BoxShadow boxShadow){
        this.offset.set(boxShadow.offset);
        this.color.set(boxShadow.color);
        this.smoothness=boxShadow.smoothness;
    }


    public BoxShadow tint(Color color){
        BoxShadow boxShadow=new BoxShadow(this);
        boxShadow.color.mul(color);
        return boxShadow;
    }


    public void apply(BoxShader shader){
        shader.setShadowColor(color);
        shader.setShadowSmoothness(smoothness);
    }
}
