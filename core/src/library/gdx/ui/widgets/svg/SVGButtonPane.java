package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.ui.buttons.UITextButton;

public class SVGButtonPane extends Table {
    public SVGButtonPane(){
        left();
    }


    public void addButton(UITextButton button){
        add(button).uniformX().fillX().padRight(10);
    }

}
