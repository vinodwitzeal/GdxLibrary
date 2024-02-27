package library.gdx.camera;

public interface CameraFaceDetectorListener {

    void onFaceDetectorAvailable(boolean faceDetectorAvailable);
    void onFaceDetected(int score);
}
