package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class MusicMap extends AssetMap<Music> {
    public MusicMap(String path, Files.FileType fileType) {
        super(path, fileType);
    }

    public MusicMap(FileHandle directory) {
        super(directory);
    }
    public Music findMusic(String name){
        return findAsset(name);
    }
    @Override
    protected Music findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        Music music=Gdx.audio.newMusic(directory.child(name+".mp3"));
        map.put(name,music);
        return music;

    }
}
