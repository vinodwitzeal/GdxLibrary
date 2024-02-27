package library.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.HdpiMode;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import library.gdx.handlers.IOSHandler;
import library.gdx.handlers.PlatformHandler;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useAccelerometer=false;
        config.useCompass=false;
        config.hdpiMode= HdpiMode.Pixels;
        return new IOSApplication(new SceneManager(){
            @Override
            public PlatformHandler createPlatformHandler() {
                IOSHandler iosHandler=new IOSHandler();
                iosHandler.init((IOSApplication) Gdx.app);
                return iosHandler;
            }
        }, config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
