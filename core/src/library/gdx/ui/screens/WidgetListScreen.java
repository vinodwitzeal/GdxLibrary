package library.gdx.ui.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

import library.gdx.SceneManager;
import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.list.WidgetList;
import library.gdx.ui.list.WidgetListItem;
import library.gdx.ui.list.WidgetSelectBox;

public class WidgetListScreen extends UIScreen{
    public WidgetListScreen(SceneManager sceneManager) {
        super(sceneManager,"WidgetListScreen");
    }

    @Override
    protected void buildUI() {
        WidgetSelectBox.WidgetSelectBoxStyle style=new WidgetSelectBox.WidgetSelectBoxStyle();
        style.background=new BoxDrawable().radius(4*density).fillSolid(Color.valueOf("#000000"));
        style.scrollStyle=new ScrollPane.ScrollPaneStyle();


        WidgetList.WidgetListStyle widgetListStyle=new WidgetList.WidgetListStyle();
        widgetListStyle.selection=new BoxDrawable().radius(4*density).fillSolid(Color.valueOf("#000000"));
        widgetListStyle.selection.setLeftWidth(8*density);
        widgetListStyle.selection.setRightWidth(8*density);
        widgetListStyle.selection.setTopHeight(2*density);
        widgetListStyle.selection.setBottomHeight(2*density);
        widgetListStyle.background=new BoxDrawable().radius(4*density).fillSolid(Color.valueOf("#C7C7C7"));
        style.listStyle=widgetListStyle;

        WidgetSelectBox selectBox=new WidgetSelectBox(style);
        Array<WidgetListItem> items=new Array<>();
        for (int i=1;i<=10;i++){
            items.add(createItem("Selectable Item"+i));
        }
        selectBox.setItems(items);

        Table mainTable=new Table();
        mainTable.setFillParent(true);

        mainTable.add(selectBox);

        stage.addActor(mainTable);
    }

    private WidgetListItem createItem(String text){
        WidgetListItem item=new WidgetListItem();
        item.add(new UILabel(text,new UILabel.UILabelStyle(FontType.BOLD, FontSize.labelLarge,Color.valueOf("#FFFFFF"))));
        return item;
    }
}
