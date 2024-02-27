package library.gdx.camera;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import library.gdx.AndroidLauncher;

public class AndroidCameraController implements CoreCameraController {
    private final String TAG = "AndroidCameraController";

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    private AndroidLauncher launcher;
    private CameraManager cameraManager;


    private DeviceCamera frontCamera, backCamera, currentCamera;
    private boolean faceDetectionSupported = false;

    private boolean processingImage = false;

    private int surfaceRotation;
    private int rotationCompensation;

    private CameraListener cameraListener;
    private CameraFrameListener cameraFrameListener;

    private CameraFaceDetectorListener faceDetectorListener;

    private ByteArrayOutputStream outputStream;

    private CameraDevice mCameraDevice;

    private ImageReader mImageReader;

    private CameraCaptureSession mCaptureSession;


    /**
     * An additional thread for running tasks that shouldn't block the UI.
     */
    private HandlerThread mBackgroundThread;

    /**
     * A {@link Handler} for running tasks in the background.
     */
    private Handler mBackgroundHandler;

    private ImageReader.OnImageAvailableListener onImageAvailableListener=new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireLatestImage();
            if (processingImage || cameraFrameListener == null || image==null){
                if (image!=null){
                    image.close();
                }
                return;
            }
            try {
                CameraFrameListener frameListener = cameraFrameListener;
                processingImage = true;
                int width = image.getWidth();
                int height = image.getHeight();
                Image.Plane imagePlane = image.getPlanes()[0];
                ByteBuffer buffer = imagePlane.getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);

                Matrix matrix = new Matrix();
                matrix.postRotate(rotationCompensation);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                outputStream.reset();
                rotatedBitmap.recycle();
                bitmap.recycle();
                image.close();
                processingImage = false;
                frameListener.onFrameAvailable(byteArray, width, height, flipImage);
            } catch (Exception ignored) {

            }
        }
    };

    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            mCameraOpenCloseLock.release();
            mCameraDevice = cameraDevice;
            if (cameraListener != null) {
                cameraListener.onCameraOpened();
            }
            createCaptureSession(cameraDevice);
        }

        @Override
        public void onClosed(@NonNull CameraDevice camera) {
            super.onClosed(camera);
            if (cameraListener != null) {
                cameraListener.onCameraClosed();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice=null;

        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice=null;
            if (cameraListener != null) {
                cameraListener.onError(error, "");
            }
        }

    };

    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    private boolean flipImage = true;

    public AndroidCameraController() {
        launcher = (AndroidLauncher) Gdx.app;
        cameraManager = (CameraManager) launcher.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            for (String currentCameraId : cameraIdList) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(currentCameraId);
                Integer facingCharacteristics = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (facingCharacteristics != null) {
                    if (facingCharacteristics == CameraCharacteristics.LENS_FACING_BACK) {
                        backCamera = new DeviceCamera();
                        backCamera.cameraId = currentCameraId;
                        backCamera.frontFacing = false;
                    } else if (facingCharacteristics == CameraCharacteristics.LENS_FACING_FRONT) {
                        frontCamera = new DeviceCamera();
                        frontCamera.cameraId = currentCameraId;
                        frontCamera.frontFacing = true;
                    }
                }
            }
        } catch (CameraAccessException exception) {
            exception.printStackTrace();
        }
        outputStream = new ByteArrayOutputStream();
    }


    private void updateDeviceRotation() {
        int deviceRotation = launcher.getWindowManager().getDefaultDisplay().getRotation();
        Log.e(TAG, "Device Rotation=>" + deviceRotation);
        surfaceRotation = ORIENTATIONS.get(deviceRotation);
        Log.e(TAG, "Surface Rotation=>" + surfaceRotation);
    }

    @Override
    public void setCameraListener(CameraListener listener) {
        this.cameraListener = listener;
    }

    @Override
    public void setCameraFrameListener(CameraFrameListener cameraFrameListener) {
        this.cameraFrameListener = cameraFrameListener;
    }

    @Override
    public void setCameraFaceDetectorListener(CameraFaceDetectorListener faceDetectorListener) {
        this.faceDetectorListener = faceDetectorListener;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private int getJpegOrientation(CameraCharacteristics c, int deviceOrientation) {
        if (deviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN) return 0;
        int sensorOrientation = c.get(CameraCharacteristics.SENSOR_ORIENTATION);

        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90;

        // Reverse device orientation for front-facing cameras
        boolean facingFront = c.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront) deviceOrientation = -deviceOrientation;

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        int jpegOrientation = (sensorOrientation + deviceOrientation + 360) % 360;

        return jpegOrientation;
    }

    @Override
    public boolean openCamera(float width, float height) {
        try {
            if (ActivityCompat.checkSelfPermission(launcher, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            startBackgroundThread();
            float imageWidth=512.0f;
            float imageHeight;
            if (width>imageWidth){
                imageHeight=height*imageWidth/width;
            }else {
                imageHeight=height;
            }
            mImageReader = ImageReader.newInstance((int)imageWidth,(int)imageHeight, ImageFormat.JPEG, 2);
            mImageReader.setOnImageAvailableListener(onImageAvailableListener, mBackgroundHandler);
            updateDeviceRotation();
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(frontCamera.cameraId);
            int[] faceModes = cameraCharacteristics.get(CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES);
            faceDetectionSupported = faceModes != null && faceModes.length > 1;
            if (faceDetectorListener != null) {
                faceDetectorListener.onFaceDetectorAvailable(faceDetectionSupported);
            }
            int sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            Log.e(TAG, "Sensor Rotation=>" + sensorOrientation);
            rotationCompensation = (surfaceRotation + sensorOrientation + 270) % 360;
            Log.e(TAG, "Rotation Compensation=>" + rotationCompensation);

            if (frontCamera != null) {
                currentCamera = frontCamera;
                flipImage = true;
            } else if (backCamera != null) {
                currentCamera = backCamera;
                flipImage = false;
            } else {
                return false;
            }

            try {
                if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                    throw new RuntimeException("Time out waiting to lock camera opening.");
                }
                cameraManager.openCamera(currentCamera.cameraId, mStateCallback, mBackgroundHandler);
            } catch (CameraAccessException | InterruptedException e) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void createCaptureSession(CameraDevice cameraDevice){
        try {
            List<Surface> surfaces = new ArrayList<>();
            surfaces.add(mImageReader.getSurface());
            cameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    if (mCameraDevice==null){
                        return;
                    }
                    mCaptureSession=session;
                    onCameraConfigured(mCaptureSession);

                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                    if (cameraListener != null) {
                        cameraListener.onError(0, "Configuration Failed");
                    }
                }
            }, null);
        }catch (Exception ignored){

        }

    }

    private void onCameraConfigured(CameraCaptureSession session){
        try {
            CaptureRequest.Builder captureRequestBuilder = session.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(mImageReader.getSurface());
            if (faceDetectionSupported)
                captureRequestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, CameraMetadata.STATISTICS_FACE_DETECT_MODE_SIMPLE);
//        captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, rotationCompensation);
            session.setRepeatingRequest(captureRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    if (faceDetectorListener == null || processingImage) return;
                    Face[] faces = result.get(CaptureResult.STATISTICS_FACES);
                    if (faces != null && faces.length == 1) {
                        Face face = faces[0];
                        faceDetectorListener.onFaceDetected(face.getScore());
                    } else {
                        faceDetectorListener.onFaceDetected(0);
                    }

                }
            }, null);
        }catch (Exception ignored){

        }

    }

    @Override
    public void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (null != mCaptureSession) {
                mCaptureSession.close();
                mCaptureSession = null;
            }
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mImageReader) {
                mImageReader.close();
                mImageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
        stopBackgroundThread();
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        if (mBackgroundThread==null)return;
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
