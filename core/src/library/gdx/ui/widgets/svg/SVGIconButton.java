package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;

public class SVGIconButton extends UITextButton {
    private Image iconImage;
    public SVGIconButton(Drawable icon,String text,UITextButtonStyle style){
        super("",style);
        clearChildren();
        iconImage=new Image(icon);
        label=new UILabel(text,new UILabel.UILabelStyle(style.font,style.fontColor));
        add(iconImage).padRight(style.font.getCapHeight()*0.25f);
        add(label);
    }
}
