package library.gdx.fonts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import library.gdx.shaders.FontShader;

public class Font extends DistanceFieldFont {
    private FontShader shader;
    public Font(FontData data, TextureRegion region, FontShader shader) {
        super(data, region, false);
        setDistanceFieldSmoothing(4.0f*data.scaleX);
        this.shader=shader;
    }

    @Override
    public BitmapFontCache newFontCache() {
        return new FontCache(this,usesIntegerPositions());
    }

    private class FontCache extends BitmapFontCache{
        public FontCache (Font font) {
            super(font, font.usesIntegerPositions());
        }

        public FontCache (DistanceFieldFont font, boolean integer) {
            super(font, integer);
        }

        private float getSmoothingFactor () {
            return getDistanceFieldSmoothing();
//            final DistanceFieldFont font = (DistanceFieldFont)super.getFont();
//            return font.getDistanceFieldSmoothing() * font.getScaleX();
        }

        private void setSmoothingUniform (float smoothing) {
            shader.setSmoothing(smoothing);
        }

        @Override
        public void draw (Batch spriteBatch) {
            shader.begin(spriteBatch);
            setSmoothingUniform(getSmoothingFactor());
            super.draw(spriteBatch);
            shader.end(spriteBatch);
        }

        @Override
        public void draw (Batch spriteBatch, int start, int end) {
            shader.begin(spriteBatch);
            setSmoothingUniform(getSmoothingFactor());
            super.draw(spriteBatch, start, end);
            shader.end(spriteBatch);
        }
    }
}
