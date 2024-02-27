package library.gdx.ui.dialogs.svg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import library.gdx.drawables.box.BoxDrawable;
import library.gdx.fonts.Font;
import library.gdx.fonts.FontSize;
import library.gdx.fonts.FontType;
import library.gdx.ui.buttons.UITextButton;
import library.gdx.ui.dialogs.UIDialog;
import library.gdx.ui.labels.UILabel;
import library.gdx.ui.screens.UIScreen;
import library.gdx.ui.styles.Styles;
import library.gdx.ui.textfield.UITextField;
import library.gdx.utils.FontUtils;
import library.gdx.utils.GameUtils;

public class SVGInputDialog extends UIDialog {
    private String title;
    private String message;
    public SVGInputDialog(UIScreen screen,String title,String message) {
        super(screen);
        this.title=title;
        this.message=message;
        buildUI();
    }

    @Override
    protected void buildUI() {
        Table contentTable=new Table();
        float radius=density*10.0f;
        contentTable.setBackground(new BoxDrawable().radius(radius).fillSolid(Color.valueOf("#555555")).outline(2,Color.valueOf("#111111")));

        Table headingTable=new Table();
        headingTable.setBackground(new BoxDrawable().radius(radius,radius,0,0).fillSolid(Color.valueOf("#000000")));
        UILabel.UILabelStyle headingLabelStyle=new UILabel.UILabelStyle();
        headingLabelStyle.font= FontUtils.scaledFont(FontType.MEDIUM,FontSize.labelLarge);
        headingLabelStyle.fontColor=Color.valueOf("#FFFFFF");

        headingTable.add(new UILabel(title,headingLabelStyle)).pad(4*density);

        contentTable.add(headingTable).expandX().fillX().padBottom(80);
        contentTable.row();

        Font textFieldFont=FontUtils.scaledFont(FontType.MEDIUM, FontSize.labelMedium);
        float vertical=textFieldFont.getCapHeight();
        float horizontal=textFieldFont.getCapHeight()*1.5f;

        BoxDrawable boxDrawable=new BoxDrawable().fillNone().radius(radius*0.5f).outline(2,Color.valueOf("#FFFFFF"));
        Styles.setDrawablePadding(boxDrawable,horizontal,vertical);


        UITextField.UITextFieldStyle textFieldStyle=new UITextField.UITextFieldStyle();
        textFieldStyle.font=textFieldFont;
        textFieldStyle.background=boxDrawable.tint(new Color(1,1,1,0.75f));
        textFieldStyle.fontColor=new Color(1,1,1,0.75f);
        textFieldStyle.focusedFontColor=new Color(1,1,1,1);
        textFieldStyle.disabledFontColor=new Color(1,1,1,0.5f);
        textFieldStyle.focusedBackground=new BoxDrawable(boxDrawable).fillSolid(Color.valueOf("#000000"));
        textFieldStyle.disabledBackground=boxDrawable.tint(new Color(1,1,1,0.5f));
        BoxDrawable cursor=new BoxDrawable().fillSolid(textFieldStyle.focusedFontColor);
        cursor.setMinSize(2,textFieldFont.getCapHeight());
        textFieldStyle.cursor=cursor;

        UITextField textField=new UITextField("",textFieldStyle);
        textField.setMessageText(message);

        contentTable.add(textField).width(Math.min(width,height)*0.5f).pad(40);
        contentTable.row();



        BoxDrawable cancelButtonBackground= Styles.createBoxDrawable(null,Color.valueOf("#FFFFFF"),radius*0.5f,2);
        Styles.setDrawablePadding(cancelButtonBackground,horizontal,vertical);
        UITextButton.UITextButtonStyle cancelButtonStyle =new UITextButton.UITextButtonStyle();
        cancelButtonStyle.up=cancelButtonBackground;
        cancelButtonStyle.down=cancelButtonBackground.tint(Color.valueOf("#A0A0A0"));
        cancelButtonStyle.checked=cancelButtonBackground;
        cancelButtonStyle.font=textFieldFont;
        cancelButtonStyle.fontColor=Color.valueOf("#FFFFFF");
        cancelButtonStyle.downFontColor=new Color(1,1,1,0.5f);
        cancelButtonStyle.disabled=cancelButtonBackground.tint(new Color(1,1,1,0.75f));
        cancelButtonStyle.disabledFontColor=new Color(cancelButtonStyle.fontColor).mul(1,1,1,0.75f);


        BoxDrawable submitButtonBackground= Styles.createBoxDrawable(Color.valueOf("#222222"),Color.valueOf("#FFFFFF"),radius*0.5f,2);
        Styles.setDrawablePadding(submitButtonBackground,horizontal,vertical);
        UITextButton.UITextButtonStyle submitButtonStyle =new UITextButton.UITextButtonStyle();
        submitButtonStyle.up=submitButtonBackground;
        submitButtonStyle.down=submitButtonBackground.tint(Color.valueOf("#A0A0A0"));
        submitButtonStyle.checked=submitButtonBackground;
        submitButtonStyle.font=textFieldFont;
        submitButtonStyle.fontColor=Color.valueOf("#FFFFFF");
        submitButtonStyle.downFontColor=new Color(1,1,1,0.5f);
        submitButtonStyle.disabled=submitButtonBackground.tint(new Color(1,1,1,0.75f));
        submitButtonStyle.disabledFontColor=new Color(submitButtonStyle.fontColor).mul(1,1,1,0.75f);


        Table buttonTable=new Table();


        UITextButton submitButton=new UITextButton("SUBMIT",submitButtonStyle);
        UITextButton cancelButton=new UITextButton("CANCEL",cancelButtonStyle);

        buttonTable.add(submitButton).uniform().expandX();
        buttonTable.add(cancelButton).uniform().expandX();

        contentTable.add(buttonTable).expandX().fillX().pad(20);

        submitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (submitButton.isDisabled())return;
                hide();
                onSubmit(textField.getText());
            }
        });

        cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                event.handle();
                if (cancelButton.isDisabled())return;
                hide();
                onCancel();
            }
        });


        contentTable.pack();
        contentTable.setPosition((width-contentTable.getWidth())*0.5f,(height-contentTable.getHeight())*0.5f);
        setContent(contentTable);
    }


    public void onSubmit(String inputText){

    }

    public void onCancel(){

    }
}
