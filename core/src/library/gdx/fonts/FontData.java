package library.gdx.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontData extends BitmapFont.BitmapFontData {
    public FontData(BitmapFont.BitmapFontData fontData,float scale) {
        super();
        name=fontData.name;
        imagePaths=fontData.imagePaths;
        fontFile=fontData.fontFile;
        flipped=fontData.flipped;
        padTop=fontData.padTop;
        padRight=fontData.padRight;
        padBottom=fontData.padBottom;
        padLeft=fontData.padLeft;
        lineHeight=fontData.lineHeight;
        capHeight=fontData.capHeight;
        ascent=fontData.ascent;
        descent=fontData.descent;
        down=fontData.down;
        blankLineScale=fontData.blankLineScale;
        scaleX=fontData.scaleX;
        scaleY=fontData.scaleY;
        markupEnabled=fontData.markupEnabled;
        cursorX=fontData.cursorX;

        int rows = fontData.glyphs.length;
        int cols = fontData.glyphs[0].length;

        for (int i = 0; i < rows; i++) {
            if (fontData.glyphs[i]!=null) {
                glyphs[i] = new BitmapFont.Glyph[fontData.glyphs[i].length];
                System.arraycopy(fontData.glyphs[i], 0, glyphs[i], 0, cols);
            }
        }

        missingGlyph=fontData.missingGlyph;
        spaceXadvance=fontData.spaceXadvance;
        xHeight=fontData.xHeight;
        breakChars=fontData.breakChars;
        setScale(scale);
    }
}
