package library.gdx.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.gdx.SceneManager;
import library.gdx.handlers.DeviceHandler;
import library.gdx.handlers.PlatformHandler;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.ui.dialogs.UIDialog;
import library.gdx.utils.BackButtonListener;
import library.gdx.utils.GameUtils;
import library.gdx.utils.ResourceUtils;
import library.gdx.utils.UIColorScheme;

public abstract class UIScreen implements Screen {

    private String name;
    protected SceneManager sceneManager;
    protected PlatformHandler platformHandler;

    protected DeviceHandler deviceHandler;
    protected PermissionHandler permissionHandler;

    protected FitViewport viewport;
    protected Stage stage;

    protected int screenWidth,screenHeight;
    protected int left,top,right,bottom;

    protected float width,height,density;
    protected float sidePad,contentPad;

    protected UIColorScheme colorScheme;

    protected final List<UIDialog> dialogs= Collections.synchronizedList(new ArrayList<>());

    public UIScreen(SceneManager sceneManager,String name){
        this.sceneManager=sceneManager;
        this.name=name;
        platformHandler=this.sceneManager.platformHandler;
        deviceHandler=platformHandler.deviceHandler;
        permissionHandler=platformHandler.permissionHandler;
        colorScheme= ResourceUtils.getInstance().colorScheme;
    }


    protected void updateDimensions(int screenWidth,int screenHeight){
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        left=Gdx.graphics.getSafeInsetLeft();
        top=Gdx.graphics.getSafeInsetTop();
        right=Gdx.graphics.getSafeInsetRight();
        bottom=Gdx.graphics.getSafeInsetBottom();

        width=screenWidth-(left+right);
        height=screenHeight-(top+bottom);
        density= GameUtils.getDensity(width,height);
        sidePad=GameUtils.round(Math.min(width,height))*0.05f;
        contentPad=sidePad*0.5f;
    }


    protected void initStage(){
        updateDimensions(Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight());
        viewport=new FitViewport(width,height);
        stage=new Stage(viewport,sceneManager.getScreenBatch());
        updateViewport();
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new BackButtonListener() {
            @Override
            protected boolean onButtonPressed() {
                return onBackButtonPressed();
            }
        }));
    }

    protected void updateViewport(){
        viewport.setWorldSize(width,height);
        viewport.setScreenBounds(left,bottom,(int)width,(int) height);
        viewport.apply(true);
//        viewport.update(screenWidth,screenHeight,true);
//        viewport.update(screenWidth,screenHeight,false);
    }



    protected abstract void buildUI();

    protected boolean onBackButtonPressed(){
        sceneManager.popScreen();
        return true;
    }


    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public PlatformHandler getPlatformHandler() {
        return platformHandler;
    }

    public DeviceHandler getDeviceHandler() {
        return deviceHandler;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public Stage getStage() {
        return stage;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getDensity() {
        return density;
    }

    public float getSidePad() {
        return sidePad;
    }

    public float getContentPad() {
        return contentPad;
    }


    public void addDialog(UIDialog dialog){
        dialogs.add(dialog);
    }

    public void removeDialog(UIDialog dialog){
        dialogs.remove(dialog);
    }

    public void hideAllDialogs(){
        List<UIDialog> uiDialogs=new ArrayList<>(dialogs);
        for (UIDialog dialog:uiDialogs){
            dialog.hide();
        }
        uiDialogs.clear();
    }

    @Override
    public void show() {
        initStage();
        buildUI();
    }

    protected void renderBackground(){
        ScreenUtils.clear(0.7f,0.7f,0.7f,0.7f,true);
    }

    @Override
    public void render(float delta) {
        renderBackground();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        updateDimensions(width,height);
        updateViewport();
        for (UIDialog dialog:dialogs){
            dialog.resize();
        }
    }

    @Override
    public void pause() {
        for (UIDialog dialog:dialogs){
            dialog.pause();
        }
    }

    @Override
    public void resume() {
        for (UIDialog dialog:dialogs){
            dialog.resume();
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public String toString() {
       return name==null?"":name;
    }
}
