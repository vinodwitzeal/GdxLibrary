package library.gdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import org.robovm.apple.avfoundation.AVAuthorizationStatus;
import org.robovm.apple.avfoundation.AVCaptureDevice;
import org.robovm.apple.avfoundation.AVMediaType;
import org.robovm.apple.corelocation.CLLocationManager;
import org.robovm.objc.block.VoidBooleanBlock;

import java.util.HashMap;
import java.util.Map;

import library.gdx.handlers.permissions.Permission;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.handlers.permissions.PermissionResult;

public class IOSPermissionHandler implements PermissionHandler {
    private IOSApplication application;
    public IOSPermissionHandler(IOSApplication application) {
        application=(IOSApplication) Gdx.app;
    }


    @Override
    public void requestPermission(Permission permission, ResultListener listener) {
        requestPermission(permission, new VoidBooleanBlock() {
            @Override
            public void invoke(boolean v) {
                listener.onResult(permission,v?PermissionResult.Granted:PermissionResult.Denied);
            }
        });
    }

    @Override
    public void requestPermission(Permission[] permissions, MultipleResultListener listener) {
        Map<Permission, PermissionResult> resultMap=new HashMap<>();
        requestPermission(0,permissions,resultMap,listener);
    }


    private void requestPermission(int index, Permission[] permissions, Map<Permission,PermissionResult> resultMap, MultipleResultListener listener){
        Permission permission=permissions[index];
        requestPermission(permission, new VoidBooleanBlock() {
            @Override
            public void invoke(boolean v) {
                resultMap.put(permission,v?PermissionResult.Granted:PermissionResult.Denied);
                if (index+1<permissions.length){
                    requestPermission(index+1,permissions,resultMap,listener);
                }else {
                    listener.onResult(resultMap);
                }
            }
        });
    }


    private void requestPermission(Permission permission,VoidBooleanBlock handler){
        switch (permission){
            case Camera:
                requestCamera(handler);
                break;
            case Read_Storage:
                break;
            case Write_Storage:
                break;
            case Location_Coarse:
            case Location_Fine:
                requestLocation(handler);
                break;
        }
    }

    private void requestCamera(VoidBooleanBlock handler){
        AVAuthorizationStatus authorizationStatus=AVCaptureDevice.getAuthorizationStatusForMediaType(AVMediaType.Video);
        switch (authorizationStatus){
            case Authorized:
                handler.invoke(true);
                break;
            case NotDetermined:
                AVCaptureDevice.requestAccessForMediaType(AVMediaType.Video,handler);
                break;
            default:
                handler.invoke(false);
                break;

        }
    }
    private void requestLocation(VoidBooleanBlock handler){
        if (!CLLocationManager.isLocationServicesEnabled()) {
            handler.invoke(false);
            return;
        }
        CLLocationManager locationManager=new CLLocationManager();
        switch (locationManager.authorizationStatus()){
            case AuthorizedWhenInUse:
                handler.invoke(true);
                return;
            case AuthorizedAlways:
                handler.invoke(true);
            case NotDetermined:
                locationManager.setDelegate(new IOSLocationHandler(handler));
                locationManager.requestWhenInUseAuthorization();
                break;
            default:
                handler.invoke(false);
                break;
        }
    }
}
