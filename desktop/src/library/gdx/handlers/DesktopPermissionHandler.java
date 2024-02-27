package library.gdx.handlers;

import java.util.HashMap;
import java.util.Map;

import library.gdx.handlers.permissions.Permission;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.handlers.permissions.PermissionResult;

public class DesktopPermissionHandler implements PermissionHandler {

    @Override
    public void requestPermission(Permission permission, ResultListener listener) {
        listener.onResult(permission,PermissionResult.Granted);
    }

    @Override
    public void requestPermission(Permission[] permissions, MultipleResultListener listener) {
        Map<Permission,PermissionResult> resultMap=new HashMap<>();
        for (Permission permission:permissions){
            resultMap.put(permission,PermissionResult.Granted);
        }
        listener.onResult(resultMap);
    }
}
