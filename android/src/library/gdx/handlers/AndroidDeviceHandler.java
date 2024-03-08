package library.gdx.handlers;

import android.widget.Toast;

import library.gdx.AndroidLauncher;
import library.gdx.dialogs.NotificationDialog;

public class AndroidDeviceHandler implements DeviceHandler{
    private AndroidLauncher launcher;

    public AndroidDeviceHandler(AndroidLauncher launcher){
        this.launcher=launcher;
    }

    @Override
    public String getDeviceType() {
        return null;
    }

    @Override
    public String getDeviceID() {
        return null;
    }

    @Override
    public void changeOrientation(int orientation) {
    }

    @Override
    public void showToast(String message) {
        launcher.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(launcher, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void openNotifications() {
        launcher.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new NotificationDialog(launcher).show();
            }
        });
    }

    @Override
    public int getAppVersion() {
        return 0;
    }
}
