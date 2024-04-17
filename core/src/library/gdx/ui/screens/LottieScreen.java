package library.gdx.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import library.gdx.SceneManager;
import library.gdx.lottie.LottieAnimationImage;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.styles.Styles;

public class LottieScreen extends UIScreen{
    public LottieScreen(SceneManager sceneManager) {
        super(sceneManager, "LottieScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);


        LottieAnimationImage lottieAnimationImage=new LottieAnimationImage(Gdx.files.internal("lottie/bullseye.json"));

        float imageSize = width * 0.75f;

        mainTable.add(lottieAnimationImage).width(imageSize).height(imageSize);
        mainTable.row();

        Table buttonTable = new Table();

        UITextButton playButton = new UITextButton("Play", Styles.button.contained);

        buttonTable.add(playButton).uniform().padTop(contentPad);
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                lottieAnimationImage.playAnimation();
            }
        });

        mainTable.add(buttonTable);

        stage.addActor(mainTable);
    }
}
