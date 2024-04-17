package library.gdx.ui.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

import library.gdx.SceneManager;
import library.gdx.ui.buttons.UIButtonGroup;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.styles.Styles;
import library.gdx.ui.widgets.masked.Mask;
import library.gdx.ui.widgets.masked.MaskedScrollPane;
import library.gdx.utils.ResourceUtils;

public class HomeScreen extends UIScreen{
    private UIButtonGroup<UITextButton> buttonGroup;
    public HomeScreen(SceneManager sceneManager) {
        super(sceneManager,"HomeScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable=new Table();
        mainTable.setFillParent(true);
        Table scrollPaneTable=new Table();
        scrollPaneTable.defaults().space(density*2.0f);
        TextureRegion maskRegion= ResourceUtils.getInstance().uiAssets.findRegion("circle");
        ScrollPane scrollPane=new ScrollPane(scrollPaneTable);
        scrollPane.setScrollingDisabled(true,false);

        buttonGroup=new UIButtonGroup<>();
        buttonGroup.setMinCheckCount(0);
        buttonGroup.setMaxCheckCount(1);

        addButton(scrollPaneTable,"SVG", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new SVGScreen(sceneManager));
            }
        });

        addButton(scrollPaneTable,"Widget", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new WidgetListScreen(sceneManager));
            }
        });

        addButton(scrollPaneTable, "QR", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new QRScreen(sceneManager));
            }
        });

        addButton(scrollPaneTable, "Camera", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new CameraScreen(sceneManager));
            }
        });

        addButton(scrollPaneTable, "Toast", new Runnable() {
            @Override
            public void run() {
                deviceHandler.showToast("Toast "+ TimeUtils.millis());
            }
        });

        addButton(scrollPaneTable, "Frame Buffer", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new BufferScreen(sceneManager));
            }
        });

        addButton(scrollPaneTable, "Notifications", new Runnable() {
            @Override
            public void run() {
                deviceHandler.openNotifications();
            }
        });

        addButton(scrollPaneTable, "Lottie", new Runnable() {
            @Override
            public void run() {
                sceneManager.pushScreen(new LottieScreen(sceneManager));
            }
        });

        mainTable.add(scrollPane);
        stage.addActor(mainTable);
    }


    private void addButton(Table buttonTable,String text,Runnable runnable){
        UITextButton.UITextButtonStyle textButtonStyle= Styles.button.contained;
        UITextButton textButton= new UITextButton(text,textButtonStyle);
        buttonGroup.add(textButton);
        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                runnable.run();
            }
        });
        buttonTable.add(textButton).uniform().fill().row();
    }

    @Override
    protected boolean onBackButtonPressed() {
        return false;
    }
}
