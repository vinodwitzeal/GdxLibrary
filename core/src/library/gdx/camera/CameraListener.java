package library.gdx.camera;

public interface CameraListener {
    void onCameraOpened();

    void onCameraDisconnected();

    void onCameraClosed();
    void onError(int error,String message);
}
