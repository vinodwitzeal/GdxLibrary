package library.gdx.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UIScreenViewport extends Viewport {
    public UIScreenViewport() {
        super();
        setCamera(new OrthographicCamera());
    }


    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        super.update(screenWidth, screenHeight, centerCamera);
    }

    @Override
    public void apply(boolean centerCamera) {
        super.apply(true);
    }
}
