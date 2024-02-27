package library.gdx.shaders;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;

public class Shader implements Disposable {
    protected ShaderProgram program;
    protected ShaderProgram batchShader;
    protected Shader(ShaderProgram shaderProgram){
        this.program =shaderProgram;
    }

    public void begin(Batch batch){
        batchShader=batch.getShader();
        batch.flush();
        batch.setShader(program);
    }

    public void end(Batch batch){
        batch.flush();
        batch.setShader(batchShader);
    }

    @Override
    public void dispose() {
        if (program !=null){
            program.dispose();
        }
    }
}
