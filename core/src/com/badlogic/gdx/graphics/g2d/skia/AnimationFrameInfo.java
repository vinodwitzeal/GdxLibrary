package com.badlogic.gdx.graphics.g2d.skia;


/**
 * Information about individual frames in a multi-framed image.
 */

public class AnimationFrameInfo {

    public AnimationFrameInfo(int _requiredFrame, int _duration, boolean _fullyReceived, ColorAlphaType _alphaType, boolean _hasAlphaWithinBounds, AnimationDisposalMode _disposalMethod, BlendMode _blendMode, IRect _frameRect) {
        this._requiredFrame = _requiredFrame;
        this._duration = _duration;
        this._fullyReceived = _fullyReceived;
        this._alphaType = _alphaType;
        this._hasAlphaWithinBounds = _hasAlphaWithinBounds;
        this._disposalMethod = _disposalMethod;
        this._blendMode = _blendMode;
        this._frameRect = _frameRect;
    }

    public AnimationFrameInfo(int requiredFrame, int duration, boolean fullyReceived, int alphaTypeOrdinal, boolean hasAlphaWithinBounds, int disposalMethodOrdinal, int blendModeOrdinal, IRect frameRect) {
        this(requiredFrame, duration, fullyReceived, ColorAlphaType._values[alphaTypeOrdinal], hasAlphaWithinBounds, AnimationDisposalMode._values[disposalMethodOrdinal], BlendMode._values[blendModeOrdinal], frameRect);
    }

    /**
     * <p>The frame that this frame needs to be blended with, or
     * -1 if this frame is independent (so it can be
     * drawn over an uninitialized buffer).</p>
     *
     * <p>Note that this is the *earliest* frame that can be used
     * for blending. Any frame from [_requiredFrame, i) can be
     * used, unless its getDisposalMethod() is {@link AnimationDisposalMode#RESTORE_PREVIOUS}.</p>
     */

    public int _requiredFrame;

    /**
     * Number of milliseconds to show this frame.
     */

    public int _duration;

    /**
     * <p>Whether the end marker for this frame is contained in the stream.</p>
     *
     * <p>Note: this does not guarantee that an attempt to decode will be complete.
     * There could be an error in the stream.</p>
     */

    public boolean _fullyReceived;

    /**
     * <p>This is conservative; it will still return non-opaque if e.g. a
     * color index-based frame has a color with alpha but does not use it.</p>
     */

    public ColorAlphaType _alphaType;

    /**
     * <p>Whether the updated rectangle contains alpha.</p>
     *
     * <p>This is conservative; it will still be set to true if e.g. a color
     * index-based frame has a color with alpha but does not use it. In
     * addition, it may be set to true, even if the final frame, after
     * blending, is opaque.</p>
     */

    public boolean _hasAlphaWithinBounds;

    /**
     * <p>How this frame should be modified before decoding the next one.</p>
     */

    public AnimationDisposalMode _disposalMethod;

    /**
     * <p>How this frame should blend with the prior frame.</p>
     */

    public BlendMode _blendMode;

    /**
     * <p>The rectangle updated by this frame.</p>
     *
     * <p>It may be empty, if the frame does not change the image. It will
     * always be contained by {@link Codec#getSize()}.
     */

    public IRect _frameRect;
}