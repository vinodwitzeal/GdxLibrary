package library.gdx.handlers;

public interface DeviceHandler {

    String getDeviceType();

    String getDeviceID();



    void changeOrientation(int orientation);

    void showToast(String message);

    int getAppVersion();
}
