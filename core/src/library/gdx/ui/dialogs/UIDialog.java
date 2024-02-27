package library.gdx.ui.dialogs;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.SnapshotArray;

import library.gdx.SceneManager;
import library.gdx.handlers.DeviceHandler;
import library.gdx.handlers.PlatformHandler;
import library.gdx.handlers.permissions.PermissionHandler;
import library.gdx.ui.screens.UIScreen;
import library.gdx.ui.textfield.UITextField;
import library.gdx.utils.ResourceUtils;

public abstract class UIDialog extends Group {

    protected UIScreen screen;
    protected SceneManager sceneManager;
    protected PlatformHandler platformHandler;

    protected DeviceHandler deviceHandler;
    protected PermissionHandler permissionHandler;

    protected float width,height,density;
    protected float sidePad,contentPad;

    private Actor previousKeyboardFocus, previousScrollFocus;
    private final FocusListener focusListener;

    private Actor content;

    private Drawable background;

    protected InputListener ignoreTouchDown = new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            event.cancel();
            return false;
        }
    };

    public UIDialog(UIScreen screen){
        this.screen=screen;
        sceneManager=screen.getSceneManager();
        platformHandler=screen.getPlatformHandler();
        deviceHandler=screen.getDeviceHandler();
        permissionHandler=screen.getPermissionHandler();
        width=screen.getWidth();
        height=screen.getHeight();
        density=screen.getDensity();
        sidePad=screen.getSidePad();
        contentPad=screen.getContentPad();
        focusListener = new FocusListener() {
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused) focusChanged(event);
            }

            public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused) focusChanged(event);
            }

            private void focusChanged(FocusEvent event) {
                Stage stage = getStage();
                if (stage != null && stage.getRoot().getChildren().size > 0
                        && stage.getRoot().getChildren().peek() == UIDialog.this) { // Dialog is top most actor.
                    Actor newFocusedActor = event.getRelatedActor();
                    if (newFocusedActor != null && !newFocusedActor.isDescendantOf(UIDialog.this)
                            && !(newFocusedActor.equals(previousKeyboardFocus) || newFocusedActor.equals(previousScrollFocus)))
                        event.cancel();
                }
            }
        };

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (getX() != 0 || getY() != 0) return true;
                Actor hit = null;
                Vector2 point = new Vector2();
                SnapshotArray<Actor> childrenArray = getChildren();
                for (int i = childrenArray.size - 1; i >= 0; i--) {
                    Actor child = childrenArray.get(i);
                    child.parentToLocalCoordinates(point.set(x, y));
                    hit = child.hit(point.x, point.y, false);
                    if (hit != null) {
                        if (hit instanceof UITextField) {
                        } else {
                            Gdx.input.setOnscreenKeyboardVisible(false);
                            Actor actor = getStage().getKeyboardFocus();
                            if (actor != null && actor != UIDialog.this) {
                                getStage().setKeyboardFocus(UIDialog.this);
                            }
                        }
                        break;
                    }
                }
                if (hit == null) {
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    onTouchOutside();
                }
                return true;
            }

            public boolean keyDown(InputEvent event, int keycode) {
                event.handle();
                if (keycode == Input.Keys.BACK) {
                    // Delay a frame to eat the keyTyped event.
                    Gdx.app.postRunnable(new Runnable() {
                        public void run() {
                            Gdx.input.setOnscreenKeyboardVisible(false);
                            Gdx.app.postRunnable(UIDialog.this::onBackPressed);
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                event.handle();
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                event.handle();
                return true;
            }
        });
        background= ResourceUtils.getInstance().pixelDrawable.tint(new Color(0,0,0,0.75f));
        setSize(width,height);
    }


    public void setContent(Actor content){
        if (this.content!=null){
            removeActor(this.content);
        }
        this.content=content;
        if (this.content!=null){
            addActor(this.content);
        }
    }

    public void pause(){

    }

    public void resume(){

    }

    public void resize(){
        width=screen.getWidth();
        height=screen.getHeight();
        density=screen.getDensity();
        sidePad=screen.getSidePad();
        contentPad=screen.getContentPad();
        setSize(width,height);
    }

    @Override
    protected void setStage(Stage stage) {
        if (stage == null)
            addListener(focusListener);
        else
            removeListener(focusListener);
        super.setStage(stage);
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        Stage stage=getStage();
        if (stage==null)return;
        if (stage.getKeyboardFocus()==null)stage.setKeyboardFocus(this);
        if (isTransform()){
            drawStageBackground(batch,parentAlpha,0,0);
            applyTransform(batch,computeTransform());
            drawChildren(batch,parentAlpha);
            resetTransform(batch);
        }else {
            drawStageBackground(batch,parentAlpha,getX(),getY());
            drawChildren(batch,parentAlpha);
        }
    }

    protected void drawStageBackground(Batch batch,float parentAlpha,float x,float y){
        if (background==null)return;
        Color color=getColor();
        batch.setColor(color.r,color.g,color.b,color.a*parentAlpha);
        background.draw(batch,x,y,getWidth(),getHeight());
    }

    public UIDialog show() {
        return show(null);
    }

    public UIDialog show(Action action) {
        if (content==null)return null;
        Stage stage = screen.getStage();
        clearActions();
        removeCaptureListener(ignoreTouchDown);

        previousKeyboardFocus = null;
        Actor actor = stage.getKeyboardFocus();
        if (actor != null && !actor.isDescendantOf(this)) previousKeyboardFocus = actor;

        previousScrollFocus = null;
        actor = stage.getScrollFocus();
        if (actor != null && !actor.isDescendantOf(this)) previousScrollFocus = actor;

        stage.addActor(this);
        stage.cancelTouchFocus();
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);
        if (action != null) content.addAction(action);
        return this;
    }

    public void hide() {
        hide(null);
    }

    public void hide(Action action) {
        Gdx.input.setOnscreenKeyboardVisible(false);
        Stage stage = getStage();
        if (stage != null) {
            removeListener(focusListener);
            if (previousKeyboardFocus != null && previousKeyboardFocus.getStage() == null)
                previousKeyboardFocus = null;
            Actor actor = stage.getKeyboardFocus();
            if (actor == null || actor.isDescendantOf(this))
                stage.setKeyboardFocus(previousKeyboardFocus);

            if (previousScrollFocus != null && previousScrollFocus.getStage() == null)
                previousScrollFocus = null;
            actor = stage.getScrollFocus();
            if (actor == null || actor.isDescendantOf(this))
                stage.setScrollFocus(previousScrollFocus);
        }
        if (action != null) {
            addCaptureListener(ignoreTouchDown);
            content.addAction(sequence(action, Actions.run(new Runnable() {
                @Override
                public void run() {
                    removeCaptureListener(ignoreTouchDown);
                    remove();
                }
            })));
        } else
            remove();
    }

    protected abstract void buildUI();

    protected void onTouchOutside(){
        hide();
    }

    protected void onBackPressed(){
        hide();
    }
}
