package library.gdx.ui.widgets.masked;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

public class Mask {
    private Drawable drawable;
    public Mask(Drawable drawable){
        this.drawable=drawable;
    }

    public void draw(MaskedDrawer drawer, Batch batch, float parentAlpha) {
        Actor actor=drawer.getActor();
        if (actor==null)return;
        if (drawable==null){
            drawer.drawContent(batch,parentAlpha);
        }else {
            drawMask(drawer,actor,batch,parentAlpha);
            drawMasked(drawer,batch,parentAlpha);
        }
    }


    private void drawMask(MaskedDrawer drawer,Actor actor, Batch batch, float parentAlpha){
        batch.flush();
        Gdx.gl.glColorMask(false, false, false, true);
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);

        batch.setColor(1,1,1,1);

        float x = actor.getX();
        float y = actor.getY();
        float scaleX = actor.getScaleX();
        float scaleY = actor.getScaleY();

        float rotation = actor.getRotation();
        if (drawable instanceof TransformDrawable && (scaleX != 1 || scaleY != 1 || rotation != 0)) {
            ((TransformDrawable)drawable).draw(batch, x, y, actor.getOriginX(), actor.getOriginY(),
                    actor.getWidth(), actor.getHeight(), scaleX, scaleY, rotation);
        }else {
            drawable.draw(batch, x, y, actor.getWidth() * scaleX, actor.getHeight() * scaleY);
        }
        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);
        drawer.drawContent(batch,parentAlpha);
        batch.flush();
    }

    private void drawMasked(MaskedDrawer drawer,Batch batch,float parentAlpha){
        Gdx.gl.glColorMask(true, true, true, true);

        /* Change the blending function so the rendered pixels alpha blend with our alpha map. */
        batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);

        /* Draw our sprite to be masked. */
        drawer.drawContent(batch,parentAlpha);

        /* Remember to flush before changing GL states again. */
        batch.flush();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
    }


    public interface MaskedDrawer {
        void drawContent(Batch batch,float parentAlpha);
        Actor getActor();
    }
}
