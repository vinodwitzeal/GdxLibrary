package library.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;

import java.awt.Window;

import library.gdx.SceneManager;
import library.gdx.handlers.DesktopHandler;
import library.gdx.handlers.PlatformHandler;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setHdpiMode(HdpiMode.Pixels);
		config.setMaximized(true);
		config.setForegroundFPS(60);
		config.setTitle("SVG Packer");


		new Lwjgl3Application(new DesktopSceneManager(){
			@Override
			public PlatformHandler createPlatformHandler() {
				DesktopHandler desktopHandler=new DesktopHandler();
				desktopHandler.init((Lwjgl3Application) Gdx.app);
				return desktopHandler;
			}
		}, config);
	}
}
