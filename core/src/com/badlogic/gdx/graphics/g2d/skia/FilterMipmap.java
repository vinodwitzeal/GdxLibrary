package com.badlogic.gdx.graphics.g2d.skia;


public class FilterMipmap implements SamplingMode {
    public final FilterMode _filterMode;
    public final MipmapMode _mipmapMode;

    public FilterMipmap(FilterMode _filterMode, MipmapMode _mipmapMode) {
        this._filterMode = _filterMode;
        this._mipmapMode = _mipmapMode;
    }

    public FilterMipmap(FilterMode filterMode) {
        this(filterMode, MipmapMode.NONE);
    }

    @Override
    public long _pack() {
        return 0x7FFFFFFFFFFFFFFFL & (((long) _filterMode.ordinal() << 32) | (long) _mipmapMode.ordinal());
    }
}
