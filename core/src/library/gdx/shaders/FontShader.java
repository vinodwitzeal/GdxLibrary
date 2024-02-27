package library.gdx.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class FontShader extends Shader {
    public FontShader(ShaderProgram shaderProgram) {
        super(shaderProgram);
        if (!shaderProgram.isCompiled()){
            Gdx.app.error("Font Shader",shaderProgram.getLog());
        }
    }

    public void setSmoothing(float smoothing){
        program.setUniformf("u_smoothing",smoothing);
    }
}
