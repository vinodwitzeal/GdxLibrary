package library.gdx;
import library.gdx.ui.screens.SVGPackerScreen;

public abstract class DesktopSceneManager extends SceneManager {
    @Override
    public void onCreate() {
        platformHandler=createPlatformHandler();
        pushScreen(new SVGPackerScreen(this));
    }
}
