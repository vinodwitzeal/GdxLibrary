package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderMap extends AssetMap<ShaderProgram>{
    public ShaderMap(String path, Files.FileType fileType) {
        super(path, fileType);
    }

    public ShaderMap(FileHandle directory) {
        super(directory);
    }


    public ShaderProgram findShader(String name){
        return findAsset(name);
    }

    @Override
    protected ShaderProgram findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        FileHandle shaderHandle=directory.child("/"+name);
        ShaderProgram.pedantic=false;
        ShaderProgram shaderProgram=new ShaderProgram(shaderHandle.child("/shader.vert"),shaderHandle.child("/shader.frag"));
        if (!shaderProgram.isCompiled()){
            Gdx.app.error("Shader Log"+name,shaderProgram.getLog());
        }
        map.put(name,shaderProgram);
        return shaderProgram;
    }
}
