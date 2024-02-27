package library.gdx.handlers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import library.gdx.files.FileChooser;
import library.gdx.files.IOSFileChooser;
import library.gdx.handlers.permissions.PermissionHandler;

public class IOSHandler extends PlatformHandler{
    @Override
    public void init(Application application) {
        super.init(application);
    }

    @Override
    protected DeviceHandler createDeviceHandler() {
        return new IOSDeviceHandler((IOSApplication)application);
    }

    @Override
    protected PermissionHandler createPermissionHandler() {
        return new IOSPermissionHandler((IOSApplication)application);
    }

    @Override
    protected FileChooser createFileChooser() {
        return new IOSFileChooser((IOSApplication)application);
    }
}
