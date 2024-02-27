package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundMap extends AssetMap<Sound>{
    public SoundMap(String path, Files.FileType fileType) {
        super(path, fileType);
    }

    public SoundMap(FileHandle directory) {
        super(directory);
    }
    public Sound findSound(String name){
        return findAsset(name);
    }
    @Override
    protected Sound findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        Sound sound=Gdx.audio.newSound(directory.child(name+".mp3"));
        map.put(name,sound);
        return sound;
    }
}
