package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import com.mazatech.gdx.SVGAssetsGDX;
import com.mazatech.svgt.SVGDocument;

public class SVGDocumentMap extends AssetMap<SVGDocument> {
    private SVGAssetsGDX assetsGDX;
    public SVGDocumentMap(String path, Files.FileType fileType,SVGAssetsGDX assetsGDX) {
        super(path, fileType);
        this.assetsGDX=assetsGDX;
    }

    public SVGDocumentMap(FileHandle directory,SVGAssetsGDX assetsGDX) {
        super(directory);
        this.assetsGDX=assetsGDX;
    }

    @Override
    protected SVGDocument findAsset(String name) {
        if (map.containsKey(name)){
            return map.get(name);
        }
        SVGDocument document=assetsGDX.createDocument(directory.child(name+".svg"));
        map.put(name,document);
        return document;
    }
}
