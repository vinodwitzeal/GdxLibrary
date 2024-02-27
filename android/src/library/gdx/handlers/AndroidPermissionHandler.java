package library.gdx.handlers;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;

import androidx.core.content.ContextCompat;

import com.badlogic.gdx.backends.android.MultiplePermissionResultListener;
import com.badlogic.gdx.backends.android.PermissionResultListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.gdx.AndroidLauncher;
import library.gdx.handlers.permissions.Permission;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.handlers.permissions.PermissionResult;

public class AndroidPermissionHandler implements PermissionHandler {
    private AndroidLauncher launcher;
    private Map<Permission,String> stringMap;
    private Map<String,Permission> permissionMap;
    public AndroidPermissionHandler(AndroidLauncher launcher) {
        this.launcher=launcher;
        initMap();
    }

    private void initMap(){
        stringMap=new HashMap<>();
        stringMap.put(Permission.Camera,Manifest.permission.CAMERA);
        stringMap.put(Permission.Location_Coarse,Manifest.permission.ACCESS_COARSE_LOCATION);
        stringMap.put(Permission.Location_Fine,Manifest.permission.ACCESS_FINE_LOCATION);
        stringMap.put(Permission.Read_Storage,Manifest.permission.READ_EXTERNAL_STORAGE);
        stringMap.put(Permission.Write_Storage,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        permissionMap=new HashMap<>();
        for (Permission permission:stringMap.keySet()){
            permissionMap.put(stringMap.get(permission),permission);
        }
    }

    @Override
    public void requestPermission(Permission permission, ResultListener listener) {
        if (checkSelfPermission(permission)){
            listener.onResult(permission,PermissionResult.Granted);
        }else {
            launcher.requestPermission(stringMap.get(permission), new PermissionResultListener() {
                @Override
                public void onResult(Boolean result) {
                    listener.onResult(permission,result?PermissionResult.Granted:PermissionResult.Denied);
                }
            });
        }
    }

    @Override
    public void requestPermission(Permission[] permissions, MultipleResultListener listener) {
        Map<Permission,PermissionResult> resultMap=new HashMap<>();
        List<Permission> allPermissions= Arrays.asList(permissions);
        List<Permission> requestPermission=new ArrayList<>();
        for (Permission permission:allPermissions){
            if (checkSelfPermission(permission)){
                resultMap.put(permission,PermissionResult.Granted);
            }else {
                requestPermission.add(permission);
            }
        }

        if (requestPermission.size()>0){
            launcher.requestPermission(convertPermissions(requestPermission), new MultiplePermissionResultListener() {
                @Override
                public void onResult(Map<String, Boolean> map) {
                    for (String permission:map.keySet()){
                        Boolean result=map.get(permission);
                        if (result==null){
                            result=false;
                        }
                        resultMap.put(permissionMap.get(permission),result?PermissionResult.Granted:PermissionResult.Denied);
                    }
                    listener.onResult(resultMap);
                }
            });
        }else{
            listener.onResult(resultMap);
        }


    }

    private boolean checkSelfPermission(Permission permission){
        return checkSelfPermission(stringMap.get(permission));
    }


    private boolean checkSelfPermission(String permission){
        return  ContextCompat.checkSelfPermission(launcher,permission)==PERMISSION_GRANTED;
    }


    private String[] convertPermissions(List<Permission> permissions){
        String[] convertedPermissions=new String[permissions.size()];
        for (int i=0;i<permissions.size();i++){
            convertedPermissions[i]=stringMap.get(permissions.get(i));
        }
        return convertedPermissions;
    }

}
