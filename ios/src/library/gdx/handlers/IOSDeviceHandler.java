package library.gdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.uikit.UIView;

import library.gdx.toast.UIToastView;

public class IOSDeviceHandler implements DeviceHandler{
    private IOSApplication application;
    public IOSDeviceHandler(IOSApplication application) {
        this.application=application;
    }

    @Override
    public String getDeviceType() {
        return Gdx.app.getType().name();
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
        DispatchQueue.getMainQueue().async(new Runnable() {
            @Override
            public void run() {
                UIView view=application.getUIViewController().getView();
                UIToastView.showToast(view,null,message,null);
            }
        });
    }

    @Override
    public int getAppVersion() {
        return 0;
    }
}
