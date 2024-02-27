package library.gdx.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mazatech.gdx.SVGTexture;

import library.gdx.SceneManager;
import library.gdx.utils.ResourceUtils;

public class SVGScreen extends UIScreen{
    public SVGScreen(SceneManager sceneManager) {
        super(sceneManager,"SVGScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable=new Table();
        mainTable.setFillParent(true);
        SVGTexture svgTexture= ResourceUtils.getInstance().uiAssets.findSVGTexture("ColourfulBackground35");

        Image svgImage=new Image(svgTexture);

        mainTable.add(svgImage).width(width).height(width);
        stage.addActor(mainTable);


    }
}
