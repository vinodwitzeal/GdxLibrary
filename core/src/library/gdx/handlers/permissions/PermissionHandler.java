package library.gdx.handlers.permissions;

import java.util.Map;

public interface PermissionHandler {
    void requestPermission(Permission permission,ResultListener listener);
    void requestPermission(Permission[] permissions, MultipleResultListener listener);
    interface MultipleResultListener {
        void onResult(Map<Permission,PermissionResult> resultMap);
    }

    interface ResultListener{
        void onResult(Permission permission,PermissionResult result);
    }
}

