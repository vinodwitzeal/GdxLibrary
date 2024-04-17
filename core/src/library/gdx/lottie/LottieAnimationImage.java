package library.gdx.lottie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import library.gdx.SceneManager;

public class LottieAnimationImage extends Image implements LottieAnimationController{
    private LottieAnimationController controller;
    private Texture texture;
    private String jsonString;
    public LottieAnimationImage(String jsonString){
        this.jsonString=jsonString;
    }

    public LottieAnimationImage(FileHandle fileHandle){
        this(fileHandle.readString());
    }

    @Override
    public void layout() {
        super.layout();
        if (controller==null) {
            SceneManager sceneManager = (SceneManager) Gdx.app.getApplicationListener();
            controller = sceneManager.platformHandler.deviceHandler.createLottieAnimationController(jsonString,getWidth(),getHeight());
        }
    }

    @Override
    public Texture getTexture() {
        if (controller!=null){
            return controller.getTexture();
        }
        return null;
    }

    @Override
    public boolean isActivated() {
        if (controller!=null){
            return controller.isActivated();
        }
        return false;
    }

    @Override
    public void playAnimation() {
        if (controller!=null){
            controller.playAnimation();
        }
    }

    @Override
    public boolean isAnimating() {
        if (controller!=null){
            return controller.isAnimating();
        }
        return false;
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        if (controller!=null){
            controller.setRepeatMode(repeatMode);
        }
    }

    @Override
    public void setRepeatCount(int repeatCount) {
        if (controller!=null){
            controller.setRepeatCount(repeatCount);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Texture currentTexture=getTexture();
        if (currentTexture!=null){
            setDrawable(new TextureRegionDrawable(currentTexture));
            if (texture!=null){
                texture.dispose();
            }
            texture=currentTexture;
        }
    }
}
