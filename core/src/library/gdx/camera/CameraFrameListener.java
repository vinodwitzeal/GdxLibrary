package library.gdx.camera;

public interface CameraFrameListener {
    void onFrameAvailable(byte[] data,int width,int height,boolean flipImage);
}
