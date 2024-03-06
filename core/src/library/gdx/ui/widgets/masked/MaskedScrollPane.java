package library.gdx.ui.widgets.masked;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MaskedScrollPane extends ScrollPane implements Mask.MaskedDrawer {
    private Mask mask;

    public MaskedScrollPane(TextureRegion maskRegion,Actor actor){
        this(maskRegion,actor,new ScrollPaneStyle());
    }
    public MaskedScrollPane(TextureRegion maskRegion,Actor actor,ScrollPaneStyle style){
        this(new TextureRegionDrawable(maskRegion),actor,style);
    }


    public MaskedScrollPane(Texture maskTexture,Actor actor){
        this(maskTexture,actor,new ScrollPaneStyle());
    }

    public MaskedScrollPane(Texture maskTexture,Actor actor,ScrollPaneStyle style){
        this(new TextureRegionDrawable(maskTexture),actor,style);
    }

    public MaskedScrollPane(Drawable maskedDrawable,Actor actor){
        this(maskedDrawable,actor,new ScrollPaneStyle());
    }

    public MaskedScrollPane(Drawable maskedDrawable,Actor actor,ScrollPaneStyle style){
        super(actor,style);
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
