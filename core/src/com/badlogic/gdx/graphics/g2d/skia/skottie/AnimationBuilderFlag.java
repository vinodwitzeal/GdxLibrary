package com.badlogic.gdx.graphics.g2d.skia.skottie;


public enum AnimationBuilderFlag {
    /**
     * Normally, all static image frames are resolved at
     * load time via ImageAsset::getFrame(0).  With this flag,
     * frames are only resolved when needed, at seek() time.
     */
    DEFER_IMAGE_LOADING(0x01),

    /**
     * Attempt to use the embedded fonts (glyph paths,
     * normally used as fallback) over native Skia typefaces.
     */
    PREFER_EMBEDDED_FONTS(0x02);

    public static final AnimationBuilderFlag[] _values = values();

    public final int _flag;

    AnimationBuilderFlag(int flag) {
        this._flag = flag;
    }
}