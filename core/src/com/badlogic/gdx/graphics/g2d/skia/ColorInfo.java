package com.badlogic.gdx.graphics.g2d.skia;


import com.badlogic.gdx.graphics.g2d.skia.impl.*;

/**
 * <p>Describes pixel and encoding. ImageInfo can be created from ColorInfo by
 * providing dimensions.</p>
 *
 * <p>It encodes how pixel bits describe alpha, transparency; color components red, blue,
 * and green; and ColorSpace, the range and linearity of colors.</p>
 */

public class ColorInfo {
    
    public final ColorType _colorType;
    
    public final ColorAlphaType _alphaType;
    
    public final ColorSpace _colorSpace;
    /**
     * Creates an ColorInfo with {@link ColorType#UNKNOWN}, {@link ColorAlphaType#UNKNOWN},
     * and no ColorSpace.
     */
    public static final ColorInfo DEFAULT = new ColorInfo(ColorType.UNKNOWN, ColorAlphaType.UNKNOWN, null);

    public ColorInfo(ColorType _colorType, ColorAlphaType _alphaType, ColorSpace _colorSpace) {
        this._colorType = _colorType;
        this._alphaType = _alphaType;
        this._colorSpace = _colorSpace;
    }

    public ColorType getColorType() {
        return _colorType;
    }

    public ColorAlphaType getAlphaType() {
        return _alphaType;
    }

    public ColorSpace getColorSpace() {
        return _colorSpace;
    }

    public boolean isOpaque() {
        return _alphaType == ColorAlphaType.OPAQUE || _colorType.isAlwaysOpaque();
    }

    /**
     * Returns number of bytes per pixel required by ColorType.
     * Returns zero if getColorType() is {@link ColorType#UNKNOWN}.
     *
     * @return bytes in pixel
     * @see <a href="https://fiddle.skia.org/c/@ImageInfo_bytesPerPixel">https://fiddle.skia.org/c/@ImageInfo_bytesPerPixel</a>
     */
    public int getBytesPerPixel() {
        return _colorType.getBytesPerPixel();
    }

    /**
     * Returns bit shift converting row bytes to row pixels.
     * Returns zero for {@link ColorType#UNKNOWN}.
     *
     * @return one of: 0, 1, 2, 3, 4; left shift to convert pixels to bytes
     * @see <a href="https://fiddle.skia.org/c/@ImageInfo_shiftPerPixel">https://fiddle.skia.org/c/@ImageInfo_shiftPerPixel</a>
     */
    public int getShiftPerPixel() {
        return _colorType.getShiftPerPixel();
    }

    public boolean isGammaCloseToSRGB() {
        return _colorSpace != null && _colorSpace.isGammaCloseToSRGB();
    }

    public ColorInfo withColorType(ColorType colorType){
        if (colorType==_colorType)return this;
        return new ColorInfo(colorType,_alphaType,_colorSpace);
    }

    public ColorInfo withAlphaType(ColorAlphaType alphaType){
        if (alphaType==_alphaType)return this;
        return new ColorInfo(_colorType,alphaType,_colorSpace);
    }

    public ColorInfo withColorSpace(ColorSpace colorSpace){
        if (colorSpace==_colorSpace)return this;
        return new ColorInfo(_colorType,_alphaType,colorSpace);
    }
}