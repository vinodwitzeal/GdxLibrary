package  library.gdx.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pools;

/** A button is a {@link Table} with a checked state and additional {@link UIButtonStyle style} fields for pressed, unpressed, and
 * checked. Each time a button is clicked, the checked state is toggled. Being a table, a button can contain any other actors.<br>
 * <br>
 * The button's padding is set to the background drawable's padding when the background changes, overwriting any padding set
 * manually. Padding can still be set on the button's table cells.
 * <p>
 * {@link ChangeEvent} is fired when the button is clicked. Cancelling the event will restore the checked button state to what is
 * was previously.
 * <p>
 * The preferred size of the button is determined by the background and the button contents.
 * @author Nathan Sweet */
public class UIButton extends Table implements Disableable {
    private UIButtonStyle style;
    boolean isChecked, isDisabled;
    UIButtonGroup buttonGroup;
    private ClickListener clickListener;
    private boolean programmaticChangeEvents = true;


    public UIButton (UIButtonStyle style) {
        initialize();
        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());
    }

    /** Creates a button without setting the style or size. At least a style must be set before using this button. */
    public UIButton () {
        initialize();
    }

    public UIButton (@Null Drawable up) {
        this(new UIButtonStyle(up, null, null));
    }

    public UIButton (@Null Drawable up, @Null Drawable down) {
        this(new UIButtonStyle(up, down, null));
    }

    public UIButton (@Null Drawable up, @Null Drawable down, @Null Drawable checked) {
        this(new UIButtonStyle(up, down, checked));
    }

    private void initialize () {
        setTouchable(Touchable.enabled);
        addListener(clickListener = new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                if (isDisabled()) return;
                setChecked(!isChecked, true);
            }
        });
    }



    public void setChecked (boolean isChecked) {
        setChecked(isChecked, programmaticChangeEvents);
    }

    void setChecked (boolean isChecked, boolean fireEvent) {
        if (this.isChecked == isChecked) return;
        if (buttonGroup != null && !buttonGroup.canCheck(this, isChecked)) return;
        this.isChecked = isChecked;

        if (fireEvent) {
            ChangeEvent changeEvent = Pools.obtain(ChangeEvent.class);
            if (fire(changeEvent)) this.isChecked = !isChecked;
            Pools.free(changeEvent);
        }
    }

    /** Toggles the checked state. This method changes the checked state, which fires a {@link ChangeEvent} (if programmatic change
     * events are enabled), so can be used to simulate a button click. */
    public void toggle () {
        setChecked(!isChecked);
    }

    public boolean isChecked () {
        return isChecked;
    }

    public boolean isPressed () {
        return clickListener.isVisualPressed();
    }

    public boolean isOver () {
        return clickListener.isOver();
    }

    public ClickListener getClickListener () {
        return clickListener;
    }

    public boolean isDisabled () {
        return isDisabled;
    }

    /** When true, the button will not toggle {@link #isChecked()} when clicked and will not fire a {@link ChangeEvent}. */
    public void setDisabled (boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /** If false, {@link #setChecked(boolean)} and {@link #toggle()} will not fire {@link ChangeEvent}. The event will only be
     * fired only when the user clicks the button */
    public void setProgrammaticChangeEvents (boolean programmaticChangeEvents) {
        this.programmaticChangeEvents = programmaticChangeEvents;
    }

    public void setStyle (UIButtonStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;

        setBackground(getBackgroundDrawable());
    }

    /** Returns the button's style. Modifying the returned style may not have an effect until {@link #setStyle(UIButtonStyle)} is
     * called. */
    public UIButtonStyle getStyle () {
        return style;
    }

    /** @return May be null. */
    public @Null UIButtonGroup getButtonGroup () {
        return buttonGroup;
    }

    /** Returns appropriate background drawable from the style based on the current button state. */
    protected @Null Drawable getBackgroundDrawable () {
        if (isDisabled() && style.disabled != null) return style.disabled;
        if (isPressed()) {
            if (style.down != null) return style.down;
        }
        boolean focused = hasKeyboardFocus();
        if (isChecked()) {
            if (style.checked != null) return style.checked;
        }
        return style.up;
    }

    public void draw (Batch batch, float parentAlpha) {
        validate();

        setBackground(getBackgroundDrawable());

        float offsetX = 0, offsetY = 0;
        if (isPressed() && !isDisabled()) {
            offsetX = style.pressedOffsetX;
            offsetY = style.pressedOffsetY;
        } else if (isChecked() && !isDisabled()) {
            offsetX = style.checkedOffsetX;
            offsetY = style.checkedOffsetY;
        } else {
            offsetX = style.unpressedOffsetX;
            offsetY = style.unpressedOffsetY;
        }
        boolean offset = offsetX != 0 || offsetY != 0;

        Array<Actor> children = getChildren();
        if (offset) {
            for (int i = 0; i < children.size; i++)
                children.get(i).moveBy(offsetX, offsetY);
        }
        super.draw(batch, parentAlpha);
        if (offset) {
            for (int i = 0; i < children.size; i++)
                children.get(i).moveBy(-offsetX, -offsetY);
        }

        Stage stage = getStage();
        if (stage != null && stage.getActionsRequestRendering() && isPressed() != clickListener.isPressed())
            Gdx.graphics.requestRendering();
    }

    public float getPrefWidth () {
        float width = super.getPrefWidth();
        if (style.up != null) width = Math.max(width, style.up.getMinWidth());
        if (style.down != null) width = Math.max(width, style.down.getMinWidth());
        if (style.checked != null) width = Math.max(width, style.checked.getMinWidth());
        return width;
    }

    public float getPrefHeight () {
        float height = super.getPrefHeight();
        if (style.up != null) height = Math.max(height, style.up.getMinHeight());
        if (style.down != null) height = Math.max(height, style.down.getMinHeight());
        if (style.checked != null) height = Math.max(height, style.checked.getMinHeight());
        return height;
    }

    public float getMinWidth () {
        return getPrefWidth();
    }

    public float getMinHeight () {
        return getPrefHeight();
    }

    /** The style for a button, see {@link UIButton}.
     * @author mzechner */
    static public class UIButtonStyle {
        public @Null Drawable up, down,checked, disabled;
        public float pressedOffsetX, pressedOffsetY, unpressedOffsetX, unpressedOffsetY, checkedOffsetX, checkedOffsetY;

        public UIButtonStyle () {
        }

        public UIButtonStyle (@Null Drawable up, @Null Drawable down, @Null Drawable checked) {
            this.up = up;
            this.down = down;
            this.checked = checked;
        }

        public UIButtonStyle (UIButtonStyle style) {
            up = style.up;
            down = style.down;
            checked = style.checked;
            disabled = style.disabled;

            pressedOffsetX = style.pressedOffsetX;
            pressedOffsetY = style.pressedOffsetY;
            unpressedOffsetX = style.unpressedOffsetX;
            unpressedOffsetY = style.unpressedOffsetY;
            checkedOffsetX = style.checkedOffsetX;
            checkedOffsetY = style.checkedOffsetY;
        }
    }
}
