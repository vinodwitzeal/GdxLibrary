package library.gdx;

import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.utils.SharedLibraryLoader;

import library.gdx.handlers.PlatformHandler;
import library.gdx.handlers.SceneHandler;
import library.gdx.ui.screens.HomeScreen;

public abstract class SceneManager extends SceneHandler {
	public PlatformHandler platformHandler;
	public abstract PlatformHandler createPlatformHandler();

	@Override
	public void onCreate() {
		platformHandler=createPlatformHandler();
		pushScreen(new HomeScreen(this));
	}
}
