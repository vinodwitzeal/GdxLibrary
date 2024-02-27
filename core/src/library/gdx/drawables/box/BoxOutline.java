package library.gdx.drawables.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import library.gdx.shaders.BoxShader;

public class BoxOutline {
    public static final int OUTER = 0, INNER = 1, CENTER = 2;

    public int type;
    public float size;

    public BoxFill fill;

    public BoxOutline(int type,float size, BoxFill fill) {
        this.type = type;
        this.size=size;
        this.fill = fill;
    }

    public BoxOutline(int type,float size, Color color) {
        this(type,size, new BoxFill().solid(color));
    }

    public BoxOutline(float size,Color color) {
        this(OUTER,size, color);
    }

    public BoxOutline(int type,float size) {
        this.type = type;
        this.size=size;
        fill = new BoxFill();
    }

    public BoxOutline(float size) {
        this(OUTER,size);
    }

    public BoxOutline(){
        this(0.0f);
    }

    public BoxOutline solid(Color color) {
        this.fill.solid(color);
        return this;
    }

    public BoxOutline linear(Color start, Color end, Vector2 position,float angle) {
        this.fill.linear(start, end, position,angle);
        return this;
    }

    public BoxOutline radial(Color start, Color end, Vector2 position,float radius) {
        this.fill.radial(start, end, position,radius);
        return this;
    }


    public BoxOutline tint(Color color) {
        BoxOutline outline = new BoxOutline(this.type,this.size, this.fill);
        outline.fill=outline.fill.tint(color);
        return outline;
    }


    public void set(BoxOutline boxOutline){
        this.type=boxOutline.type;
        this.size=boxOutline.size;
        this.fill.set(boxOutline.fill);
    }


    public void apply(BoxShader shader){
        shader.setOutline(size);
    }
}
