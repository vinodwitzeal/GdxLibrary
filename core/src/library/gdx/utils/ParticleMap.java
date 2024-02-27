package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleMap extends AssetMap<ParticleEffect>{
    public ParticleMap(String path, Files.FileType fileType) {
        super(path, fileType);
    }

    public ParticleMap(FileHandle directory) {
        super(directory);
    }

    @Override
    protected ParticleEffect findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        ParticleEffect particleEffect=new ParticleEffect();
        particleEffect.load(directory.child(name+".particle"),directory);
        map.put(name,particleEffect);
        return particleEffect;
    }
}
