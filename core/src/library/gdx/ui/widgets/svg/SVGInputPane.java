package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.ui.buttons.UIButtonGroup;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.dialogs.svg.SVGInputDialog;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.list.WidgetList;
import library.gdx.ui.list.WidgetListItem;
import library.gdx.ui.screens.SVGPackerScreen;
import library.gdx.utils.GameUtils;

public class SVGInputPane extends SVGGroupContainer{
    private SVGPackerScreen screen;
    private SVGStyle style;
    private UIButtonGroup<SVGAtlasItem> atlasItemGroup;
    private Table atlasItemTable;

    public SVGInputPane(SVGPackerScreen screen) {
        super("Document");
        this.screen=screen;
        this.style=screen.style;
        build();
    }



    protected void build(){
        Table contentTable=getContentTable();
        SVGButtonPane buttonPane=new SVGButtonPane();
        UITextButton.UITextButtonStyle menuButtonStyle=style.buttonStyle;
        float iconHeight=menuButtonStyle.font.getLineHeight();

        atlasItemGroup=new UIButtonGroup<>();
        atlasItemGroup.setMinCheckCount(1);
        atlasItemGroup.setMaxCheckCount(1);
        atlasItemTable=new Table();
        atlasItemTable.pad(2);
        atlasItemTable.top().left();

        ScrollPane atlasItemScrollPane=new ScrollPane(atlasItemTable,style.scrollPaneStyle);
        atlasItemScrollPane.setScrollingDisabled(true,false);

        UITextButton addButton=new SVGIconButton(style.getIcon("post_add",iconHeight),"Add",menuButtonStyle);
        UITextButton deleteButton=new SVGIconButton(style.getIcon("delete",iconHeight),"Delete",menuButtonStyle){
            @Override
            public void act(float delta) {
                super.act(delta);
                setDisabled(atlasItemGroup.getChecked()==null);
            }
        };
        buttonPane.addButton(addButton);
        buttonPane.addButton(deleteButton);
        contentTable.add(buttonPane).expandX().fillX().align(Align.left).padBottom(4);
        contentTable.row();
        contentTable.add(atlasItemScrollPane).expand().fill();
        contentTable.row();


        addButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (addButton.isDisabled())return;
                new SVGInputDialog(screen,"Add Atlas","Enter Atlas Name"){
                    @Override
                    public void onSubmit(String inputText) {
                        if (GameUtils.isNotNullAndEmpty(inputText)){
                            SVGAtlasItem atlasItem=new SVGAtlasItem(SVGInputPane.this.screen,inputText,style.atlasItemStyle);
                            atlasItem.addListener(new ChangeListener() {
                                @Override
                                public void changed(ChangeEvent event, Actor actor) {
                                    if (atlasItem.isChecked()){
                                        onItemSelected(atlasItem);
                                    }
                                }
                            });
                            atlasItemGroup.add(atlasItem);
                            atlasItemTable.add(atlasItem).expandX().fillX().padTop(2).padBottom(2);
                            atlasItemTable.row();
                            atlasItem.setChecked(true);
                        }
                    }
                }.show();
            }
        });

        deleteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (deleteButton.isDisabled())return;
                SVGAtlasItem atlasItem=getSelectedItem();
                if (atlasItem!=null){
                    Cell<SVGAtlasItem> cell=atlasItemTable.getCell(atlasItem);
                    if (cell!=null){
                        cell.setActor(null).size(0,0).pad(0);
                        atlasItemTable.invalidate();
                    }
                    atlasItemGroup.remove(atlasItem);
                    onItemSelected(null);
                }
            }
        });
    }

    public SVGAtlasItem getSelectedItem(){
        return atlasItemGroup.getChecked();
    }

    public Array<SVGAtlasItem> getAllItems(){
        return atlasItemGroup.getButtons();
    }

    public void onItemSelected(SVGAtlasItem svgAtlasItem){

    }
}
