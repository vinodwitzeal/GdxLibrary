package library.gdx.camera;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import org.robovm.apple.avfoundation.AVCaptureConnection;
import org.robovm.apple.avfoundation.AVCaptureDevice;
import org.robovm.apple.avfoundation.AVCaptureDeviceInput;
import org.robovm.apple.avfoundation.AVCaptureDevicePosition;
import org.robovm.apple.avfoundation.AVCaptureDeviceType;
import org.robovm.apple.avfoundation.AVCaptureInput;
import org.robovm.apple.avfoundation.AVCaptureOutput;
import org.robovm.apple.avfoundation.AVCaptureSession;
import org.robovm.apple.avfoundation.AVCaptureSessionPreset;
import org.robovm.apple.avfoundation.AVCaptureVideoDataOutput;
import org.robovm.apple.avfoundation.AVCaptureVideoDataOutputSampleBufferDelegate;
import org.robovm.apple.avfoundation.AVMediaType;
import org.robovm.apple.avfoundation.AVVideoSettings;
import org.robovm.apple.coreimage.CIContext;
import org.robovm.apple.coreimage.CIContextOptions;
import org.robovm.apple.coreimage.CIDetector;
import org.robovm.apple.coreimage.CIDetectorAccuracy;
import org.robovm.apple.coreimage.CIDetectorOptions;
import org.robovm.apple.coreimage.CIDetectorType;
import org.robovm.apple.coreimage.CIImage;
import org.robovm.apple.coremedia.CMSampleBuffer;
import org.robovm.apple.corevideo.CVPixelBuffer;
import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.dispatch.DispatchQueueAttr;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.imageio.CGImagePropertyOrientation;
import org.robovm.apple.uikit.UIImage;
import org.robovm.apple.uikit.UIImageOrientation;
import org.robovm.apple.vision.VNDetectBarcodesRequest;
import org.robovm.apple.vision.VNDetectFaceCaptureQualityRequest;
import org.robovm.apple.vision.VNDetectFaceLandmarksRequest;
import org.robovm.apple.vision.VNDetectFaceRectanglesRequest;
import org.robovm.apple.vision.VNFaceObservation;
import org.robovm.apple.vision.VNImageOption;
import org.robovm.apple.vision.VNImageRequestHandler;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class IOSCameraController extends NSObject implements CoreCameraController, AVCaptureVideoDataOutputSampleBufferDelegate {
    private IOSApplication application;

    private AVCaptureSession captureSession;
    private AVCaptureDevice captureDevice;

    private CameraListener cameraListener;
    private CameraFrameListener frameListener;
    private CameraFaceDetectorListener faceDetectorListener;
    private boolean processingFrame = false;
    private boolean detectingFace=false;
    private Semaphore openCloseLock = new Semaphore(1);
    private DispatchQueue backgroundQueue = DispatchQueue.create("Camera Executor", DispatchQueueAttr.Concurrent());
    private DispatchQueue outputQueue = DispatchQueue.create("Camera Preview", DispatchQueueAttr.Concurrent());

    private VNDetectFaceCaptureQualityRequest faceLandmarksRequest;
    private VNImageRequestHandler imageRequestHandler;

    private VNImageOption imageOption;

    public IOSCameraController() {
        application = (IOSApplication) Gdx.app;
    }

    @Override
    public void setCameraListener(CameraListener listener) {
        this.cameraListener = listener;
    }

    @Override
    public void setCameraFrameListener(CameraFrameListener listener) {
        this.frameListener = listener;
    }

    @Override
    public void setCameraFaceDetectorListener(CameraFaceDetectorListener faceDetectorListener) {
        this.faceDetectorListener=faceDetectorListener;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public boolean openCamera(float width, float height) {
        Gdx.app.error("IOSCameraController","Open");
        boolean cameraStarted=false;
        try {
            if (!openCloseLock.tryAcquire(2500,TimeUnit.MILLISECONDS)){
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
           cameraStarted=startCamera((long) width,(long) height);
        } catch (InterruptedException e) {
            if (cameraListener!=null){
                cameraListener.onError(0,e.getMessage());
            }
        }finally {
            openCloseLock.release();
        }
        return cameraStarted;
    }

    private boolean startCamera(long width,long height){
        imageOption=new VNImageOption();
        imageOption.set(new NSString(""),new NSString(""));


        captureSession = new AVCaptureSession();
        captureSession.beginConfiguration();
        if (captureSession.canSetSessionPreset(AVCaptureSessionPreset.Size640x480)) {
            captureSession.setSessionPreset(AVCaptureSessionPreset.Size640x480);
        }
        captureSession.setAutomaticallyConfiguresCaptureDeviceForWideColor(true);

        captureDevice = AVCaptureDevice.getDefaultDevice(AVCaptureDeviceType.BuiltInWideAngleCamera(), AVMediaType.Video, AVCaptureDevicePosition.Front);
        try {
            AVCaptureDeviceInput input = new AVCaptureDeviceInput(captureDevice);
            captureSession.addInput(input);
        } catch (NSErrorException e) {
            return false;
        }

        AVCaptureVideoDataOutput videoDataOutput = new AVCaptureVideoDataOutput();
        videoDataOutput.setAlwaysDiscardsLateVideoFrames(true);
        videoDataOutput.automaticallyConfiguresOutputBufferDimensions();
        videoDataOutput.setSampleBufferDelegate(this, outputQueue);
        captureSession.addOutput(videoDataOutput);

        captureSession.commitConfiguration();
        captureSession.startRunning();


        if (faceDetectorListener!=null){
            faceDetectorListener.onFaceDetectorAvailable(true);
        }
        if (cameraListener != null) {
            cameraListener.onCameraOpened();
        }
        return true;
    }

    private void clearSession() {
        if (captureSession != null && captureSession.isRunning()) {
            captureSession.stopRunning();
            for (AVCaptureInput input:captureSession.getInputs()){
                input.dispose();
            }
            for (AVCaptureOutput output:captureSession.getOutputs()){
                output.dispose();
            }
            captureSession.dispose();
        }
        captureSession = null;
        if (captureDevice != null) {
            captureDevice.dispose();
        }
        captureDevice = null;
    }

    @Override
    public void closeCamera() {
        try {
            openCloseLock.acquire();
            clearSession();
        }catch (Exception e){

        }finally {
            openCloseLock.release();
        }
        if (cameraListener!=null){
            cameraListener.onCameraClosed();
        }
    }

    @Override
    public void didOutputSampleBuffer(AVCaptureOutput output, CMSampleBuffer sampleBuffer, AVCaptureConnection connection) {
        if (processingFrame || frameListener == null) {
            sampleBuffer.dispose();
            return;
        }
        if (sampleBuffer != null) {
            try {
                processingFrame = true;
                CVPixelBuffer imageBuffer = sampleBuffer.getPixelBuffer();
                CIImage cgImage = new CIImage(imageBuffer);
                UIImage uiImage = new UIImage(cgImage, 1.0f, UIImageOrientation.LeftMirrored);
                NSData nsBuffer = uiImage.toJPEGData(0.1);
                frameListener.onFrameAvailable(nsBuffer.getBytes(), (int) imageBuffer.getWidth(), (int) imageBuffer.getHeight(), false);
                detectFace(imageBuffer);
                sampleBuffer.dispose();
                nsBuffer.dispose();
                uiImage.dispose();
                cgImage.dispose();
                processingFrame = false;
            } catch (Exception e) {
                processingFrame = false;
            }
        }
    }

    @Override
    public void didDropSampleBuffer(AVCaptureOutput output, CMSampleBuffer sampleBuffer, AVCaptureConnection connection) {
        if (sampleBuffer != null) {
            sampleBuffer.dispose();
        }
    }


    private void detectFace(CVPixelBuffer pixelBuffer){
        backgroundQueue.async(new Runnable() {
            @Override
            public void run() {
                if (detectingFace || faceDetectorListener==null){
                    pixelBuffer.dispose();
                    return;
                }
                detectingFace=true;
                faceLandmarksRequest=new VNDetectFaceCaptureQualityRequest();
                imageRequestHandler=new VNImageRequestHandler(pixelBuffer,imageOption);
                try {
                    imageRequestHandler.performRequests(new NSArray<>(faceLandmarksRequest));
                    handleFaceDetectionResults(faceLandmarksRequest.getResults(),pixelBuffer);
                }catch (Exception e){
                    clearDetectionQueue();
                    pixelBuffer.dispose();
                }
            }
        });
    }

    private void clearDetectionQueue(){
        detectingFace=false;
        if (imageRequestHandler!=null) {
            imageRequestHandler.dispose();
        }
        if (faceLandmarksRequest!=null) {
            faceLandmarksRequest.dispose();
        }
        imageRequestHandler=null;
        faceLandmarksRequest=null;
    }

    private void handleFaceDetectionResults(NSArray<VNFaceObservation> faceObservations,CVPixelBuffer pixelBuffer){
        if (faceDetectorListener==null || faceObservations==null || faceObservations.size()==0){
            clearDetectionQueue();
            pixelBuffer.dispose();
            if (faceDetectorListener!=null){
                faceDetectorListener.onFaceDetected(0);
            }
            return;
        }
        try {
            VNFaceObservation faceObservation=faceObservations.first();
            if (faceObservation.getFaceCaptureQuality()!=null){
                double faceQuality=faceObservation.getFaceCaptureQuality().doubleValue();
                faceDetectorListener.onFaceDetected((int)(faceQuality*200));
            }else {
                faceDetectorListener.onFaceDetected(0);
            }
        }catch (Exception ignored){

        }

        for (VNFaceObservation observation:faceObservations){
            observation.dispose();
        }
        pixelBuffer.dispose();
        clearDetectionQueue();
    }
}
