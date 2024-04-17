package library.gdx.lottie;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AndroidLottieAnimationController implements LottieAnimationController{
    private LottieDrawer lottieDrawer;
    private LottieAnimationView animationView;
    private float currentProgress;
    public AndroidLottieAnimationController(LottieDrawer lottieDrawer,String jsonString,float width,float height){
        this.lottieDrawer=lottieDrawer;
        animationView=new LottieAnimationView(lottieDrawer.context);
        animationView.setAnimationFromJson(jsonString,null);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        animationView.setLayoutParams(layoutParams);
        int widthSpec = View.MeasureSpec.makeMeasureSpec((int)width, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec((int)height, View.MeasureSpec.EXACTLY);
        animationView.measure(widthSpec,heightSpec);
        animationView.setLayerType(LottieAnimationView.LAYER_TYPE_NONE,null);
        animationView.buildLayer();
        animationView.buildDrawingCache(true);
//        animationView.layout(0,0,widthSpec,heightSpec);
    }


    @Override
    public Texture getTexture() {
        currentProgress+= Gdx.graphics.getDeltaTime();
        if (currentProgress>0.0f){
            currentProgress=0.0f;
        }
        return lottieDrawer.getTextureFromView(animationView);
    }

    @Override
    public void playAnimation() {
        animationView.playAnimation();
    }

    @Override
    public boolean isActivated() {
        return animationView.isActivated();
    }

    @Override
    public boolean isAnimating() {
        return animationView.isAnimating();
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        animationView.setRepeatMode(repeatMode);
    }

    @Override
    public void setRepeatCount(int repeatCount) {
        animationView.setRepeatCount(repeatCount);
    }
}
