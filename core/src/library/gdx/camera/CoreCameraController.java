package library.gdx.camera;

import com.badlogic.gdx.Application;

import java.util.HashMap;
import java.util.Map;

public interface CoreCameraController {
    void setCameraListener(CameraListener listener);

    void setCameraFrameListener(CameraFrameListener listener);

    void setCameraFaceDetectorListener(CameraFaceDetectorListener faceDetectorListener);

    void onResume();
    void onPause();

    boolean openCamera(float width,float height);

    void closeCamera();




}
