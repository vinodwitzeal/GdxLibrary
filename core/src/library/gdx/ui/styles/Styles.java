package library.gdx.ui.styles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.Font;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.utils.FontUtils;
import library.gdx.utils.ResourceUtils;
import library.gdx.utils.UIColorScheme;

public class Styles {
    public static ButtonStyles button;
    public static LabelStyles label;
    private static Font buttonFont;
    private static float horizontal,vertical,radius,outline;
    private static UIColorScheme colorScheme;
    public static void init(){
        createButtonStyles();
    }


    private static void createButtonStyles(){
        buttonFont= FontUtils.scaledFont(FontType.MEDIUM, FontSize.titleMedium);
        horizontal=buttonFont.getCapHeight();
        vertical=buttonFont.getCapHeight()*0.5f;
        radius=vertical*0.75f;
        outline=radius*0.5f;
        colorScheme= ResourceUtils.getInstance().colorScheme;

        button =new ButtonStyles();

        button.text =new UITextButton.UITextButtonStyle();
        button.text.up=createBoxDrawable(null,null);
        button.text.font=buttonFont;
        button.text.fontColor=colorScheme.primary;
        button.text.downFontColor=new Color(colorScheme.primary).mul(0.7f,0.7f,0.7f,1.0f);
        button.text.disabledFontColor=new Color(colorScheme.primary).mul(1,1,1,0.75f);

        BoxDrawable outlinedDrawable= createBoxDrawable(null,new Color(0,0,0,0.25f));
        button.outlined =new UITextButton.UITextButtonStyle();
        button.outlined.up=outlinedDrawable;
        button.outlined.down=outlinedDrawable.tint(Color.valueOf("#A0A0A0"));
        button.outlined.checked=outlinedDrawable;
        button.outlined.font=buttonFont;
        button.outlined.fontColor=colorScheme.primary;
        button.outlined.downFontColor=new Color(colorScheme.primary).mul(0.7f,0.7f,0.7f,1.0f);
        button.outlined.disabled=outlinedDrawable.tint(new Color(1,1,1,0.75f));
        button.outlined.disabledFontColor=new Color(button.outlined.fontColor).mul(1,1,1,0.75f);

        BoxDrawable containedDrawable= createBoxDrawable(colorScheme.primary,null);
        button.contained =new UITextButton.UITextButtonStyle();
        button.contained.up=containedDrawable;
        button.contained.down=containedDrawable.tint(Color.valueOf("#A0A0A0"));
        button.contained.checked=containedDrawable;
        button.contained.font=buttonFont;
        button.contained.fontColor=colorScheme.onPrimary;
        button.contained.downFontColor=new Color(colorScheme.onPrimary).mul(0.7f,0.7f,0.7f,1.0f);
        button.contained.disabled=containedDrawable.tint(new Color(1,1,1,0.75f));
        button.contained.disabledFontColor=new Color(button.contained.fontColor).mul(1,1,1,0.75f);


        BoxDrawable toggledDrawable= createBoxDrawable(null,new Color(0,0,0,0.25f));
        button.toggle =new UITextButton.UITextButtonStyle();
        button.toggle.up=toggledDrawable;
        button.toggle.down=toggledDrawable.tint(Color.valueOf("#A0A0A0"));
        button.toggle.checked=createBoxDrawable(new Color(colorScheme.primary).mul(1,1,1,0.1f),colorScheme.primary);
        button.toggle.font=buttonFont;
        button.toggle.fontColor=new Color(0,0,0,0.5f);
        button.toggle.downFontColor=new Color(0,0,0,0.75f);
        button.toggle.checkedFontColor=colorScheme.primary;
        button.toggle.disabled=toggledDrawable.tint(new Color(1,1,1,0.75f));
        button.toggle.disabledFontColor=new Color(button.toggle.fontColor).mul(1,1,1,0.75f);


        label=new LabelStyles();
        label.small=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelSmall,colorScheme.primary);
        label.medium=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelMedium,colorScheme.primary);
        label.large=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelLarge,colorScheme.primary);

        label.errorSmall=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelSmall,colorScheme.error);
        label.errorMedium=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelMedium,colorScheme.error);
        label.errorLarge=new UILabel.UILabelStyle(FontType.REGULAR,FontSize.labelLarge,colorScheme.error);


    }

    protected static BoxDrawable createBoxDrawable(Color fillColor, Color outlineColor){
        BoxDrawable boxDrawable= createBoxDrawable(fillColor,outlineColor,radius,outline);
        boxDrawable.setLeftWidth(horizontal);
        boxDrawable.setRightWidth(horizontal);
        boxDrawable.setTopHeight(vertical);
        boxDrawable.setBottomHeight(vertical);
        return boxDrawable;
    }



    public static BoxDrawable createBoxDrawable(Color fillColor, Color outlineColor, float radius, float outline){
        BoxDrawable boxDrawable=new BoxDrawable();
        boxDrawable.radius(radius);
        if (fillColor!=null){
            boxDrawable.fillSolid(fillColor);
        }

        if (outlineColor!=null){
            boxDrawable.outline(outline,outlineColor);
        }
        return boxDrawable;
    }

    public static Color tintColor(Color color,Color tint){
        return new Color(color).mul(tint);
    }

    public static TextureRegionDrawable getDrawableRespectToWidth(Texture texture, float width){
        float height=width*texture.getHeight()/texture.getWidth();
        return getDrawable(texture,width,height);
    }

    public static TextureRegionDrawable getDrawableRespectToWidth(TextureRegion region, float width){
        float height=width*region.getRegionHeight()/region.getRegionWidth();
        return getDrawable(region,width,height);
    }

    public static TextureRegionDrawable getDrawableRespectToHeight(Texture texture,float height){
        float width=height*texture.getWidth()/texture.getHeight();
        return getDrawable(texture,width,height);
    }

    public static TextureRegionDrawable getDrawableRespectToHeight(TextureRegion region,float height){
        float width=height*region.getRegionWidth()/region.getRegionHeight();
        return getDrawable(region,width,height);
    }

    public static TextureRegionDrawable getDrawable(Texture texture,float width,float height){
        TextureRegionDrawable drawable=new TextureRegionDrawable(texture);
        drawable.setMinSize(width,height);
        return drawable;
    }

    public static TextureRegionDrawable getDrawable(TextureRegion region,float width,float height){
        TextureRegionDrawable drawable=new TextureRegionDrawable(region);
        drawable.setMinSize(width,height);
        return drawable;
    }

    public static NinePatchDrawable getNinePatchDrawable(Texture texture, int left, int right, int top, int bottom){
        return new NinePatchDrawable(new NinePatch(texture,left,right,top,bottom));
    }

    public static NinePatchDrawable getNinePatchDrawable(TextureRegion region,int left,int right,int top,int bottom){
        return new NinePatchDrawable(new NinePatch(region,left,right,top,bottom));
    }

    public static NinePatchDrawable getNinePatchDrawable(Texture texture,int horizontal,int vertical){
        return getNinePatchDrawable(texture,horizontal,horizontal,vertical,vertical);
    }

    public static NinePatchDrawable getNinePatchDrawable(TextureRegion region,int horizontal,int vertical){
        return getNinePatchDrawable(region,horizontal,horizontal,vertical,vertical);
    }


    public static void setDrawablePadding(Drawable drawable, float left, float right, float top, float bottom){
        drawable.setLeftWidth(left);
        drawable.setRightWidth(right);
        drawable.setTopHeight(top);
        drawable.setBottomHeight(bottom);
    }

    public static void setDrawablePadding(Drawable drawable,float horizontal,float vertical){
        setDrawablePadding(drawable,horizontal,horizontal,vertical,vertical);
    }
}
