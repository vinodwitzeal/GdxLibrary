package library.gdx.handlers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.ArrayDeque;
import java.util.Deque;

import library.gdx.ui.screens.UIScreen;
import library.gdx.ui.styles.Styles;
import library.gdx.utils.FontUtils;
import library.gdx.utils.ResourceUtils;

public abstract class SceneHandler implements ApplicationListener {
    private UIScreen screen;
    private PolygonSpriteBatch screenBatch;

    private Deque<UIScreen> pushedScreens;

    @Override
    public void create() {
        screenBatch=new PolygonSpriteBatch();
        pushedScreens=new ArrayDeque<>();
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
        ResourceUtils.init();
        FontUtils.init();
        Styles.init();
        onCreate();
    }

    public abstract void onCreate();

    @Override
    public void dispose () {
        if (screen != null){
            screen.hide();
            screen.dispose();
        }
    }

    @Override
    public void pause () {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume () {
        if (screen != null) screen.resume();
    }

    @Override
    public void render () {
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize (int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    /** Sets the current screen. {@link Screen#hide()} is called on any old screen, and {@link Screen#show()} is called on the new
     * screen, if any.
     * @param screen may be {@code null} */
    public void setScreen (UIScreen screen) {
        if (this.screen != null) this.screen.hide();
        UIScreen previousScreen=this.screen;
        this.screen = screen;
        if (this.screen != null) {
            Gdx.app.error("Screen",screen.toString());
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        if (previousScreen!=null)previousScreen.dispose();
    }

    /** @return the currently active {@link Screen}. */
    public Screen getScreen () {
        return screen;
    }

    public void pushScreen(UIScreen screen){
        pushedScreens.add(screen);
        setScreen(screen);
    }

    public void popScreen(){
        UIScreen popedScreen=pushedScreens.removeLast();
        UIScreen screen=pushedScreens.getLast();
        if (screen!=null){
            Gdx.app.error("Screen",screen.toString());
            setScreen(screen);
        }
    }

    public PolygonSpriteBatch getScreenBatch() {
        return screenBatch;
    }
}
