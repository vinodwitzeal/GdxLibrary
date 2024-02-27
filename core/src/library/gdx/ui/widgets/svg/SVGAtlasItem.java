package library.gdx.ui.widgets.svg;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.mazatech.gdx.SVGTextureAtlas;
import com.mazatech.gdx.SVGTextureAtlasGenerator;
import com.mazatech.gdx.SVGTextureAtlasPage;
import com.mazatech.gdx.SVGTextureAtlasRegion;

import java.util.ArrayList;
import java.util.List;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.Font;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.screens.SVGPackerScreen;
import library.gdx.ui.styles.Styles;
import library.gdx.utils.FontUtils;

public class SVGAtlasItem extends UITextButton {
    private SVGPackerScreen screen;
    private String name;
    private SVGFilesPane outputPane;
    private List<FileHandle> outputFiles;
    private Table previewTable;

    public SVGAtlasItem(SVGPackerScreen screen, String name, SVGAtlasItemStyle style) {
        super(name, style);
        left();
        clearChildren(true);
        left();
        label=newLabel(name,new UILabel.UILabelStyle(style.font,style.fontColor));
        add(label).expandX().align(Align.left);
        this.screen=screen;
        this.name=name;
        this.outputPane=new SVGFilesPane(screen);
        this.previewTable=new Table();
    }

    @Override
    public String getName() {
        return name;
    }

    public SVGFilesPane getOutputPane() {
        return outputPane;
    }

    public Array<SVGFileItem> getAllFileItems(){
        return outputPane.getAllFileItems();
    }

    public List<FileHandle> getAllFiles(){
        List<FileHandle> fileHandles=new ArrayList<>();
        for (SVGFileItem fileItem:getAllFileItems()){
            fileHandles.add(fileItem.getFileHandle());
        }
        return fileHandles;
    }

    public void setOutputFiles(List<FileHandle> outputFiles){
        if (outputFiles==null || outputFiles.size()==0)return;
        this.outputFiles=outputFiles;
        try {
            for (FileHandle outputFile : outputFiles) {
                SVGTextureAtlasGenerator atlasGenerator = screen.svgAssets.createAtlasGenerator(1, 1, false);
                atlasGenerator.add(outputFile, true, 1);
                SVGTextureAtlas svgTextureAtlas = atlasGenerator.generateAtlas();
                SVGTextureAtlasPage[] pages = svgTextureAtlas.getPages();
                previewTable.clear();
                for (SVGTextureAtlasPage page : pages) {
                    SVGTextureAtlasRegion[] regions = page.getRegions();
                    for (SVGTextureAtlasRegion region : regions) {
                        Image image = new Image(region);
                        image.setScaling(Scaling.fillY);
                        previewTable.add(image).pad(20);
                    }
                }
            }
        }catch (Exception ignored){

        }
    }

    public Table getPreviewTable() {
        return previewTable;
    }

    public FileHandle getOutputFolder(){
        return outputPane.getSelectedFolder();
    }


    public static class SVGAtlasItemStyle extends UITextButtonStyle{

        public SVGAtlasItemStyle() {
            Font buttonFont= FontUtils.scaledFont(FontType.MEDIUM_ITALIC,FontSize.labelMedium);
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
