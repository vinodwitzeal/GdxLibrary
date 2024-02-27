package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public abstract class AssetMap<T extends Disposable> implements Disposable{
    protected FileHandle directory;
    protected Map<String,T> map;

    public AssetMap(String path, Files.FileType fileType){
        this(Gdx.files.getFileHandle(path,fileType));
    }

    public AssetMap(FileHandle directory){
        this.directory=directory;
        map =new HashMap<>();
    }

    protected abstract T findAsset(String name);

    @Override
    public void dispose() {
    }
}
