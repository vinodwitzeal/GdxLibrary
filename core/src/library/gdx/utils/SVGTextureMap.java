package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.mazatech.gdx.SVGAssetsGDX;
import com.mazatech.gdx.SVGTexture;

public class SVGTextureMap extends AssetMap<SVGTexture>{
    private SVGAssetsGDX assetsGDX;
    public SVGTextureMap(String path, Files.FileType fileType,SVGAssetsGDX assetsGDX) {
        super(path, fileType);
        this.assetsGDX=assetsGDX;
    }

    public SVGTextureMap(FileHandle directory,SVGAssetsGDX assetsGDX) {
        super(directory);
        this.assetsGDX=assetsGDX;
    }

    @Override
    protected SVGTexture findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        SVGTexture texture=assetsGDX.createTexture(directory.child(name+".svg"));
        map.put(name,texture);
        return texture;
    }
}
