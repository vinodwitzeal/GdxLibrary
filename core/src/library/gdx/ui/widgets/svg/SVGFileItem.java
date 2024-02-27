package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mazatech.gdx.SVGTexture;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.Font;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.list.WidgetListItem;
import library.gdx.ui.styles.Styles;
import library.gdx.utils.FontUtils;
import library.gdx.utils.ResourceUtils;

public class SVGFileItem extends UITextButton {
    private FileHandle fileHandle;
    public SVGFileItem(FileHandle fileHandle, SVGFileItemStyle style) {
        super("", style);
        clearChildren(true);
        left();
        this.fileHandle=fileHandle;
        label=newLabel(fileHandle.path(),new UILabel.UILabelStyle(style.font,style.fontColor));
        add(getIcon(fileHandle));
        add(label).padLeft(style.font.getCapHeight()*0.2f);
    }

    protected Image getIcon(FileHandle fileHandle){
        SVGTexture svgTexture= ResourceUtils.getInstance().uiAssets.getSvgAssetsGDX().createTexture(fileHandle);
        TextureRegion region=new TextureRegion(svgTexture);
        region.flip(false,true);
        TextureRegionDrawable iconDrawable=Styles.getDrawableRespectToHeight(region,getStyle().font.getCapHeight());
        return  new Image(iconDrawable);
    }

    public FileHandle getFileHandle() {
        return fileHandle;
    }


    public static class SVGFileItemStyle extends UITextButtonStyle{

        public SVGFileItemStyle() {
            Font buttonFont= FontUtils.scaledFont(FontType.MEDIUM_ITALIC, FontSize.labelMedium);
            BoxDrawable boxDrawable=new BoxDrawable().radius(10).fillSolid(Color.valueOf("#757575")).outline(1,new Color(1,1,1,0.2f));
            float horizontal=buttonFont.getCapHeight();
            float vertical=horizontal*0.5f;
            Styles.setDrawablePadding(boxDrawable,horizontal,vertical);
            up=boxDrawable;
            down=boxDrawable.tint(new Color(1,1,1,0.5f));
            checked=new BoxDrawable(boxDrawable).fillSolid(Color.valueOf("#252525"));
            font=buttonFont;
            fontColor=Color.valueOf("#ADADAD");
            downFontColor=Color.valueOf("#555555");
            checkedFontColor=Color.valueOf("#DADADA");
        }
    }
}
