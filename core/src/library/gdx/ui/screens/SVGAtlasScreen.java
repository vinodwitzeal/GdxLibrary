//package library.gdx.ui.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.ui.Cell;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.Align;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.Scaling;
//import com.mazatech.gdx.SVGAssetsGDX;
//import com.mazatech.gdx.SVGTexture;
//import com.mazatech.gdx.SVGTextureAtlas;
//import com.mazatech.gdx.SVGTextureAtlasGenerator;
//import com.mazatech.gdx.SVGTextureAtlasPage;
//import com.mazatech.gdx.SVGTextureAtlasRegion;
//import com.mazatech.svgt.SVGColor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import library.gdx.SceneManager;
//import library.gdx.drawables.box.BoxDrawable;
//import library.gdx.files.FileAction;
//import library.gdx.files.FileChooser;
//import library.gdx.files.FileFilter;
//import library.gdx.files.FileOptions;
//import library.gdx.fonts.FontSize;
//import library.gdx.fonts.FontType;
//import library.gdx.ui.buttons.UIButtonGroup;
//import library.gdx.ui.buttons.UITextButton;
//import library.gdx.ui.labels.UILabel;
//import library.gdx.ui.styles.Styles;
//import library.gdx.utils.FontUtils;
//import library.gdx.utils.GameUtils;
//import library.gdx.utils.ResourceUtils;
//import library.gdx.utils.svg.SVGWriter;
//
//public class SVGAtlasScreen extends UIScreen{
//    private UITextButton.UITextButtonStyle menuButtonStyle,submenuButtonStyle,fileButtonStyle,actionButtonStyle;
//
//    private float titleHeight,subtitleHeight;
//    private UILabel.UILabelStyle titleStyle,subtitleStyle;
//    private UIButtonGroup<SVGFileButton> buttonGroup;
//    private SVGAssetsGDX svgAssetsGDX;
//
//    private SVGTextureAtlas svgTextureAtlas;
//
//    private Table previewTable;
//
//    public SVGAtlasScreen(SceneManager sceneManager) {
//        super(sceneManager, "SVGAtlasScreen");
//        svgAssetsGDX=ResourceUtils.getInstance().uiAssets.getSvgAssetsGDX();
//    }
//    @Override
//    protected void buildUI() {
//        initStyles();
//
//        Table mainTable=new Table();
//        mainTable.setFillParent(true);
//        mainTable.top();
//        mainTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#555555")));
//        Table menuTable=createMenuTable();
//        mainTable.add(menuTable).colspan(2).expandX().fillX();
//        mainTable.row();
//
//        Table inputPaneTable=createInputPane();
//        mainTable.add(inputPaneTable).width(width*0.4f).expandY().fillY();
////
//        Table previewPaneTable=createPreviewPane();
//        mainTable.add(previewPaneTable).expand().fill();
//
//        stage.addActor(mainTable);
//    }
//
//
//    protected void initStyles(){
//        menuButtonStyle=new UITextButton.UITextButtonStyle();
//        menuButtonStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelMedium);
//        menuButtonStyle.fontColor=Color.valueOf("#FFFFFF");
//        menuButtonStyle.downFontColor=Color.valueOf("#FFFFFF");
//        BoxDrawable buttonBackground=new BoxDrawable().fillNone();
//        buttonBackground.setLeftWidth(menuButtonStyle.font.getLineHeight());
//        buttonBackground.setRightWidth(menuButtonStyle.font.getLineHeight());
//        buttonBackground.setTopHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        buttonBackground.setBottomHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        menuButtonStyle.up=buttonBackground;
//        menuButtonStyle.down=new BoxDrawable(buttonBackground).fillSolid(new Color(1,1,1,0.1f));
//        menuButtonStyle.checked=buttonBackground;
//
//        titleStyle=new UILabel.UILabelStyle();
//        titleStyle.font=FontUtils.scaledFont(FontType.MEDIUM,FontSize.labelSmall);
//        titleStyle.fontColor=Color.valueOf("#FFFFFF");
//
//        titleHeight=titleStyle.font.getLineHeight()*1.5f;
//
//        subtitleStyle=new UILabel.UILabelStyle();
//        subtitleStyle.font=FontUtils.unscaledFont(FontType.MEDIUM,FontSize.labelSmall);
//        subtitleStyle.fontColor=colorScheme.onPrimary;
//
//        subtitleHeight=subtitleStyle.font.getLineHeight()*1.5f;
//
//        submenuButtonStyle=new UITextButton.UITextButtonStyle();
//        submenuButtonStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelSmall);
//        submenuButtonStyle.fontColor=Color.valueOf("#FFFFFF");
//        submenuButtonStyle.disabledFontColor=Color.valueOf("#AFAFAF");
//        submenuButtonStyle.downFontColor=Color.valueOf("#FFFFFF");
//        BoxDrawable submenuBackground=new BoxDrawable().fillNone();
//        submenuBackground.setLeftWidth(menuButtonStyle.font.getLineHeight());
//        submenuBackground.setRightWidth(menuButtonStyle.font.getLineHeight());
//        submenuBackground.setTopHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        submenuBackground.setBottomHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        submenuButtonStyle.up=submenuBackground;
//        submenuButtonStyle.down=new BoxDrawable(submenuBackground).fillSolid(new Color(1,1,1,0.1f));
//        submenuButtonStyle.disabled=new BoxDrawable(submenuBackground).fillSolid(new Color(1,1,1,0.75f));
//        submenuButtonStyle.checked=buttonBackground;
//
//        fileButtonStyle=new UITextButton.UITextButtonStyle();
//        fileButtonStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelSmall);
//        fileButtonStyle.fontColor=Color.valueOf("#FFFFFF");
//        fileButtonStyle.disabledFontColor=Color.valueOf("#AFAFAF");
//        fileButtonStyle.downFontColor=Color.valueOf("#FFFFFF");
//        BoxDrawable fileButtonBackground=new BoxDrawable().fillNone().outline(2,Color.valueOf("00000088"));
//        fileButtonBackground.setLeftWidth(menuButtonStyle.font.getLineHeight());
//        fileButtonBackground.setRightWidth(menuButtonStyle.font.getLineHeight());
//        fileButtonBackground.setTopHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        fileButtonBackground.setBottomHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        fileButtonStyle.up=fileButtonBackground;
//        fileButtonStyle.down=new BoxDrawable(fileButtonBackground).fillSolid(new Color(1,1,1,0.1f));
//        fileButtonStyle.disabled=new BoxDrawable(fileButtonBackground).fillSolid(new Color(1,1,1,0.75f));
//        fileButtonStyle.checked=new BoxDrawable(fileButtonBackground).fillSolid(new Color(0,0,0,0.2f));
//
//        actionButtonStyle=new UITextButton.UITextButtonStyle();
//        actionButtonStyle.font= FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelSmall);
//        actionButtonStyle.fontColor=Color.valueOf("#F0F0F0");
//        actionButtonStyle.disabledFontColor=Color.valueOf("#AFAFAF");
//        actionButtonStyle.downFontColor=Color.valueOf("#222222");
//        BoxDrawable actionButtonBackground=new BoxDrawable().fillSolid(Color.valueOf("#7a7a7a")).outline(2,Color.valueOf("#AFAFAF"));
//        actionButtonBackground.setLeftWidth(menuButtonStyle.font.getLineHeight());
//        actionButtonBackground.setRightWidth(menuButtonStyle.font.getLineHeight());
//        actionButtonBackground.setTopHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        actionButtonBackground.setBottomHeight(menuButtonStyle.font.getLineHeight()*0.5f);
//        actionButtonStyle.up=actionButtonBackground;
//        actionButtonStyle.down=new BoxDrawable(actionButtonBackground).fillSolid(Color.valueOf("#8a8a8a"));
//        actionButtonStyle.disabled=new BoxDrawable(actionButtonBackground).fillSolid(Color.valueOf("#585858"));
//    }
//
//
//    protected Table createMenuTable(){
//        BoxDrawable seperator=new BoxDrawable().fillSolid(Color.valueOf("#000000"));
//        Table menuTable=new Table();
//        menuTable.setBackground(new BoxDrawable().fillNone().outline(1.0f, Color.valueOf("#000000")));
//        menuTable.left();
//        UITextButton fileButton=new UITextButton("File",menuButtonStyle);
//        UITextButton toolsButton=new UITextButton("Tools",menuButtonStyle);
//
//        menuTable.add(fileButton).uniformX().fillX();
//        menuTable.add(new Image(seperator)).expandY().fillY().width(1);
//        menuTable.add(toolsButton).uniformX().fillX();
//        menuTable.add(new Image(seperator)).expandY().fillY().width(1);
//
//        return menuTable;
//    }
//
//    protected Table createInputPane(){
//        Table paneTable=new Table();
//        paneTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#3e3e3e")));
//        paneTable.top();
//        paneTable.padLeft(10).padRight(10);
//
//        paneTable.add(new UILabel("Atlases",titleStyle)).expandX().align(Align.left).height(titleHeight);
//        paneTable.row();
//
//        buttonGroup=new UIButtonGroup<>();
//        buttonGroup.setMinCheckCount(0);
//        buttonGroup.setMaxCheckCount(0);
//
//        Table atlasTable=createAtlasSection();
//        paneTable.add(atlasTable).expand().fill();
//        paneTable.row();
//
//        Table actionTable=createActionSection();
//        paneTable.add(actionTable).expandX().fillX();
//        return paneTable;
//    }
//
//    protected Table createAtlasSection(){
//        Table atlasTable=new Table();
//        atlasTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#555555")));
//
//        BoxDrawable seperator=new BoxDrawable().fillSolid(Color.valueOf("#000000"));
//        Table atlasMenuTable=new Table();
//        atlasMenuTable.setBackground(new BoxDrawable().fillNone().outline(1,Color.valueOf("#000000")));
//        atlasMenuTable.left();
//
//
//        float iconHeight=submenuButtonStyle.font.getLineHeight();
//
//        UITextButton addFolderButton=new UITextButton(getIcon("ic_add_folder",iconHeight),"Add Folder",submenuButtonStyle);
//        UITextButton addFilesButton=new UITextButton(getIcon("ic_add_file",iconHeight),"Add Files",submenuButtonStyle);
//        UITextButton deleteButton=new UITextButton(getIcon("ic_delete",iconHeight),"Delete",submenuButtonStyle){
//            @Override
//            public void act(float delta) {
//                super.act(delta);
//                setDisabled(buttonGroup.getAllChecked().size<=0);
//            }
//        };
//        deleteButton.setDisabled(true);
//        UITextButton deleteAllButton=new UITextButton(getIcon("ic_delete",iconHeight),"Delete All",submenuButtonStyle);
//
//        atlasMenuTable.add(addFolderButton).uniformX().fillX();
//        atlasMenuTable.add(new Image(seperator)).expandY().fillY().width(1);
//        atlasMenuTable.add(addFilesButton).uniformX().fillX();
//        atlasMenuTable.add(new Image(seperator)).expandY().fillY().width(1);
//        atlasMenuTable.add(deleteButton).uniformX().fillX();
//        atlasMenuTable.add(new Image(seperator)).expandY().fillY().width(1);
//        atlasMenuTable.add(deleteAllButton).uniformX().fillX();
//        atlasMenuTable.add(new Image(seperator)).expandY().fillY().width(1);
//
//        atlasTable.add(atlasMenuTable).expandX().fillX();
//        atlasTable.row();
//        Table filesTable=new Table();
//        filesTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#7a7a7a")));
//        filesTable.top().left();
//        Table scrollPaneTable=new Table();
//        scrollPaneTable.top();
//        ScrollPane scrollPane=new ScrollPane(scrollPaneTable);
//        scrollPane.setScrollingDisabled(true,false);
//        filesTable.add(scrollPane).expand().fill();
//        filesTable.row();
//        atlasTable.add(filesTable).expand().fill();
//
//
//        FileOptions fileOptions=new FileOptions();
//        fileOptions.directory= Gdx.files.local("");
//        fileOptions.filters=FileFilter.createSingle("SVG File","svg");
//        FileChooser.FileChooserCallback callback=new FileChooser.FileChooserCallback() {
//            @Override
//            public void canceled() {
//                Gdx.app.error("File Canceled","");
//            }
//
//            @Override
//            public void selected(Array<FileHandle> fileHandles) {
//                Gdx.app.error("File Selected",fileHandles.toString());
//                for (FileHandle fileHandle:fileHandles){
//                    if(fileHandle.isDirectory()){
//                        FileHandle[] files=fileHandle.list(".svg");
//                        for (FileHandle svgFileHandle:files){
//                            addFile(scrollPaneTable,svgFileHandle);
//                        }
//                    }else {
//                        addFile(scrollPaneTable,fileHandle);
//                    }
//                }
//            }
//
//            @Override
//            public void error(String error) {
//                Gdx.app.error("File Error",error);
//            }
//        };
//
//
//        addFolderButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (addFilesButton.isDisabled())return;
//                fileOptions.action= FileAction.OpenFolder;
//                platformHandler.fileChooser.open(fileOptions,callback);
//            }
//        });
//
//
//        addFilesButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (addFilesButton.isDisabled())return;
//                fileOptions.action= FileAction.OpenFile;
//                platformHandler.fileChooser.open(fileOptions,callback);
//            }
//        });
//
//        deleteButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                event.handle();
//                if (deleteButton.isDisabled())return;
//                Array<SVGFileButton> svgFileButtons=new Array<>(buttonGroup.getAllChecked());
//                if (svgFileButtons.size>0){
//                    for (SVGFileButton svgFileButton:svgFileButtons){
//                        Cell<SVGFileButton> cell=scrollPaneTable.getCell(svgFileButton);
//                        if (cell!=null){
//                            buttonGroup.remove(svgFileButton);
//                            cell.setActor(null).pad(0).size(0,0);
//                        }
//                    }
//                }
//            }
//        });
//
//        deleteAllButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                event.handle();
//                if (deleteAllButton.isDisabled())return;
//                Array<SVGFileButton> svgFileButtons=new Array<>(buttonGroup.getButtons());
//                if (svgFileButtons.size>0){
//                    for (SVGFileButton svgFileButton:svgFileButtons){
//                        Cell<SVGFileButton> cell=scrollPaneTable.getCell(svgFileButton);
//                        if (cell!=null){
//                            buttonGroup.remove(svgFileButton);
//                            cell.setActor(null).pad(0).size(0,0);
//                        }
//                    }
//                }
//            }
//        });
//
//        return atlasTable;
//    }
//
//    protected Table createActionSection(){
//        Table actionTable=new Table();
////        actionTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#555555")));
//
//
//
//        float iconHeight=submenuButtonStyle.font.getLineHeight();
//
//        UITextButton createButton=new UITextButton(getIcon("ic_create",iconHeight),"Create",actionButtonStyle){
//            @Override
//            public void act(float delta) {
//                super.act(delta);
//                setDisabled(buttonGroup.getButtons().size<=0);
//            }
//        };
//        UITextButton saveButton=new UITextButton(getIcon("ic_save",iconHeight),"Save",actionButtonStyle){
//            @Override
//            public void act(float delta) {
//                super.act(delta);
//                setDisabled(buttonGroup.getButtons().size<=0);
//            }
//        };
//
//        actionTable.add(createButton).uniformX().fillX().pad(20);
//        actionTable.add(saveButton).uniformX().fillX().pad(20);
//
//        createButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                event.handle();
//                if (createButton.isDisabled())return;
//                createAtlas();
//            }
//        });
//
//        saveButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                event.handle();
//                if (saveButton.isDisabled())return;
//                Array<SVGFileButton> buttonArray=buttonGroup.getButtons();
//                List<FileHandle> svgFiles=new ArrayList<>();
//                for (SVGFileButton button:buttonArray){
//                    svgFiles.add(button.fileHandle);
//                }
//                FileOptions fileOptions=new FileOptions();
//                fileOptions.directory= Gdx.files.local("");
//                fileOptions.filters=FileFilter.createSingle("SVG File","svg");
//                fileOptions.action= FileAction.OpenFolder;
//                FileChooser.FileChooserCallback callback=new FileChooser.FileChooserCallback() {
//                    @Override
//                    public void canceled() {
//                        Gdx.app.error("File Canceled","");
//                    }
//
//                    @Override
//                    public void selected(Array<FileHandle> fileHandles) {
//                        Gdx.app.error("File Selected",fileHandles.toString());
//                        if (fileHandles.size>0){
//                            FileHandle fileHandle=fileHandles.get(0);
//                            String fileName="ui";
//                            if (fileHandle.isDirectory()){
//                                try {
//                                    SVGWriter svgWriter=new SVGWriter();
//                                    svgWriter.combineSVG(svgFiles);
//                                    List<FileHandle> outputFiles=svgWriter.saveDocs(fileHandle,fileName);
//                                    previewTable.clear();
//                                    for (FileHandle outputFile:outputFiles){
//                                        SVGTextureAtlasGenerator atlasGenerator=svgAssetsGDX.createAtlasGenerator(1,1,false);
//                                        atlasGenerator.add(outputFile,true,1);
//                                        svgTextureAtlas=atlasGenerator.generateAtlas();
//                                        SVGTextureAtlasPage[] pages=svgTextureAtlas.getPages();
//                                        previewTable.clear();
//                                        for (SVGTextureAtlasPage page:pages){
//                                            SVGTextureAtlasRegion[] regions=page.getRegions();
//                                            for (SVGTextureAtlasRegion region:regions){
//                                                Image image=new Image(region);
//                                                image.setScaling(Scaling.fillY);
//                                                previewTable.add(image).pad(20);
//                                            }
//                                        }
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//
//
//
//
//                        }
//                    }
//
//                    @Override
//                    public void error(String error) {
//                        Gdx.app.error("File Error",error);
//                    }
//                };
//
//                platformHandler.fileChooser.open(fileOptions,callback);
//
//            }
//        });
//
//        return actionTable;
//    }
//
//
//    protected void addFile(Table fileTable,FileHandle fileHandle){
//        SVGFileButton svgFileButton=new SVGFileButton(fileHandle,fileButtonStyle);
//        fileTable.add(svgFileButton).expandX().fillX();
//        fileTable.row();
//        buttonGroup.add(svgFileButton);
//    }
//
//    protected void createAtlas(){
//        svgAssetsGDX.createAtlasGenerator(1,1,false);
//        SVGTextureAtlasGenerator atlasGenerator=svgAssetsGDX.createAtlasGenerator(1,2,true, SVGColor.Clear);
//        Array<SVGFileButton> svgFileButtons=buttonGroup.getButtons();
//        for (SVGFileButton svgFileButton:svgFileButtons){
//            atlasGenerator.add(svgFileButton.fileHandle,true,1.0f);
//        }
//        try{
//            svgTextureAtlas=atlasGenerator.generateAtlas();
//            SVGTextureAtlasPage[] pages=svgTextureAtlas.getPages();
//            previewTable.clear();
//            for (SVGTextureAtlasPage page:pages){
//                SVGTextureAtlasRegion[] regions=page.getRegions();
//                for (SVGTextureAtlasRegion region:regions){
//
//                    Image image=new Image(region);
//                    image.setScaling(Scaling.fillY);
//                    previewTable.add(image).pad(20);
//                }
//            }
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//
//    protected Table createPreviewPane(){
//        Table previewPane=new Table();
//
//        previewPane.pad(20);
//
//        previewTable=new Table();
//        ScrollPane scrollPane=new ScrollPane(previewTable);
//        scrollPane.setScrollingDisabled(false,true);
//
//        previewPane.add(scrollPane).expand().fill();
//
//
//        return previewPane;
//    }
//
//
//    protected Image getIcon(String name,float height){
//        TextureRegionDrawable iconDrawable= Styles.getDrawableRespectToHeight(ResourceUtils.getInstance().uiAssets.findTexture(name),height);
//        return  new Image(iconDrawable);
//    }
//
//    protected Image getIcon(FileHandle fileHandle,float height){
//        SVGTexture svgTexture=ResourceUtils.getInstance().uiAssets.getSvgAssetsGDX().createTexture(fileHandle);
//        TextureRegionDrawable iconDrawable=Styles.getDrawableRespectToHeight(svgTexture,height);
//        return  new Image(iconDrawable);
//    }
//
//
//    protected class SVGFileButton extends UITextButton{
//        private FileHandle fileHandle;
//        public SVGFileButton(FileHandle fileHandle,UITextButtonStyle style){
//            super(getIcon(fileHandle,style.font.getCapHeight()),fileHandle.path(),style);
//            this.fileHandle=fileHandle;
//            left();
//        }
//
//        public FileHandle getFileHandle() {
//            return fileHandle;
//        }
//    }
//}
