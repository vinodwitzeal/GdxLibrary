package library.gdx.ui.screens;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mazatech.gdx.SVGAssetsGDX;
import com.mazatech.gdx.SVGTextureAtlas;
import com.mazatech.gdx.SVGTextureAtlasGenerator;

import library.gdx.SceneManager;
import library.gdx.drawables.box.BoxDrawable;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.widgets.svg.SVGAtlasItem;
import library.gdx.ui.widgets.svg.SVGButtonPane;
import library.gdx.ui.widgets.svg.SVGIconButton;
import library.gdx.ui.widgets.svg.SVGInputPane;
import library.gdx.ui.widgets.svg.SVGStyle;
import library.gdx.utils.ResourceUtils;
import library.gdx.utils.svg.SVGWriter;

public class SVGPackerScreen extends UIScreen {
    public SVGStyle style;
    public SVGAssetsGDX svgAssets;
    private Table outputTable = new Table();
    private Table previewTable;

    public SVGPackerScreen(SceneManager sceneManager) {
        super(sceneManager, "SVGPackerScreen");
        this.style = new SVGStyle();
        svgAssets = ResourceUtils.getInstance().uiAssets.getSvgAssetsGDX();
    }

    @Override
    protected void buildUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.left();
        mainTable.setBackground(new BoxDrawable().fillSolid(Color.valueOf("#222222")));

        outputTable = new Table();
        outputTable.top();
        Table leftTable = new Table();
        leftTable.top();
        leftTable.pad(5);
        SVGInputPane svgInputPane = new SVGInputPane(this) {
            @Override
            public void onItemSelected(SVGAtlasItem svgAtlasItem) {
                super.onItemSelected(svgAtlasItem);
                outputTable.clear();
                if (svgAtlasItem != null) {
                    outputTable.add(svgAtlasItem.getOutputPane()).expand().fill();
                }
                setPreview(svgAtlasItem);
            }
        };
        leftTable.add(svgInputPane).expandX().fillX().height(height * 0.3f).padBottom(5);
        leftTable.row();
        leftTable.add(outputTable).expand().fill();
        leftTable.row();

        SVGButtonPane svgButtonPane = new SVGButtonPane();
        svgButtonPane.center();

        UITextButton.UITextButtonStyle menuButtonStyle = style.buttonStyle;
        float iconHeight = menuButtonStyle.font.getLineHeight();


        UITextButton packButton = new SVGIconButton(style.getIcon("inventory_2", iconHeight), "Pack", menuButtonStyle) {
            @Override
            public void act(float delta) {
                super.act(delta);
                SVGAtlasItem svgAtlasItem = svgInputPane.getSelectedItem();
                if (svgAtlasItem != null) {
                    setDisabled(!canPackSVGAtlasItem(svgAtlasItem));
                } else {
                    setDisabled(true);
                }
            }
        };
        UITextButton packAllButton = new SVGIconButton(style.getIcon("inventory", iconHeight), "Pack All", menuButtonStyle) {
            @Override
            public void act(float delta) {
                super.act(delta);
                if (svgInputPane.getAllItems().size > 0) {
                    boolean disabled = false;
                    for (SVGAtlasItem atlasItem : svgInputPane.getAllItems()) {
                        if (!canPackSVGAtlasItem(atlasItem)) {
                            disabled = true;
                            break;
                        }
                    }
                    setDisabled(disabled);
                } else {
                    setDisabled(true);
                }
            }
        };

        svgButtonPane.addButton(packButton);
        svgButtonPane.addButton(packAllButton);

        packButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (packButton.isDisabled()) return;
                packSVGAtlasItem(svgInputPane.getSelectedItem());
                setPreview(svgInputPane.getSelectedItem());
            }
        });
        packAllButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (packAllButton.isDisabled()) return;
                for (SVGAtlasItem atlasItem : svgInputPane.getAllItems()) {
                    packSVGAtlasItem(atlasItem);
                }
                setPreview(svgInputPane.getSelectedItem());
            }
        });

        leftTable.add(svgButtonPane).expandX().fillX().padTop(20).padBottom(20);

        mainTable.add(leftTable).width(width * 0.3f).expandY().fillY();

        previewTable = new Table();
        mainTable.add(previewTable).expand().fill();
        mainTable.row();

        stage.addActor(mainTable);
    }


    private boolean canPackSVGAtlasItem(SVGAtlasItem atlasItem) {
        boolean canPack = false;
        if (atlasItem.getOutputFolder() != null && atlasItem.getAllFileItems().size > 1) {
            canPack = true;
        }
        return canPack;
    }

    private void packSVGAtlasItem(SVGAtlasItem atlasItem) {
        if (atlasItem == null || !canPackSVGAtlasItem(atlasItem)) return;
        try {
            SVGWriter svgWriter = new SVGWriter();
            svgWriter.combineSVG(atlasItem.getAllFiles());
            atlasItem.setOutputFiles(svgWriter.saveDocs(atlasItem.getOutputFolder(), atlasItem.getName()));
        } catch (Exception ignored) {

        }
    }

    private void setPreview(SVGAtlasItem atlasItem) {
        previewTable.clear();
        if (atlasItem==null)return;
        previewTable.add(atlasItem.getPreviewTable()).expand().fill();
    }


}
