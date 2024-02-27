package library.gdx.ui.textfield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Null;

import library.gdx.fonts.Font;

public class UIInputField extends Table implements Disableable {
    protected UITextField textField;
    private UIInputFieldStyle style;

    private Cell<Actor> leadCell,tailCell;
    private Input.OnscreenKeyboardType keyboardType= Input.OnscreenKeyboardType.Default;

    public UIInputField(String message,boolean disableTextInput,UIInputFieldStyle style){
        leadCell=add((Actor)null);
        textField=new UITextField("",getTextFieldStyle(style));
        textField.setMessageText(message);
        textField.setOnscreenKeyboard(new UITextField.OnscreenKeyboard() {
            @Override
            public void show(boolean visible) {
                Gdx.input.setOnscreenKeyboardVisible(visible,keyboardType);
            }
        });
        if (disableTextInput){
            textField.setTouchable(Touchable.disabled);
            setTouchable(Touchable.enabled);
            addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    event.handle();
                    onClicked();
                }
            });
        }
        add(textField).expand().fill();
        setStyle(style);
        tailCell=add((Actor)null);
    }

    public void setMessageText(String message){
        textField.setMessageText(message);
    }

    public void setText(String text){
        textField.setText(text);
    }

    public void setKeyboardType(Input.OnscreenKeyboardType keyboardType) {
        this.keyboardType = keyboardType;
    }

    protected void onClicked(){

    }

    private UITextField.UITextFieldStyle getTextFieldStyle(UIInputFieldStyle style){
        UITextField.UITextFieldStyle textFieldStyle=new UITextField.UITextFieldStyle();
        textFieldStyle.font=style.font;
        textFieldStyle.fontColor=style.fontColor;
        textFieldStyle.focusedFontColor=style.focusedFontColor;
        textFieldStyle.disabledFontColor=style.disabledFontColor;
        textFieldStyle.messageFont=style.messageFont;
        textFieldStyle.messageFontColor=style.messageFontColor;
        textFieldStyle.cursor=style.cursor;
        textFieldStyle.selection=style.selection;
        return textFieldStyle;
    }

    public void setStyle(UIInputFieldStyle style){
        this.style=style;
        textField.setStyle(getTextFieldStyle(style));
        updateBackground();
    }

    public Cell<Actor> setLead(Actor actor){
        if (actor==null){
            removeLead();
            return null;
        }
        return leadCell.setActor(actor).padRight(style.font.getCapHeight()*0.5f);
    }

    public void removeLead(){
        leadCell.setActor(null).size(0,0).pad(0);
    }

    public Cell<Actor> setTail(Actor actor){
        if (actor==null){
            removeTail();
            return null;
        }
        return tailCell.setActor(actor).padLeft(style.font.getCapHeight()*0.5f);
    }

    public void removeTail(){
        tailCell.setActor(null).size(0,0).pad(0);
    }

    public Cell getTailCell() {
        return tailCell;
    }

    public Cell getLeadCell() {
        return leadCell;
    }

    protected void updateBackground(){
        if (textField.hasKeyboardFocus()){
            setBackground(style.focusedBackground);
        }else if (isDisabled()){
            setBackground(style.disabledBackground);
        }else {
            setBackground(style.background);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateBackground();
        super.draw(batch, parentAlpha);
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        textField.setDisabled(isDisabled);
    }

    @Override
    public boolean isDisabled() {
        return textField.isDisabled();
    }

    public static class UIInputFieldStyle{
        public Font font;
        public Color fontColor;
        public @Null Color focusedFontColor, disabledFontColor;
        public @Null Drawable background, focusedBackground, disabledBackground, cursor, selection;
        public @Null Font messageFont;
        public @Null Color messageFontColor;
    }
}
