package library.gdx.ui.widgets.masked;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

public class MaskedImage extends Image implements Mask.MaskedDrawer {
    private Mask mask;
    public MaskedImage(TextureRegion maskRegion){
        this(new TextureRegionDrawable(maskRegion));
    }

    public MaskedImage(Texture maskTexture){
        this(new TextureRegionDrawable(maskTexture));
    }

    public MaskedImage(Drawable maskedDrawable){
        super();
        this.mask=new Mask(maskedDrawable);
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (mask==null){
            super.draw(batch,parentAlpha);
        }else {
            validate();
            mask.draw(this,batch,parentAlpha);
        }
    }

    @Override
    public void drawContent(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
    }

    @Override
    public Actor getActor() {
        return this;
    }
}
