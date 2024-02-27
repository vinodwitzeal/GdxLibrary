package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.labels.UILabel;
import library.gdx.utils.FontUtils;

public class SVGGroupContainer extends Table {
    private Table contentTable;
    public SVGGroupContainer(String title){
        setBackground(new BoxDrawable().fillSolid(Color.valueOf("#343434")));
        top().left();
        UILabel.UILabelStyle titleStyle=new UILabel.UILabelStyle();
        titleStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelMedium);
        titleStyle.fontColor=Color.valueOf("#757575");
        add(new UILabel(title,titleStyle)).expandX().align(Align.left).pad(20,20,10,20);
        row();
        contentTable=new Table();
        contentTable.pad(10,20,0,20);
        contentTable.top().left();
        contentTable.setBackground(new BoxDrawable().fillSolid(new Color(1,1,1,0.1f)));
        add(contentTable).expand().fill();
    }

    public Table getContentTable() {
        return contentTable;
    }
}
