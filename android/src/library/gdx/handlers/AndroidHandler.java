package library.gdx.handlers;

import library.gdx.AndroidLauncher;
import library.gdx.files.AndroidFileChooser;
import library.gdx.files.FileChooser;
import library.gdx.handlers.permissions.PermissionHandler;

public class AndroidHandler extends PlatformHandler{
    public AndroidHandler(AndroidLauncher launcher) {
        init(launcher);
    }

    @Override
    protected DeviceHandler createDeviceHandler() {
        return new AndroidDeviceHandler((AndroidLauncher) application);
    }

    @Override
    protected PermissionHandler createPermissionHandler() {
        return new AndroidPermissionHandler((AndroidLauncher) application);
    }

    @Override
    protected FileChooser createFileChooser() {
        return new AndroidFileChooser((AndroidLauncher) application,(AndroidPermissionHandler) permissionHandler);
    }
}
