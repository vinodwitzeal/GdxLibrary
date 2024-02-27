package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.files.FileAction;
import library.gdx.files.FileChooser;
import library.gdx.files.FileFilter;
import library.gdx.files.FileOptions;
import library.gdx.fonts.Font;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UIButtonGroup;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.screens.SVGPackerScreen;
import library.gdx.ui.styles.Styles;
import library.gdx.ui.textfield.UIInputField;
import library.gdx.utils.FontUtils;

public class SVGFilesPane extends SVGGroupContainer{
    private SVGPackerScreen screen;
    private SVGStyle style;
    private FileHandle selectedFolder;

    private Table filesTable;
    private UIButtonGroup<SVGFileItem> fileItemGroup;
    public SVGFilesPane(SVGPackerScreen screen) {
        super("Files & Output");
        this.screen=screen;
        this.style=screen.style;
        top();
        build();
    }



    protected void build(){
        Table contenTable=getContentTable();
        Font textFieldFont= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelMedium);
        float vertical=textFieldFont.getCapHeight();
        float horizontal=textFieldFont.getCapHeight()*1.5f;

        BoxDrawable boxDrawable=new BoxDrawable().fillNone().radius(16).outline(1,new Color(1,1,1,0.75f));
        Styles.setDrawablePadding(boxDrawable,horizontal,vertical);

        UIInputField.UIInputFieldStyle inputFieldStyle=new UIInputField.UIInputFieldStyle();
        inputFieldStyle.font=textFieldFont;
        inputFieldStyle.background=boxDrawable.tint(new Color(1,1,1,0.75f));
        inputFieldStyle.fontColor=new Color(1,1,1,0.75f);
        inputFieldStyle.focusedFontColor=new Color(1,1,1,1);
        inputFieldStyle.disabledFontColor=new Color(1,1,1,0.5f);
        inputFieldStyle.focusedBackground=new BoxDrawable(boxDrawable).fillSolid(new Color(0,0,0,0.5f));
        inputFieldStyle.disabledBackground=boxDrawable.tint(new Color(1,1,1,0.5f));
        BoxDrawable cursor=new BoxDrawable().fillSolid(inputFieldStyle.focusedFontColor);
        cursor.setMinSize(2,textFieldFont.getCapHeight());
        inputFieldStyle.cursor=cursor;

        UIInputField textField=new UIInputField("",true,inputFieldStyle){
            @Override
            protected void onClicked() {
                FileOptions fileOptions=new FileOptions();
                fileOptions.directory= Gdx.files.local("");
                fileOptions.filters= FileFilter.createSingle("Select Folder","svg");
                fileOptions.action= FileAction.OpenFolder;
                FileChooser.FileChooserCallback callback=new FileChooser.FileChooserCallback() {
                    @Override
                    public void canceled() {
                        Gdx.app.error("File Canceled","");
                    }

                    @Override
                    public void selected(Array<FileHandle> fileHandles) {
                        if (fileHandles.size>0){
                            FileHandle fileHandle=fileHandles.get(0);
                            if (fileHandle.isDirectory()){
                                try {
                                    selectedFolder=fileHandle;
                                    textField.setText(fileHandle.path());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void error(String error) {
                        Gdx.app.error("File Error",error);
                    }
                };

                screen.getPlatformHandler().fileChooser.open(fileOptions,callback);
            }
        };
        Table inputTailTable=new Table();
        inputTailTable.setBackground(new BoxDrawable().radius(0,16,16,0).fillSolid(new Color(0,0,0,0.5f)).outline(1,new Color(1,1,1,0.75f)));
        inputTailTable.padLeft(20).padRight(20);
        inputTailTable.pack();
        inputTailTable.add(new Image(style.getIcon("folder_open",textFieldFont.getLineHeight())));
        textField.setTail(inputTailTable).expandY().fillY().padRight(-horizontal).padTop(-vertical).padBottom(-vertical);
        textField.setMessageText("Select Output Directory");
        contenTable.add(textField).expandX().fillX().padBottom(10);
        contenTable.row();

        UITextButton.UITextButtonStyle menuButtonStyle=style.buttonStyle;
        float iconHeight=menuButtonStyle.font.getLineHeight();
        SVGButtonPane buttonPane=new SVGButtonPane();

        SVGIconButton addFileButton=new SVGIconButton(style.getIcon("add_photo_alternate",iconHeight),"Add File",style.buttonStyle);
        SVGIconButton addFolderButton=new SVGIconButton(style.getIcon("create_new_folder",iconHeight),"Add Folder",style.buttonStyle);
        SVGIconButton deleteButton=new SVGIconButton(style.getIcon("delete",iconHeight),"Delete",style.buttonStyle){
            @Override
            public void act(float delta) {
                super.act(delta);
                setDisabled(fileItemGroup.getAllChecked().size<1);
            }
        };
        SVGIconButton deleteAllButton=new SVGIconButton(style.getIcon("delete_forever",iconHeight),"Delete All",style.buttonStyle){
            @Override
            public void act(float delta) {
                super.act(delta);
                setDisabled(fileItemGroup.getButtons().size<1);
            }
        };
        buttonPane.addButton(addFileButton);
        buttonPane.addButton(addFolderButton);
        buttonPane.addButton(deleteButton);
        buttonPane.addButton(deleteAllButton);

        contenTable.add(buttonPane).expandX().fillX().padBottom(5);
        contenTable.row();

        filesTable=new Table();
        filesTable.top();
        fileItemGroup=new UIButtonGroup<>();
        fileItemGroup.setMinCheckCount(0);
        fileItemGroup.setMaxCheckCount(0);
        ScrollPane fileItemScrollPane=new ScrollPane(filesTable,style.scrollPaneStyle);
        fileItemScrollPane.setScrollingDisabled(true,false);
        contenTable.add(fileItemScrollPane).expand().fill();


        FileOptions fileOptions=new FileOptions();
        fileOptions.directory= Gdx.files.local("");
        fileOptions.filters=FileFilter.createSingle("SVG File","svg");
        FileChooser.FileChooserCallback callback=new FileChooser.FileChooserCallback() {
            @Override
            public void canceled() {
                Gdx.app.error("File Canceled","");
            }

            @Override
            public void selected(Array<FileHandle> fileHandles) {
                Gdx.app.error("File Selected",fileHandles.toString());
                for (FileHandle fileHandle:fileHandles){
                    if(fileHandle.isDirectory()){
                        FileHandle[] files=fileHandle.list(".svg");
                        for (FileHandle svgFileHandle:files){
                            addFileItem(svgFileHandle);
                        }
                    }else {
                        addFileItem(fileHandle);
                    }
                }
            }

            @Override
            public void error(String error) {
                Gdx.app.error("File Error",error);
            }
        };


        addFolderButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (addFolderButton.isDisabled())return;
                fileOptions.action= FileAction.OpenFolder;
                screen.getPlatformHandler().fileChooser.open(fileOptions,callback);
            }
        });


        addFileButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (addFileButton.isDisabled())return;
                fileOptions.action= FileAction.OpenFile;
                screen.getPlatformHandler().fileChooser.open(fileOptions,callback);
            }
        });

        deleteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (deleteButton.isDisabled())return;
                Array<SVGFileItem> svgFileItems=new Array<>(fileItemGroup.getAllChecked());
                if (svgFileItems.size>0){
                    for (SVGFileItem svgFileItem:svgFileItems){
                        removeFileItem(svgFileItem);
                    }
                }
            }
        });

        deleteAllButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (deleteAllButton.isDisabled())return;
                Array<SVGFileItem> svgFileItems=new Array<>(fileItemGroup.getButtons());
                if (svgFileItems.size>0){
                    for (SVGFileItem svgFileItem:svgFileItems){
                        removeFileItem(svgFileItem);
                    }
                }
            }
        });
    }

    protected void addFileItem(FileHandle fileHandle){
        SVGFileItem fileItem=new SVGFileItem(fileHandle,style.fileItemStyle);
        filesTable.add(fileItem).expandX().fillX().padBottom(2);
        filesTable.row();
        fileItemGroup.add(fileItem);
    }

    protected void removeFileItem(SVGFileItem fileItem){
        Cell<SVGFileItem> cell=filesTable.getCell(fileItem);
        if (cell!=null){
            fileItemGroup.remove(fileItem);
            cell.setActor(null).pad(0).size(0,0);
        }
    }

    public FileHandle getSelectedFolder() {
        return selectedFolder;
    }

    public Array<SVGFileItem> getAllFileItems(){
        return fileItemGroup.getButtons();
    }
}
