package library.gdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

import library.gdx.fonts.Font;
import library.gdx.fonts.FontAsset;
import library.gdx.fonts.FontData;
import library.gdx.fonts.FontType;
import library.gdx.shaders.FontShader;

public class FontUtils implements Disposable {
    private static FontUtils instance;

    private static FontUtils getInstance() {
        if (instance == null) {
            instance = new FontUtils();
        }
        return instance;
    }

    public static Font scaledFont(FontType type, float size) {
        return getInstance().getScaledFont(type, size);
    }

    public static Font unscaledFont(FontType type, float size) {
        return getInstance().getUnscaledFont(type, size);
    }

    public static void init() {
        if (instance != null) {
            instance.dispose();
        }
        instance = new FontUtils();
    }

    private final FontShader fontShader;
    private final Map<FontType, FontAsset> fontAssetMap;

    private final Map<String, Font> fontMap;

    private float density;

    private FontUtils() {
        fontShader = ResourceUtils.getInstance().fontShader;
        fontAssetMap = new HashMap<>();
        fontMap = new HashMap<>();
        density = GameUtils.getDensity(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private Font getScaledFont(FontType fontType, float size) {
        return getUnscaledFont(fontType, size * density);
    }

    private Font getUnscaledFont(FontType fontType, float size) {
        size = GameUtils.round(size);
        String key = fontType.name() + size;
        if (fontMap.containsKey(key)) {
            return fontMap.get(key);
        }
        float scale = GameUtils.round(size / 32.0f);
        FontAsset fontAsset = getFontAsset(fontType);
        if (fontAsset == null) {
            Gdx.app.error("Font Asset", "IS Null");
        }
        FontData fontData = new FontData(fontAsset.getFontData(), scale);
        Font font = new Font(fontData, scale > 1.0f ? fontAsset.getLinearRegion() : fontAsset.getNearestRegion(), fontShader);
        fontMap.put(key, font);
        return font;
    }


    private FontAsset getFontAsset(FontType type) {
        if (fontAssetMap.containsKey(type)) {
            return fontAssetMap.get(type);
        }
        FontAsset fontAsset = new FontAsset(type);
        fontAssetMap.put(type, fontAsset);
        return fontAsset;
    }

    @Override
    public void dispose() {
        for (FontAsset fontAsset : fontAssetMap.values()) {
            fontAsset.dispose();
        }
    }
}
