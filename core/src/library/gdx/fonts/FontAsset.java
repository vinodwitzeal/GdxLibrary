package library.gdx.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class FontAsset implements Disposable {
    private Texture linearTexture,nearestTexture;
    private TextureRegion linearRegion,nearestRegion;

    private FileHandle fontDirectory;
    private BitmapFont.BitmapFontData fontData;
    public FontAsset(FontType fontType){
        fontDirectory=Gdx.files.internal("fonts/"+fontType.value());
        fontData=new BitmapFont.BitmapFontData(fontDirectory.child("font.fnt"),false);

        linearTexture=new Texture(fontDirectory.child("font.png"));
        linearTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nearestTexture=new Texture(linearTexture.getTextureData());
        nearestTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestNearest);

        linearRegion=new TextureRegion(linearTexture);
        nearestRegion=new TextureRegion(nearestTexture);
    }


    public TextureRegion getLinearRegion() {
        return linearRegion;
    }

    public TextureRegion getNearestRegion() {
        return nearestRegion;
    }

    public BitmapFont.BitmapFontData getFontData() {
        return fontData;
    }

    @Override
    public void dispose() {
        linearTexture.dispose();
        nearestTexture.dispose();
    }
}
