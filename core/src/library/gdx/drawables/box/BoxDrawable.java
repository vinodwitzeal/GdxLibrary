package library.gdx.drawables.box;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

import library.gdx.shaders.BoxShader;
import library.gdx.utils.ResourceUtils;

public class BoxDrawable extends BaseDrawable implements TransformDrawable {

    private final int NONE=0,SHADOW=1,BOX=2,OUTLINE=3;

    private BoxSize boxSize=new BoxSize();
    private BoxRadius boxRadius=new BoxRadius();
    private BoxFill boxFill=new BoxFill();
    private BoxOutline boxOutline=new BoxOutline();

    private BoxShadow boxShadow=null;
    private float x,y,width,height;
    private TextureRegion region;
    private BoxShader shader;
    private boolean dirty=true;

    public BoxDrawable(){
        region= ResourceUtils.getInstance().pixelRegion;
        shader =ResourceUtils.getInstance().boxShader;
    }

    public BoxDrawable(BoxDrawable drawable){
        super(drawable);
        this.region=drawable.region;
        this.shader =drawable.shader;
        this.boxSize.set(drawable.boxSize);
        this.boxRadius.set(drawable.boxRadius);
        this.boxFill.set(drawable.boxFill);
        this.boxOutline.set(drawable.boxOutline);
        if (drawable.boxShadow!=null){
            this.boxShadow=new BoxShadow(drawable.boxShadow);
        }
        this.dirty=true;
    }

    public BoxDrawable smoothness(float smoothness){
        this.boxSize.smoothness=smoothness;
        dirty=true;
        return this;
    }

    public BoxDrawable radius(float radius){
        return this.radius(radius,radius,radius,radius);
    }

    public BoxDrawable radius(float topLeft,float topRight,float bottomRight,float bottomLeft){
        boxRadius.topLeft=topLeft;
        boxRadius.topRight=topRight;
        boxRadius.bottomRight=bottomRight;
        boxRadius.bottomLeft=bottomLeft;
        this.dirty=true;
        return this;
    }


    public BoxDrawable outline(float outline,Color outlineColor){
        boxOutline.size=outline;
        boxOutline.solid(outlineColor);
        boxOutline.type=BoxOutline.OUTER;
        dirty=true;
        return this;
    }

    public BoxDrawable outline(float outline,Color outlineColor,int outlineType){
        if (outline<0.0f)return this;
        this.boxOutline.size=outline;
        this.boxOutline.type=outlineType;
        this.boxOutline.solid(outlineColor);
        this.dirty=true;
        return this;
    }

    public BoxDrawable outline(BoxOutline boxOutline){
        this.boxOutline=boxOutline;
        this.dirty=true;
        return this;
    }

    public BoxDrawable fillNone(){
        this.boxFill.type=BoxFill.NONE;
        this.dirty=true;
        return this;
    }

    public BoxDrawable fillSolid(Color color){
        this.boxFill.solid(color);
        this.dirty=true;
        return this;
    }

    public BoxDrawable linearGradient(Color startColor,Color endColor,float angle){
        return linearGradient(startColor,endColor,new Vector2(0.5f,0.5f),angle);
    }

    public BoxDrawable linearGradient(Color startColor,Color endColor,Vector2 position,float angle){
        this.boxFill.linear(startColor,endColor,position,angle);
        this.dirty=true;
        return this;
    }


    public BoxDrawable radialGradient(Color startColor,Color endColor,Vector2 position,float radius){
        this.boxFill.type=BoxFill.RADIAL;
        this.boxFill.start.set(startColor);
        this.boxFill.end.set(endColor);
        this.boxFill.position.set(position);
        this.boxFill.radius=radius;
        this.dirty=true;
        return this;
    }

    public BoxDrawable shadow(Vector2 offset,Color color,float shadowSmoothness){
        this.boxShadow=new BoxShadow();
        this.boxShadow.offset.set(offset);
        this.boxShadow.color.set(color);
        this.boxShadow.smoothness =shadowSmoothness;
        this.dirty=true;
        return this;
    }

    private void validate(float x,float y,float width,float height){
        if (this.x==x && this.y==y && this.width==width && this.height==height && !dirty)return;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.dirty=false;
        boxSize.width=width;
        boxSize.height=height;
//        float maxDimension=Math.max(width,height);
//        region=new TextureRegion(texture,0,0,width/maxDimension,height/maxDimension);
    }


    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        draw(batch,x,y,0,0,width,height,1,1,0);
    }

    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        validate(x,y,width,height);
        beginShader(batch);
        drawShadow(batch,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
        drawShape(batch,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
        drawOutline(batch,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
        endShader(batch);
    }

    private void drawShadow(Batch batch, float x, float y,float originX,float originY, float width, float height,float scaleX,float scaleY,float rotation){
        if (boxShadow==null)return;
        shader.setShapeType(SHADOW);
        drawRegion(batch,x+boxShadow.offset.x,y+boxShadow.offset.y,originX,originY,width+boxShadow.smoothness,height+boxShadow.smoothness,scaleX,scaleY,rotation);
        batch.flush();
    }

    private void drawShape(Batch batch, float x, float y,float originX,float originY, float width, float height,float scaleX,float scaleY,float rotation){
        if (boxFill.type==BoxFill.NONE)return;
        shader.setShapeType(BOX);
        boxFill.apply(shader);
        drawRegion(batch,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
        batch.flush();
    }

    private void drawOutline(Batch batch, float x, float y,float originX,float originY, float width, float height,float scaleX,float scaleY,float rotation){
        if (boxOutline.size<=0.0f)return;
        shader.setShapeType(OUTLINE);
        boxOutline.fill.apply(shader);
        drawRegion(batch,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
        batch.flush();
    }

    private void drawRegion(Batch batch, float x, float y,float originX,float originY, float width, float height,float scaleX,float scaleY,float rotation){
        batch.draw(region,x,y,originX,originY,width,height,scaleX,scaleY,rotation);
    }

    private void beginShader(Batch batch){
        shader.begin(batch);
        if (boxShadow!=null){
            boxShadow.apply(shader);
        }
        boxSize.apply(shader);
        boxRadius.apply(shader);
        boxFill.apply(shader);
        boxOutline.apply(shader);
    }



    private void endShader(Batch batch){
        shader.setShapeType(NONE);
        shader.end(batch);
    }

    public BoxDrawable tint(Color color){
        BoxDrawable boxDrawable=new BoxDrawable(this);
        boxDrawable.boxFill=boxDrawable.boxFill.tint(color);
        boxDrawable.boxOutline=boxDrawable.boxOutline.tint(color);
        if(boxDrawable.boxShadow!=null){
            boxDrawable.boxShadow=boxDrawable.boxShadow.tint(color);
        }
        return boxDrawable;
    }
}
