package library.gdx.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;

import library.gdx.fonts.Font;
import library.gdx.ui.labels.UILabel;

/** A button with a child {@link UILabel} to display text.
 * @author Nathan Sweet */
public class UIInputButton extends UIButton {
    private Cell<Actor> leadCell,tailCell;
    private UILabel label;
    private UIInputButtonStyle style;

    public UIInputButton(@Null Actor lead,@Null Actor tail,@Null String text,UIInputButtonStyle style){
        super();
        setStyle(style);
        label = newLabel(text, new UILabel.UILabelStyle(style.font, style.fontColor));
        label.setAlignment(Align.center);
        leadCell=add();
        setLead(lead);
        add(label).expand().fill();
        tailCell=add();
        setTail(tail);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public UIInputButton (@Null String text, UIInputButtonStyle style) {
        this(null,null,text,style);
    }

    protected UILabel newLabel (String text, UILabel.UILabelStyle style) {
        return new UILabel(text, style);
    }

    public void setLead(Actor actor){
        if (actor==null){
            removeLead();
            return;
        }
        leadCell.setActor(actor).size(actor.getWidth()).height(actor.getHeight()).padRight(style.font.getCapHeight()*0.5f);
    }

    public void setTail(Actor actor){
        if (actor==null){
            removeLead();
            return;
        }
        tailCell.setActor(actor).size(actor.getWidth()).height(actor.getHeight()).padLeft(style.font.getCapHeight()*0.5f);
    }

    public void removeLead(){
        leadCell.setActor(null).size(0,0).pad(0);
    }

    public void removeTail(){
        tailCell.setActor(null).size(0,0).pad(0);
    }

    public void setStyle (UIButtonStyle style) {
        if (style == null) throw new NullPointerException("style cannot be null");
        if (!(style instanceof UIInputButtonStyle)) throw new IllegalArgumentException("style must be a TextButtonStyle.");
        this.style = (UIInputButtonStyle) style;
        super.setStyle(style);

        if (label != null) {
            UIInputButtonStyle textButtonStyle = (UIInputButtonStyle) style;
            UILabel.UILabelStyle labelStyle = label.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            label.setStyle(labelStyle);
        }
    }

    public UIInputButtonStyle getStyle () {
        return style;
    }

    /** Returns the appropriate label font color from the style based on the current button state. */
    protected @Null Color getFontColor () {
        if (isDisabled() && style.disabledFontColor != null) return style.disabledFontColor;
        if (isPressed()) {
            if (style.downFontColor != null) return style.downFontColor;
        }
        boolean focused = hasKeyboardFocus();
        if (isChecked()) {
            if (style.checkedFontColor != null) return style.checkedFontColor;
        }
        return style.fontColor;
    }

    public void draw (Batch batch, float parentAlpha) {
        label.getStyle().fontColor = getFontColor();
        super.draw(batch, parentAlpha);
    }

    public void setLabel (UILabel label) {
        if (label == null) throw new IllegalArgumentException("label cannot be null.");
        getLabelCell().setActor(label);
        this.label = label;
    }

    public UILabel getLabel () {
        return label;
    }

    public Cell<UILabel> getLabelCell () {
        return getCell(label);
    }

    public void setText (@Null String text) {
        label.setText(text);
    }

    public CharSequence getText () {
        return label.getText();
    }

    public String toString () {
        String name = getName();
        if (name != null) return name;
        String className = getClass().getName();
        int dotIndex = className.lastIndexOf('.');
        if (dotIndex != -1) className = className.substring(dotIndex + 1);
        return (className.indexOf('$') != -1 ? "TextButton " : "") + className + ": " + label.getText();
    }

    /** The style for a text button, see {@link UITextButton}.
     * @author Nathan Sweet */
    static public class UIInputButtonStyle extends UIButtonStyle {
        public Font font;
        public @Null Color fontColor, downFontColor, disabledFontColor,checkedFontColor;

        public UIInputButtonStyle () {
        }

        public UIInputButtonStyle (@Null Drawable up, @Null Drawable down, @Null Drawable checked, @Null Font font) {
            super(up, down, checked);
            this.font = font;
        }

        public UIInputButtonStyle (UIInputButtonStyle style) {
            super(style);
            font = style.font;
            if (style.fontColor != null) fontColor = new Color(style.fontColor);
            if (style.downFontColor != null) downFontColor = new Color(style.downFontColor);
            if (style.disabledFontColor != null) disabledFontColor = new Color(style.disabledFontColor);
            if (style.checkedFontColor != null) checkedFontColor = new Color(style.checkedFontColor);
        }
    }
}
