package library.gdx.handlers;

import android.widget.Toast;

import library.gdx.AndroidLauncher;
import library.gdx.dialogs.NotificationDialog;
import library.gdx.lottie.AndroidLottieAnimationController;
import library.gdx.lottie.LottieAnimationController;
import library.gdx.lottie.LottieDrawer;

public class AndroidDeviceHandler implements DeviceHandler{
    private AndroidLauncher launcher;
    private LottieDrawer lottieDrawer;

    public AndroidDeviceHandler(AndroidLauncher launcher){
        this.launcher=launcher;
        this.lottieDrawer=new LottieDrawer(launcher);
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

    @Override
    public LottieAnimationController createLottieAnimationController(String jsonString, float width, float height) {
        return new AndroidLottieAnimationController(lottieDrawer,jsonString,width,height);
    }
}
