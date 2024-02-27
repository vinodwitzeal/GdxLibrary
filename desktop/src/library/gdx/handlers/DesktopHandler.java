package library.gdx.handlers;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;

import library.gdx.files.DesktopFileChooser;
import library.gdx.files.FileChooser;
import library.gdx.handlers.permissions.PermissionHandler;

public class DesktopHandler extends PlatformHandler{

    public DesktopHandler(){

    }


    @Override
    protected DeviceHandler createDeviceHandler() {
        return new DesktopDeviceHandler();
    }

    @Override
    protected PermissionHandler createPermissionHandler() {
        return new DesktopPermissionHandler();
    }

    @Override
    protected FileChooser createFileChooser() {
        return new DesktopFileChooser((Lwjgl3Application)application);
    }
}
