package library.gdx.handlers;

public interface DeviceHandler {

    String getDeviceType();

    String getDeviceID();



    void changeOrientation(int orientation);

    void openNotifications();

    void showToast(String message);

    int getAppVersion();
}
