package library.gdx.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;

import library.gdx.shaders.BoxShader;
import library.gdx.shaders.FontShader;

public class ResourceUtils implements Disposable {
    private static ResourceUtils instance;

    public static ResourceUtils getInstance() {
        if (instance == null) {
            instance = new ResourceUtils();
        }
        return instance;
    }

    public static void init(){
        if (instance!=null){
            instance.dispose();
        }
        instance=new ResourceUtils();
    }

    public Assets uiAssets;
    public FontShader fontShader;

    public BoxShader boxShader;

    public Texture pixelTexture;
    public TextureRegion pixelRegion;

    public NinePatchDrawable pixelDrawable;

    public UIColorScheme colorScheme;

    private ResourceUtils() {
        uiAssets = new Assets("ui", Files.FileType.Internal);
        ShaderProgram.pedantic=false;
        fontShader = new FontShader(DistanceFieldFont.createDistanceFieldShader());
        boxShader = new BoxShader(uiAssets.findShader("box"));
        createPixel();
        colorScheme=UIColorScheme.light;
    }

    private void createPixel() {
        Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#FFFFFF"));
        pixmap.fill();
        pixelTexture = new Texture(pixmap);
        pixelTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        pixelRegion = new TextureRegion(pixelTexture);
        pixelDrawable = new NinePatchDrawable(new NinePatch(pixelTexture, 1, 1, 1, 1));
        pixmap.dispose();
    }

    @Override
    public void dispose() {
        uiAssets.dispose();
        fontShader.dispose();
        boxShader.dispose();
        pixelTexture.dispose();
    }
}
