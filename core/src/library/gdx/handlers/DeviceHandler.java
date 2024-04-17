package library.gdx.handlers;

import library.gdx.lottie.LottieAnimationController;

public interface DeviceHandler {

    String getDeviceType();

    String getDeviceID();

    LottieAnimationController createLottieAnimationController(String jsonString,float width,float height);


    void changeOrientation(int orientation);

    void openNotifications();

    void showToast(String message);

    int getAppVersion();
}
