package library.gdx.handlers;

import com.badlogic.gdx.Application;

import library.gdx.files.FileChooser;
import library.gdx.handlers.permissions.PermissionHandler;

public abstract class PlatformHandler {
    public DeviceHandler deviceHandler;
    public PermissionHandler permissionHandler;
    public FileChooser fileChooser;

    public Application application;

    public void init(Application application){
        this.application=application;
        this.deviceHandler=createDeviceHandler();
        this.permissionHandler=createPermissionHandler();
        this.fileChooser=createFileChooser();
    }

    protected abstract DeviceHandler createDeviceHandler();

    protected abstract PermissionHandler createPermissionHandler();

    protected abstract FileChooser createFileChooser();

}
