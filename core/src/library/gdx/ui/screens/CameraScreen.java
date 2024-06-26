package library.gdx.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import library.gdx.SceneManager;
import library.gdx.camera.CameraController;
import library.gdx.camera.CameraFaceDetectorListener;
import library.gdx.camera.CameraFrameListener;
import library.gdx.camera.CameraListener;
import library.gdx.handlers.permissions.Permission;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.handlers.permissions.PermissionResult;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.styles.Styles;

public class CameraScreen extends UIScreen implements CameraListener, CameraFrameListener, CameraFaceDetectorListener {
    private Image cameraImage;

    private int maxScore=0;
    private UILabel scoreLabel;
    private CameraController cameraController;
    private Texture previewTexture;
    private TextureRegion previewRegion;
    private Pixmap previewPixmap;
    private Runnable frameRunnable;
    public CameraScreen(SceneManager sceneManager) {
        super(sceneManager, "CameraScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable=new Table();
        mainTable.setFillParent(true);


        cameraImage=new Image();

        float imageSize=width*0.75f;

        mainTable.add(cameraImage).width(imageSize).height(imageSize);
        mainTable.row();

        scoreLabel=new UILabel("",Styles.label.medium);
        mainTable.add(scoreLabel).pad(4*density);
        mainTable.row();

        Table buttonTable=new Table();

        UITextButton cameraButton=new UITextButton("Capture", Styles.button.contained);
        UITextButton closeCamera=new UITextButton("Stop",Styles.button.text);

        buttonTable.add(cameraButton).uniform().padRight(sidePad);
        buttonTable.add(closeCamera).uniform().padLeft(sidePad);

        mainTable.add(buttonTable);




        cameraButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                Gdx.app.error("CameraScreen","Capture Button Clicked");
                permissionHandler.requestPermission(Permission.Camera, new PermissionHandler.ResultListener() {
                    @Override
                    public void onResult(Permission permission, PermissionResult result) {
                        if (result==PermissionResult.Granted){
                            try {
                                cameraController=CameraController.getInstance();
                            }catch (Exception e){
                                Gdx.app.error("Exception",e.getMessage());
                                e.printStackTrace();
                            }
                            if (cameraController!=null){
                                cameraController.setCameraListener(CameraScreen.this);
                                cameraController.openCamera(imageSize,imageSize);
                            }
                        }else {

                        }
                    }
                });
            }
        });

        closeCamera.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (cameraController!=null){
                    cameraController.closeCamera();
                    cameraController=null;
                }
            }
        });

        stage.addActor(mainTable);
    }


    @Override
    public void onFrameAvailable(byte[] data, int width, int height, boolean flipImage) {
        frameRunnable = new Runnable() {
            @Override
            public void run() {
                if (data == null || data.length == 0) return;
                Pixmap currentPixmap = null;
                try {
                    currentPixmap = new Pixmap(data, 0, data.length);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }

                if (currentPixmap == null) return;

                if (previewTexture != null) {
                    previewTexture.dispose();
                }

                if (previewPixmap != null) {
                    previewPixmap.dispose();
                }
                previewPixmap = currentPixmap;
                previewTexture = new Texture(previewPixmap);
                previewRegion = new TextureRegion(previewTexture);
                previewRegion.flip(flipImage, false);
//                if (cameraController.getFrontCamera()!=null && cameraController.getFrontCamera().frontFacing) {
//                    previewRegion.flip(true, false);
//                }
                cameraImage.setDrawable(new TextureRegionDrawable(previewRegion));
            }
        };
    }

    @Override
    public void onFaceDetectorAvailable(boolean faceDetectorAvailable) {
    }

    @Override
    public void onFaceDetected(int score) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                maxScore=Math.max(score,maxScore);
                scoreLabel.setText("SCORE : "+score +"\nMAX SCORE : "+maxScore);
            }
        });
    }

    @Override
    public void onCameraOpened() {
        if (cameraController!=null){
            cameraController.setCameraFrameListener(this);
            cameraController.setCameraFaceDetectorListener(this);
        }
    }

    @Override
    public void onCameraDisconnected() {

    }

    @Override
    public void onCameraClosed() {

    }

    @Override
    public void onError(int error, String message) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (frameRunnable!=null){
            frameRunnable.run();
        }
        frameRunnable=null;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (cameraController!=null){
            cameraController.closeCamera();
        }
    }
}
