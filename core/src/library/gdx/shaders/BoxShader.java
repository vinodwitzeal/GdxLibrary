package library.gdx.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class BoxShader extends Shader{
    public BoxShader(ShaderProgram shaderProgram) {
        super(shaderProgram);
    }

    public void setShapeType(int type){
        program.setUniformi("u_type",type);
    }

    public void setSize(float width,float height){
        program.setUniformf("u_size",width,height);
    }

    public void setSmoothness(float smoothness){
        program.setUniformf("u_smoothness",smoothness);
    }

    public void setRadius(float topLeft,float topRight,float bottomRight,float bottomLeft){
        program.setUniformf("u_radius",bottomRight,topRight,bottomLeft,topLeft);
    }

    public void setOutline(float size){
        program.setUniformf("u_outline_size",size);
    }

    public void setShadowColor(Color color){
        program.setUniformf("u_shadowColor",color);
    }

    public void setShadowSmoothness(float smoothness){
        program.setUniformf("u_shadow_smoothness",smoothness);
    }

    public void setFillType(int type){
        program.setUniformi("u_fill_type",type);
    }

    public void setSolidColor(Color color){
        program.setUniformf("u_solid",color);
    }

    public void setGradientStartColor(Color color){
        program.setUniformf("u_start",color);
    }

    public void setGradientEndColor(Color color){
        program.setUniformf("u_end",color);
    }

    public void setGradientPosition(Vector2 position){
        program.setUniformf("u_gradient_position",position);
    }

    public void setGradientAngle(float angle){
        program.setUniformf("u_gradient_angle",angle);
    }

    public void setGradientRadius(float radius){
        program.setUniformf("u_gradient_radius",radius);
    }











}
