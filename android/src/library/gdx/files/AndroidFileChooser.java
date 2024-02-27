package library.gdx.files;

import java.util.Map;

import library.gdx.AndroidLauncher;
import library.gdx.handlers.AndroidPermissionHandler;
import library.gdx.handlers.permissions.Permission;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.handlers.permissions.PermissionResult;

public class AndroidFileChooser implements FileChooser{
    private AndroidLauncher launcher;
    private AndroidPermissionHandler permissionHandler;
    public AndroidFileChooser(AndroidLauncher launcher, AndroidPermissionHandler permissionHandler){
        this.launcher=launcher;
        this.permissionHandler=permissionHandler;
    }


    @Override
    public void open(FileOptions options, FileChooserCallback callback) {
        permissionHandler.requestPermission(new Permission[]{Permission.Read_Storage}, new PermissionHandler.MultipleResultListener() {
            @Override
            public void onResult(Map<Permission, PermissionResult> resultMap) {
                if (resultMap.get(Permission.Read_Storage)==PermissionResult.Granted){
                    openFileChooser(options,callback);
                }
            }
        });
    }

    private void openFileChooser(FileOptions options,FileChooserCallback callback){

    }
}
