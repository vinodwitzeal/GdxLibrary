package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mazatech.gdx.SVGTexture;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.styles.Styles;
import library.gdx.utils.FontUtils;
import library.gdx.utils.GameUtils;
import library.gdx.utils.ResourceUtils;

public class SVGStyle {
    public UITextButton.UITextButtonStyle buttonStyle;
    public SVGAtlasItem.SVGAtlasItemStyle atlasItemStyle;
    public SVGFileItem.SVGFileItemStyle fileItemStyle;
    public ScrollPane.ScrollPaneStyle scrollPaneStyle;

    public SVGStyle(){
        buttonStyle=new UITextButton.UITextButtonStyle();
        buttonStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelMedium);
        buttonStyle.fontColor= new Color(1,1,1,1);
        buttonStyle.downFontColor=new Color(1,1,1,0.75f);
        buttonStyle.disabledFontColor=new Color(1,1,1,0.5f);
        BoxDrawable buttonBackground=new BoxDrawable().fillSolid(Color.valueOf("#939393")).radius(8);
        buttonBackground.setLeftWidth(buttonStyle.font.getLineHeight());
        buttonBackground.setRightWidth(buttonStyle.font.getLineHeight());
        buttonBackground.setTopHeight(buttonStyle.font.getLineHeight()*0.5f);
        buttonBackground.setBottomHeight(buttonStyle.font.getLineHeight()*0.5f);
        buttonStyle.up=buttonBackground;
        buttonStyle.down=buttonBackground.tint(new Color(1,1,1,0.75f));
        buttonStyle.disabled=buttonBackground.tint(new Color(1,1,1,0.5f));
        buttonStyle.checked=buttonBackground;

        atlasItemStyle=new SVGAtlasItem.SVGAtlasItemStyle();
        fileItemStyle=new SVGFileItem.SVGFileItemStyle();

        scrollPaneStyle=new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background=new BoxDrawable().fillSolid(new Color(1,1,1,0.1f)).radius(16);
        BoxDrawable knob=new BoxDrawable().fillSolid(new Color(1,1,1,0.5f)).radius(4);
        knob.setMinSize(10,10);
        scrollPaneStyle.hScrollKnob=knob;
        scrollPaneStyle.vScrollKnob=knob;
    }

    public Drawable getIcon(String name, float height){
        return getIcon(name,height,Color.WHITE);
    }

    public Drawable getIcon(String name, float height,Color color){
        FileHandle fileHandle= Gdx.files.internal("ui/svg/"+name+"_white_48dp.svg");
        return getIcon(fileHandle,height,color);
    }

    public Drawable getIcon(FileHandle fileHandle, float height){
        return getIcon(fileHandle,height,Color.WHITE);
    }

    public Drawable getIcon(FileHandle fileHandle, float height,Color color){
        SVGTexture texture=ResourceUtils.getInstance().uiAssets.getSvgAssetsGDX().createTexture(fileHandle);
        TextureRegion region=new TextureRegion(texture);
        region.flip(false,true);
        return Styles.getDrawableRespectToHeight(region,height);
    }
}
