package library.gdx.ui.list;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectSet;

/**
 * A list (aka list box) displays textual items and highlights the currently selected item.
 * <p>
 * {@link ChangeEvent} is fired when the list selection changes.
 * <p>
 * The preferred size of the list is determined by the text bounds of the items and the size of the {@link com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle#selection}.
 *
 * @author mzechner
 * @author Nathan Sweet
 */
public class WidgetList extends Widget implements Cullable {
    WidgetListStyle style;
    final Array<WidgetListItem> items = new Array<WidgetListItem>();
    ArraySelection<WidgetListItem> selection = new ArraySelection<WidgetListItem>(items);
    private Rectangle cullingArea;
    private float prefWidth, prefHeight;
    float itemHeight;
    private int alignment = Align.left;
    int pressedIndex = -1, overIndex = -1;
    private InputListener keyListener;
    boolean typeToSelect;

    public WidgetList(WidgetListStyle style) {
        selection.setActor(this);
        selection.setRequired(true);

        setStyle(style);
        setSize(getPrefWidth(), getPrefHeight());

        addListener(keyListener = new InputListener() {
            long typeTimeout;
            String prefix;

            public boolean keyDown(InputEvent event, int keycode) {
                if (items.isEmpty()) return false;
                int index;
                switch (keycode) {
                    case Keys.A:
                        if (UIUtils.ctrl() && selection.getMultiple()) {
                            selection.clear();
                            selection.addAll(items);
                            return true;
                        }
                        break;
                    case Keys.HOME:
                        setSelectedIndex(0);
                        return true;
                    case Keys.END:
                        setSelectedIndex(items.size - 1);
                        return true;
                    case Keys.DOWN:
                        index = items.indexOf(getSelected(), false) + 1;
                        if (index >= items.size) index = 0;
                        setSelectedIndex(index);
                        return true;
                    case Keys.UP:
                        index = items.indexOf(getSelected(), false) - 1;
                        if (index < 0) index = items.size - 1;
                        setSelectedIndex(index);
                        return true;
                    case Keys.ESCAPE:
                        if (getStage() != null) getStage().setKeyboardFocus(null);
                        return true;
                }
                return false;
            }
        });

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return true;
                if (selection.isDisabled()) return true;
                if (getStage() != null) getStage().setKeyboardFocus(WidgetList.this);
                if (items.size == 0) return true;
                int index = getItemIndexAt(y);
                if (index == -1) return true;
                selection.choose(items.get(index));
                pressedIndex = index;
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer != 0 || button != 0) return;
                pressedIndex = -1;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                overIndex = getItemIndexAt(y);
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                overIndex = getItemIndexAt(y);
                return false;
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == 0) pressedIndex = -1;
                if (pointer == -1) overIndex = -1;
            }
        });
    }

    public void setStyle(WidgetListStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;
        invalidateHierarchy();
    }

    /**
     * Returns the list's style. Modifying the returned style may not have an effect until {@link #setStyle(WidgetListStyle)} is
     * called.
     */
    public WidgetListStyle getStyle() {
        return style;
    }

    public void layout() {
        Drawable selectedDrawable = style.selection;


        prefWidth = 0;
        for (int i = 0; i < items.size; i++) {
            WidgetListItem item = items.get(i);
            item.pack();
            prefWidth = Math.max(item.getWidth(), prefWidth);
            itemHeight = Math.max(item.getHeight(), itemHeight);
        }
        itemHeight += selectedDrawable.getTopHeight() + selectedDrawable.getBottomHeight();
        prefWidth += selectedDrawable.getLeftWidth() + selectedDrawable.getRightWidth();

        Gdx.app.error("Item Height", String.valueOf(itemHeight));

        prefHeight = items.size * itemHeight;

        Gdx.app.error("Pref Height", String.valueOf(prefHeight));

        Drawable background = style.background;
        if (background != null) {
            prefWidth = Math.max(prefWidth + background.getLeftWidth() + background.getRightWidth(), background.getMinWidth());
            prefHeight = Math.max(prefHeight + background.getTopHeight() + background.getBottomHeight(), background.getMinHeight());
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        validate();

        drawBackground(batch, parentAlpha);
        Drawable selectedDrawable = style.selection;

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX(), y = getY(), width = getWidth(), height = getHeight();
        float itemY = height;

        for (int i = 0; i < items.size; i++) {
            if (cullingArea == null || (itemY - itemHeight <= cullingArea.y + cullingArea.height && itemY >= cullingArea.y)) {
                WidgetListItem item = items.get(i);
                boolean selected = selection.contains(item);
                Drawable drawable = null;
                if (pressedIndex == i && style.down != null)
                    drawable = style.down;
                else if (selected) {
                    drawable = selectedDrawable;
                } else if (overIndex == i && style.over != null) //
                    drawable = style.over;
                drawSelection(batch, drawable, x, y + itemY - itemHeight, width, itemHeight);
                drawItem(batch, parentAlpha, item, x, y + itemY - itemHeight, width, itemHeight);
            } else if (itemY < cullingArea.y) {
                break;
            }
            itemY -= itemHeight;
        }
    }

    protected void drawSelection(Batch batch, @Null Drawable drawable, float x, float y, float width, float height) {
        if (drawable != null) drawable.draw(batch, x, y, width, height);
    }

    /**
     * Called to draw the background. Default implementation draws the style background drawable.
     */
    protected void drawBackground(Batch batch, float parentAlpha) {
        if (style.background != null) {
            Color color = getColor();
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
            style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
    }

    protected void drawItem(Batch batch, float parentAlpha, WidgetListItem item, float x, float y, float width, float height) {
        float itemX = item.getX();
        float itemY = item.getY();
        float itemWidth = item.getWidth();
        float itemHeight = item.getHeight();
        item.setPosition(x, y);
        item.setSize(width, height);
        item.draw(batch, parentAlpha);
        item.setPosition(itemX, itemY);
        item.setSize(itemWidth, itemHeight);
    }

    public ArraySelection<WidgetListItem> getSelection() {
        return selection;
    }

    public void setSelection(ArraySelection<WidgetListItem> selection) {
        this.selection = selection;
    }

    /**
     * Returns the first selected item, or null.
     */
    public @Null WidgetListItem getSelected() {
        return selection.first();
    }

    /**
     * Sets the selection to only the passed item, if it is a possible choice.
     *
     * @param item May be null.
     */
    public void setSelected(@Null WidgetListItem item) {
        if (items.contains(item, false))
            selection.set(item);
        else if (selection.getRequired() && items.size > 0)
            selection.set(items.first());
        else
            selection.clear();
    }

    /**
     * @return The index of the first selected item. The top item has an index of 0. Nothing selected has an index of -1.
     */
    public int getSelectedIndex() {
        ObjectSet<WidgetListItem> selected = selection.items();
        return selected.size == 0 ? -1 : items.indexOf(selected.first(), false);
    }

    /**
     * Sets the selection to only the selected index.
     *
     * @param index -1 to clear the selection.
     */
    public void setSelectedIndex(int index) {
        if (index < -1 || index >= items.size)
            throw new IllegalArgumentException("index must be >= -1 and < " + items.size + ": " + index);
        if (index == -1) {
            selection.clear();
        } else {
            selection.set(items.get(index));
        }
    }

    /**
     * @return May be null.
     */
    public WidgetListItem getOverItem() {
        return overIndex == -1 ? null : items.get(overIndex);
    }

    /**
     * @return May be null.
     */
    public WidgetListItem getPressedItem() {
        return pressedIndex == -1 ? null : items.get(pressedIndex);
    }

    /**
     * @return null if not over an item.
     */
    public @Null WidgetListItem getItemAt(float y) {
        int index = getItemIndexAt(y);
        if (index == -1) return null;
        return items.get(index);
    }

    /**
     * @return -1 if not over an item.
     */
    public int getItemIndexAt(float y) {
        float height = getHeight();
        Drawable background = this.style.background;
        if (background != null) {
            height -= background.getTopHeight() + background.getBottomHeight();
            y -= background.getBottomHeight();
        }
        int index = (int) ((height - y) / itemHeight);
        if (index < 0 || index >= items.size) return -1;
        return index;
    }

    public void setItems(WidgetListItem... newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        items.clear();
        items.addAll(newItems);
        overIndex = -1;
        pressedIndex = -1;
        selection.validate();

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight())
            invalidateHierarchy();
    }

    public void addItem(WidgetListItem newItem){
        if (newItem == null) throw new IllegalArgumentException("newItem cannot be null.");
        if (items.contains(newItem,true))return;
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();
        items.add(newItem);
        selection.validate();

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight())
            invalidateHierarchy();
    }

    /**
     * Sets the items visible in the list, clearing the selection if it is no longer valid. If a selection is
     * {@link ArraySelection#getRequired()}, the first item is selected. This can safely be called with a (modified) array returned
     * from {@link #getItems()}.
     */
    public void setItems(Array newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

        if (newItems != items) {
            items.clear();
            items.addAll(newItems);
        }
        overIndex = -1;
        pressedIndex = -1;
        selection.validate();

        invalidate();
        if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight())
            invalidateHierarchy();
    }

    public void clearItems() {
        if (items.size == 0) return;
        items.clear();
        overIndex = -1;
        pressedIndex = -1;
        selection.clear();
        invalidateHierarchy();
    }

    /**
     * Returns the internal items array. If modified, {@link #setItems(Array)} must be called to reflect the changes.
     */
    public Array<WidgetListItem> getItems() {
        return items;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public float getPrefWidth() {
        validate();
        return prefWidth;
    }

    public float getPrefHeight() {
        validate();
        return prefHeight;
    }

    public String toString(WidgetListItem object) {
        return object.toString();
    }

    public void setCullingArea(@Null Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    /**
     * @return May be null.
     * @see #setCullingArea(Rectangle)
     */
    public Rectangle getCullingArea() {
        return cullingArea;
    }

    /**
     * Sets the horizontal alignment of the list items.
     *
     * @param alignment See {@link Align}.
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setTypeToSelect(boolean typeToSelect) {
        this.typeToSelect = typeToSelect;
    }

    public InputListener getKeyListener() {
        return keyListener;
    }

    /**
     * The style for a list, see {@link com.badlogic.gdx.scenes.scene2d.ui.List}.
     *
     * @author mzechner
     * @author Nathan Sweet
     */
    static public class WidgetListStyle {
        public Drawable selection;
        public @Null Drawable down, over, background;

        public WidgetListStyle() {
        }

        public WidgetListStyle(Drawable selection) {
            this.selection = selection;
        }

        public WidgetListStyle(WidgetListStyle style) {
            selection = style.selection;

            down = style.down;
            over = style.over;
            background = style.background;
        }
    }
}
