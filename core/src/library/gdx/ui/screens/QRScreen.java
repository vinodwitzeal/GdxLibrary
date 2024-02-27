package library.gdx.ui.screens;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import library.gdx.SceneManager;
import library.gdx.utils.QRGenerator;

public class QRScreen extends UIScreen{

    public QRScreen(SceneManager sceneManager) {
        super(sceneManager,"QRScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable=new Table();
        mainTable.setFillParent(true);

        TextureRegion qr=new QRGenerator().blockSize(4).generate("Hello,How are you?");

        mainTable.add(new Image(qr)).width(width*0.5f).height(width*0.5f);

        stage.addActor(mainTable);

    }
}
