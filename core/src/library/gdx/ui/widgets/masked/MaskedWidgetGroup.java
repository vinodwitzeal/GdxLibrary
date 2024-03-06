package library.gdx.ui.widgets.masked;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MaskedWidgetGroup extends WidgetGroup implements Mask.MaskedDrawer {
    private Mask mask;
    public MaskedWidgetGroup(TextureRegion maskRegion){
        this(new TextureRegionDrawable(maskRegion));
    }

    public MaskedWidgetGroup(Texture maskTexture){
        this(new TextureRegionDrawable(maskTexture));
    }

    public MaskedWidgetGroup(Drawable maskedDrawable){
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
