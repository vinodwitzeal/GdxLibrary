package library.gdx.drawables.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import library.gdx.shaders.BoxShader;

public class BoxFill {
    public static final int NONE=0,SOLID=1,LINEAR=2,RADIAL=3;

    protected int type;
    protected Color solid=Color.valueOf("#00000000");
    protected Color start=Color.valueOf("#00000000");
    protected Color end=Color.valueOf("#00000000");

    protected final Vector2 position=new Vector2();

    protected float angle=0.0f;
    protected float radius=0.0f;
    public BoxFill(){
        type=NONE;
    }

    public BoxFill(BoxFill boxFill){
        this.type=boxFill.type;
        this.solid.set(boxFill.solid);
        this.start.set(boxFill.start);
        this.end.set(boxFill.end);
        this.position.set(boxFill.position);
        this.angle=boxFill.angle;
        this.radius=boxFill.radius;
    }

    public BoxFill solid(Color color){
        this.type=SOLID;
        this.solid=color;
        return this;
    }

    public BoxFill linear(Color start, Color end, Vector2 position,float angle){
        this.type=LINEAR;
        this.start=start;
        this.end=end;
        this.position.set(position);
        this.angle=angle;
        return this;
    }

    public BoxFill radial(Color start,Color end,Vector2 position,float radius){
        this.type=RADIAL;
        this.start=start;
        this.end=end;
        this.position.set(position);
        this.radius=radius;
        return this;
    }


    public void set(BoxFill boxFill){
        this.type=boxFill.type;
        this.solid.set(boxFill.solid);
        this.start.set(boxFill.start);
        this.end.set(boxFill.end);
        this.position.set(boxFill.position);
        this.angle=boxFill.angle;
        this.radius=boxFill.radius;
    }


    public void apply(BoxShader shader){
        shader.setFillType(type);
        shader.setSolidColor(solid);
        shader.setGradientStartColor(start);
        shader.setGradientEndColor(end);
        shader.setGradientPosition(position);
        shader.setGradientAngle(angle);
        shader.setGradientRadius(radius);
    }


    public BoxFill tint(Color color){
        BoxFill boxFill=new BoxFill(this);
        boxFill.solid.mul(color);
        boxFill.start.mul(color);
        boxFill.end.mul(color);
        return this;
    }
}
