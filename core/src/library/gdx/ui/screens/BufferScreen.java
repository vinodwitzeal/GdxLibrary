package library.gdx.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import library.gdx.SceneManager;
import library.gdx.drawables.box.BoxDrawable;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.styles.Styles;

public class BufferScreen extends UIScreen{
    public BufferScreen(SceneManager sceneManager) {
        super(sceneManager, "BufferScreen");
    }

    @Override
    protected void buildUI() {
        Table mainTable=new Table();
        mainTable.setFillParent(true);

        mainTable.add(new FrameBufferImage()).width(width*0.5f).height(width*0.5f);

        stage.addActor(mainTable);
    }






    private class FrameBufferImage extends Image{
        public FrameBufferImage(){

            Stage frameStage=new Stage();
            FrameBufferActor frameBufferActor=new FrameBufferActor();
            frameBufferActor.pack();
            frameBufferActor.setSize(width*0.5f,width*0.5f);
            int actorWidth=(int)frameBufferActor.getWidth();
            int actorHeight=(int)frameBufferActor.getHeight();
            frameStage.addActor(frameBufferActor);
            FrameBuffer frameBuffer=new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false);
            frameBuffer.begin();
            ScreenUtils.clear(Color.CLEAR);
            frameStage.draw();
            frameBuffer.end();


            frameBuffer.bind();
            Pixmap pixmap=Pixmap.createFromFrameBuffer(0,0,actorWidth,actorHeight);
            FrameBuffer.unbind();
            Texture texture=new Texture(pixmap);
            TextureRegion region=new TextureRegion(texture);
            region.flip(false,true);
            setDrawable(new TextureRegionDrawable(region));
        }
    }





    private class FrameBufferActor extends Table{
        public FrameBufferActor(){
            pad(10);
            setBackground(new BoxDrawable().radius(80).fillSolid(Color.valueOf("#FF00FF")));

            UITextButton textButton=new UITextButton("Click Here!", Styles.button.contained);

            add(textButton).expand().fillX().align(Align.bottom);
        }
    }


}
