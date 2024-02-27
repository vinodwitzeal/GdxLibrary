package library.gdx.camera;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class CameraController implements CoreCameraController{
    private static CameraController instance;

    public static CameraController getInstance() throws ReflectionException{
        if (instance==null){
            instance=new CameraController();
        }
        return instance;
    }

    private CoreCameraController controller;
    private CameraController() throws ReflectionException{
        Class<? extends CoreCameraController> cameraClass;
        if (Gdx.app.getType()== Application.ApplicationType.Android){
            cameraClass=ClassReflection.forName("library.gdx.camera.AndroidCameraController");
        }else if (Gdx.app.getType()==Application.ApplicationType.iOS){
            cameraClass=ClassReflection.forName("library.gdx.camera.IOSCameraController");
        }else{
            throw new RuntimeException("Application "+Gdx.app.getType().name()+" is not supported");
        }
        controller=ClassReflection.newInstance(cameraClass);
    }

    @Override
    public void setCameraFaceDetectorListener(CameraFaceDetectorListener faceDetectorListener) {
        controller.setCameraFaceDetectorListener(faceDetectorListener);
    }

    @Override
    public void setCameraListener(CameraListener listener) {
        controller.setCameraListener(listener);
    }

    @Override
    public void setCameraFrameListener(CameraFrameListener listener) {
        controller.setCameraFrameListener(listener);
    }

    @Override
    public void onResume() {
        controller.onResume();
    }

    @Override
    public void onPause() {
        controller.onPause();
    }

    @Override
    public boolean openCamera(float width, float height) {
        return controller.openCamera(width,height);
    }

    @Override
    public void closeCamera() {
        controller.closeCamera();
    }

}
