package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TextureMap extends AssetMap<Texture> {

    protected final Map<String, TextureRegion> regionMap;

    public TextureMap(String path, Files.FileType fileType) {
        super(path, fileType);
        regionMap=new HashMap<>();
    }

    public TextureMap(FileHandle directory) {
        super(directory);
        regionMap=new HashMap<>();
    }

    public TextureRegion findRegion(String name){
        if (regionMap.containsKey(name)){
            return regionMap.get(name);
        }
        Texture texture=findTexture(name);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region=new TextureRegion(texture);
        regionMap.put(name,region);
        return region;
    }

    public Texture findTexture(String name){
        return findAsset(name);
    }

    @Override
    protected Texture findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        Texture texture=new Texture(directory.child(name+".png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        map.put(name,texture);
        return texture;
    }
}
