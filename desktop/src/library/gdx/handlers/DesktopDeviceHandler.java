package library.gdx.handlers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

public class DesktopDeviceHandler implements DeviceHandler{
    @Override
    public String getDeviceType() {
        return Application.ApplicationType.Desktop.name();
    }

    @Override
    public String getDeviceID() {
        return "";
    }

    @Override
    public void changeOrientation(int orientation) {
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public int getAppVersion() {
        return Gdx.app.getVersion();
    }

    @Override
    public void openNotifications() {

    }
}
