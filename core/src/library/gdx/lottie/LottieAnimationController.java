package library.gdx.lottie;

import com.badlogic.gdx.graphics.Texture;

public interface LottieAnimationController {
    Texture getTexture();

    boolean isActivated();
    void playAnimation();

    boolean isAnimating();

    void setRepeatMode(int repeatMode);

    void setRepeatCount(int repeatCount);

}
