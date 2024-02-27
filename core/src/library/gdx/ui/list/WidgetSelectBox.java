package library.gdx.ui.list;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ObjectSet;

/** A select box (aka a drop-down list) allows a user to choose one of a number of values from a list. When inactive, the selected
 * value is displayed. When activated, it shows the list of values that may be selected.
 * <p>
 * {@link ChangeEvent} is fired when the selectbox selection changes.
 * <p>
 * The preferred size of the select box is determined by the maximum text bounds of the items and the size of the
 * {@link WidgetSelectBoxStyle#background}.
 * @author mzechner
 * @author Nathan Sweet */
public class WidgetSelectBox extends Widget implements Disableable {
    static final Vector2 temp = new Vector2();

    WidgetSelectBoxStyle style;
    final Array<WidgetListItem> items = new Array();
    SelectBoxScrollPane scrollPane;
    private float prefWidth, prefHeight;
    private ClickListener clickListener;
    boolean disabled;
    private int alignment = Align.left;
    boolean selectedPrefWidth;

    final ArraySelection<WidgetListItem> selection = new ArraySelection(items) {
        public boolean fireChangeEvent () {
            if (selectedPrefWidth) invalidateHierarchy();
            return super.fireChangeEvent();
        }
    };

    public WidgetSelectBox (WidgetSelectBoxStyle style) {
        setStyle(style);
        selection.setActor(this);
        selection.setRequired(true);

        scrollPane = new SelectBoxScrollPane(this);
        setSize(getPrefWidth(), getPrefHeight());

        addListener(clickListener = new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == 0 && button != 0) return false;
                if (isDisabled()) return false;
                if (scrollPane.hasParent())
                    hideScrollPane();
                else
                    showScrollPane();
                return true;
            }
        });
    }

    /** Allows a subclass to customize the scroll pane shown when the select box is open. */
    protected SelectBoxScrollPane newScrollPane () {
        return new SelectBoxScrollPane(this);
    }

    /** Set the max number of items to display when the select box is opened. Set to 0 (the default) to display as many as fit in
     * the stage height. */
    public void setMaxListCount (int maxListCount) {
        scrollPane.maxListCount = maxListCount;
    }

    /** @return Max number of items to display when the box is opened, or <= 0 to display them all. */
    public int getMaxListCount () {
        return scrollPane.maxListCount;
    }

    protected void setStage (Stage stage) {
        if (stage == null) scrollPane.hide();
        super.setStage(stage);
    }

    public void setStyle (WidgetSelectBoxStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;

        if (scrollPane != null) {
            scrollPane.setStyle(style.scrollStyle);
            scrollPane.list.setStyle(style.listStyle);
        }
        invalidateHierarchy();
    }

    /** Returns the select box's style. Modifying the returned style may not have an effect until {@link #setStyle(WidgetSelectBoxStyle)}
     * is called. */
    public WidgetSelectBoxStyle getStyle () {
        return style;
    }

    /** Set the backing Array that makes up the choices available in the SelectBox */
    public void setItems (WidgetListItem... newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth();

        items.clear();
        items.addAll(newItems);
        selection.validate();
        scrollPane.list.setItems(items);

        invalidate();
        if (oldPrefWidth != getPrefWidth()) invalidateHierarchy();
    }

    /** Sets the items visible in the select box. */
    public void setItems (Array<WidgetListItem> newItems) {
        if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
        float oldPrefWidth = getPrefWidth();

        if (newItems != items) {
            items.clear();
            items.addAll(newItems);
        }
        selection.validate();
        scrollPane.list.setItems(items);

        invalidate();
        if (oldPrefWidth != getPrefWidth()) invalidateHierarchy();
    }

    public void clearItems () {
        if (items.size == 0) return;
        items.clear();
        selection.clear();
        scrollPane.list.clearItems();
        invalidateHierarchy();
    }

    /** Returns the internal items array. If modified, {@link #setItems(Array)} must be called to reflect the changes. */
    public Array<WidgetListItem> getItems () {
        return items;
    }

    public void layout () {
        Drawable bg = style.background;
        WidgetList list=scrollPane.list;
        float itemHeight=0.0f;
        float itemWidth=0.0f;
        if (list!=null){
            list.layout();
            itemHeight=list.itemHeight;
            itemWidth=list.getPrefWidth();
        }
        if (bg != null) {
            prefHeight = Math.max(bg.getTopHeight() + bg.getBottomHeight() + itemHeight,
                    bg.getMinHeight());
        } else
            prefHeight =itemHeight;

        if (selectedPrefWidth) {
            prefWidth = 0;
            if (bg != null) prefWidth = bg.getLeftWidth() + bg.getRightWidth();
            prefWidth += itemWidth;
        } else {
            prefWidth = 0;
            if (bg != null) prefWidth = bg.getLeftWidth() + bg.getRightWidth();
            prefWidth += itemWidth;

            WidgetList.WidgetListStyle listStyle = style.listStyle;
            ScrollPaneStyle scrollStyle = style.scrollStyle;
            float scrollWidth = prefWidth + listStyle.selection.getLeftWidth() + listStyle.selection.getRightWidth();
            bg = scrollStyle.background;
            if (bg != null) scrollWidth = Math.max(scrollWidth + bg.getLeftWidth() + bg.getRightWidth(), bg.getMinWidth());
            if (scrollPane == null || !scrollPane.isScrollingDisabledY()) {
                scrollWidth += Math.max(style.scrollStyle.vScroll != null ? style.scrollStyle.vScroll.getMinWidth() : 0,
                        style.scrollStyle.vScrollKnob != null ? style.scrollStyle.vScrollKnob.getMinWidth() : 0);
            }
            prefWidth = Math.max(prefWidth, scrollWidth);
        }
    }

    /** Returns appropriate background drawable from the style based on the current select box state. */
    protected @Null Drawable getBackgroundDrawable () {
        if (isDisabled() && style.backgroundDisabled != null) return style.backgroundDisabled;
        if (scrollPane.hasParent() && style.backgroundOpen != null) return style.backgroundOpen;
        if (isOver() && style.backgroundOver != null) return style.backgroundOver;
        return style.background;
    }


    public void draw (Batch batch, float parentAlpha) {
        validate();

        Drawable background = getBackgroundDrawable();

        Color color = getColor();
        float x = getX(), y = getY();
        float width = getWidth(), height = getHeight();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (background != null) background.draw(batch, x, y, width, height);

        WidgetListItem selected = selection.first();
        if (selected != null) {
            if (background != null) {
                width -= background.getLeftWidth() + background.getRightWidth();
                height -= background.getBottomHeight() + background.getTopHeight();
                x += background.getLeftWidth();
                y += background.getBottomHeight();
            } else {
                y += (int)(height / 2);
            }
            drawItem(batch,parentAlpha, selected, x, y, width,height);
        }
    }

    protected void drawItem (Batch batch,float parentAlpha, WidgetListItem item, float x, float y, float width,float height) {
        float itemX=item.getX();
        float itemY=item.getY();
        float itemWidth=item.getWidth();
        float itemHeight=item.getHeight();
        item.setSize(width,height);
        item.setPosition(x,y);
        item.draw(batch,parentAlpha);
        item.setSize(itemX,itemY);
        item.setSize(itemWidth,itemHeight);
    }

    /** Sets the alignment of the selected item in the select box. See {@link #getList()} and {@link List#setAlignment(int)} to set
     * the alignment in the list shown when the select box is open.
     * @param alignment See {@link Align}. */
    public void setAlignment (int alignment) {
        this.alignment = alignment;
    }

    /** Get the set of selected items, useful when multiple items are selected
     * @return a Selection object containing the selected elements */
    public ArraySelection<WidgetListItem> getSelection () {
        return selection;
    }

    /** Returns the first selected item, or null. For multiple selections use {@link WidgetSelectBox#getSelection()}. */
    public @Null WidgetListItem getSelected () {
        return selection.first();
    }

    /** Sets the selection to only the passed item, if it is a possible choice, else selects the first item. */
    public void setSelected (@Null WidgetListItem item) {
        if (items.contains(item, false))
            selection.set(item);
        else if (items.size > 0)
            selection.set(items.first());
        else
            selection.clear();
    }

    /** @return The index of the first selected item. The top item has an index of 0. Nothing selected has an index of -1. */
    public int getSelectedIndex () {
        ObjectSet<WidgetListItem> selected = selection.items();
        return selected.size == 0 ? -1 : items.indexOf(selected.first(), false);
    }

    /** Sets the selection to only the selected index. */
    public void setSelectedIndex (int index) {
        selection.set(items.get(index));
    }

    /** When true the pref width is based on the selected item. */
    public void setSelectedPrefWidth (boolean selectedPrefWidth) {
        this.selectedPrefWidth = selectedPrefWidth;
    }

    public boolean getSelectedPrefWidth () {
        return selectedPrefWidth;
    }

    /** Returns the pref width of the select box if the widest item was selected, for use when
     * {@link #setSelectedPrefWidth(boolean)} is true. */
    public float getMaxSelectedPrefWidth () {
        float width = 0;
        for (int i = 0; i < items.size; i++) {
            WidgetListItem item=items.get(i);
            item.pack();
            width = Math.max(item.getWidth(), width);
        }
        Drawable bg = style.background;
        if (bg != null) width = Math.max(width + bg.getLeftWidth() + bg.getRightWidth(), bg.getMinWidth());
        return width;
    }

    public void setDisabled (boolean disabled) {
        if (disabled && !this.disabled) hideScrollPane();
        this.disabled = disabled;
    }

    public boolean isDisabled () {
        return disabled;
    }

    public float getPrefWidth () {
        validate();
        return prefWidth;
    }

    public float getPrefHeight () {
        validate();
        return prefHeight;
    }

    protected String toString (WidgetListItem item) {
        return item.toString();
    }

    /** @deprecated Use {@link #showScrollPane()}. */
    @Deprecated
    public void showList () {
        showScrollPane();
    }

    public void showScrollPane () {
        if (items.size == 0) return;
        if (getStage() != null) scrollPane.show(getStage());
    }

    /** @deprecated Use {@link #hideScrollPane()}. */
    @Deprecated
    public void hideList () {
        hideScrollPane();
    }

    public void hideScrollPane () {
        scrollPane.hide();
    }

    /** Returns the list shown when the select box is open. */
    public WidgetList getList () {
        return scrollPane.list;
    }

    /** Disables scrolling of the list shown when the select box is open. */
    public void setScrollingDisabled (boolean y) {
        scrollPane.setScrollingDisabled(true, y);
        invalidateHierarchy();
    }

    /** Returns the scroll pane containing the list that is shown when the select box is open. */
    public SelectBoxScrollPane getScrollPane () {
        return scrollPane;
    }

    public boolean isOver () {
        return clickListener.isOver();
    }

    public ClickListener getClickListener () {
        return clickListener;
    }

    protected void onShow (Actor scrollPane, boolean below) {
        scrollPane.getColor().a = 0;
        scrollPane.addAction(fadeIn(0.3f, Interpolation.fade));
    }

    protected void onHide (Actor scrollPane) {
        scrollPane.getColor().a = 1;
        scrollPane.addAction(sequence(fadeOut(0.15f, Interpolation.fade), removeActor()));
    }

    /** The scroll pane shown when a select box is open.
     * @author Nathan Sweet */
    static public class SelectBoxScrollPane extends ScrollPane {
        final WidgetSelectBox selectBox;
        int maxListCount;
        private final Vector2 stagePosition = new Vector2();
        final WidgetList list;
        private InputListener hideListener;
        private Actor previousScrollFocus;

        public SelectBoxScrollPane (final WidgetSelectBox selectBox) {
            super(null, selectBox.style.scrollStyle);
            this.selectBox = selectBox;

            setOverscroll(false, false);
            setFadeScrollBars(false);
            setScrollingDisabled(true, false);

            list = newList();
            list.setTouchable(Touchable.disabled);
            list.setTypeToSelect(true);
            setActor(list);

            list.addListener(new ClickListener() {
                public void clicked (InputEvent event, float x, float y) {
                    WidgetListItem selected = list.getSelected();
                    // Force clicking the already selected item to trigger a change event.
                    if (selected != null) selectBox.selection.items().clear(51);
                    selectBox.selection.choose(selected);
                    hide();
                }

                public boolean mouseMoved (InputEvent event, float x, float y) {
                    int index = list.getItemIndexAt(y);
                    if (index != -1) list.setSelectedIndex(index);
                    return true;
                }
            });

            addListener(new InputListener() {
                public void exit (InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    if (toActor == null || !isAscendantOf(toActor)) list.selection.set(selectBox.getSelected());
                }
            });

            hideListener = new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Actor target = event.getTarget();
                    if (isAscendantOf(target)) return false;
                    list.selection.set(selectBox.getSelected());
                    hide();
                    return false;
                }

                public boolean keyDown (InputEvent event, int keycode) {
                    switch (keycode) {
                        case Keys.NUMPAD_ENTER:
                        case Keys.ENTER:
                            selectBox.selection.choose(list.getSelected());
                            // Fall thru.
                        case Keys.ESCAPE:
                            hide();
                            event.stop();
                            return true;
                    }
                    return false;
                }
            };
        }

        /** Allows a subclass to customize the select box list. The default implementation returns a list that delegates
         * {@link List#toString(Object)} to {@link WidgetSelectBox#toString(WidgetListItem)}. */
        protected WidgetList newList () {
            return new WidgetList(selectBox.style.listStyle) {
                public String toString (WidgetListItem obj) {
                    return selectBox.toString(obj);
                }
            };
        }

        public void show (Stage stage) {
            if (list.isTouchable()) return;

            stage.addActor(this);
            stage.addCaptureListener(hideListener);
            stage.addListener(list.getKeyListener());

            selectBox.localToStageCoordinates(stagePosition.set(0, 0));

            // Show the list above or below the select box, limited to a number of items and the available height in the stage.
            float itemHeight = list.getItemHeight();
            float height = itemHeight * (maxListCount <= 0 ? selectBox.items.size : Math.min(maxListCount, selectBox.items.size));
            Drawable scrollPaneBackground = getStyle().background;
            if (scrollPaneBackground != null) height += scrollPaneBackground.getTopHeight() + scrollPaneBackground.getBottomHeight();
            Drawable listBackground = list.getStyle().background;
            if (listBackground != null) height += listBackground.getTopHeight() + listBackground.getBottomHeight();

            float heightBelow = stagePosition.y;
            float heightAbove = stage.getHeight() - heightBelow - selectBox.getHeight();
            boolean below = true;
            if (height > heightBelow) {
                if (heightAbove > heightBelow) {
                    below = false;
                    height = Math.min(height, heightAbove);
                } else
                    height = heightBelow;
            }

            if (below)
                setY(stagePosition.y - height);
            else
                setY(stagePosition.y + selectBox.getHeight());
            setX(stagePosition.x);
            setHeight(height);
            validate();
            float width = Math.max(getPrefWidth(), selectBox.getWidth());
            setWidth(width);

            validate();
            scrollTo(0, list.getHeight() - selectBox.getSelectedIndex() * itemHeight - itemHeight / 2, 0, 0, true, true);
            updateVisualScroll();

            previousScrollFocus = null;
            Actor actor = stage.getScrollFocus();
            if (actor != null && !actor.isDescendantOf(this)) previousScrollFocus = actor;
            stage.setScrollFocus(this);

            list.selection.set(selectBox.getSelected());
            list.setTouchable(Touchable.enabled);
            clearActions();
            selectBox.onShow(this, below);
        }

        public void hide () {
            if (!list.isTouchable() || !hasParent()) return;
            list.setTouchable(Touchable.disabled);

            Stage stage = getStage();
            if (stage != null) {
                stage.removeCaptureListener(hideListener);
                stage.removeListener(list.getKeyListener());
                if (previousScrollFocus != null && previousScrollFocus.getStage() == null) previousScrollFocus = null;
                Actor actor = stage.getScrollFocus();
                if (actor == null || isAscendantOf(actor)) stage.setScrollFocus(previousScrollFocus);
            }

            clearActions();
            selectBox.onHide(this);
        }

        public void draw (Batch batch, float parentAlpha) {
            selectBox.localToStageCoordinates(temp.set(0, 0));
            if (!temp.equals(stagePosition)) hide();
            super.draw(batch, parentAlpha);
        }

        public void act (float delta) {
            super.act(delta);
            toFront();
        }

        protected void setStage (Stage stage) {
            Stage oldStage = getStage();
            if (oldStage != null) {
                oldStage.removeCaptureListener(hideListener);
                oldStage.removeListener(list.getKeyListener());
            }
            super.setStage(stage);
        }

        public WidgetList getList () {
            return list;
        }

        public WidgetSelectBox getSelectBox () {
            return selectBox;
        }
    }

    /** The style for a select box, see {@link WidgetSelectBox}.
     * @author mzechner
     * @author Nathan Sweet */
    static public class WidgetSelectBoxStyle {
        public @Null Drawable background;
        public ScrollPaneStyle scrollStyle;
        public WidgetList.WidgetListStyle listStyle;
        public @Null Drawable backgroundOver, backgroundOpen, backgroundDisabled;

        public WidgetSelectBoxStyle () {
        }

        public WidgetSelectBoxStyle (BitmapFont font, Color fontColor, @Null Drawable background, ScrollPaneStyle scrollStyle,
                                     WidgetList.WidgetListStyle listStyle) {
            this.background = background;
            this.scrollStyle = scrollStyle;
            this.listStyle = listStyle;
        }

        public WidgetSelectBoxStyle (WidgetSelectBoxStyle style) {
            background = style.background;
            scrollStyle = new ScrollPaneStyle(style.scrollStyle);
            listStyle = new WidgetList.WidgetListStyle(style.listStyle);

            backgroundOver = style.backgroundOver;
            backgroundOpen = style.backgroundOpen;
            backgroundDisabled = style.backgroundDisabled;
        }
    }
}
