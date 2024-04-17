package com.badlogic.gdx.graphics.g2d.skia.skottie;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;
import com.badlogic.gdx.graphics.g2d.skia.resources.*;

public class AnimationBuilder extends Managed {
    static {
        Library.staticLoad();
    }


    public AnimationBuilder(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public AnimationBuilder() {
        this(new AnimationBuilderFlag[0]);
    }

    public AnimationBuilder(AnimationBuilderFlag... builderFlags) {
        this(_nMake(_flagsToInt(builderFlags)));
        Stats.onNativeCall();
    }


    public static int _flagsToInt(AnimationBuilderFlag... builderFlags) {
        int flags = 0;
        for (AnimationBuilderFlag flag : builderFlags)
            flags |= flag._flag;
        return flags;
    }

    /**
     * <p>Specify a font manager for loading animation fonts.</p>
     */
    
    
    public AnimationBuilder setFontManager( FontMgr fontMgr) {
        try {
            Stats.onNativeCall();
            _nSetFontManager(_ptr, Native.getPtr(fontMgr));
            return this;
        } finally {
            Reference.reachabilityFence(fontMgr);
        }
    }

    /**
     * <p>Register a {@link Logger} with this builder.</p>
     */
    
    
    public AnimationBuilder setLogger( Logger logger) {
        try {
            Stats.onNativeCall();
            _nSetLogger(_ptr, Native.getPtr(logger));
            return this;
        } finally {
            Reference.reachabilityFence(logger);
        }
    }

    
    
    public AnimationBuilder setResourceProvider( ResourceProvider resourceProvider) {
        try {
            Stats.onNativeCall();
            _nSetResourceProvider(_ptr, Native.getPtr(resourceProvider));
            return this;
        } finally {
            Reference.reachabilityFence(resourceProvider);
        }
    }

    
    
    public Animation buildFromString( String data) {
        try {
            assert data != null : "Can’t buildFromString with data == null";
            Stats.onNativeCall();
            long ptr = _nBuildFromString(_ptr, data);
            if (ptr == 0)
                throw new IllegalArgumentException("Failed to create Animation from string: \"" + data + "\"");
            return new Animation(ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public Animation buildFromFile( String path) {
        try {
            assert path != null : "Can’t buildFromFile with path == null";
            Stats.onNativeCall();
            long ptr = _nBuildFromFile(_ptr, path);
            if (ptr == 0)
                throw new IllegalArgumentException("Failed to create Animation from path: " + path);
            return new Animation(ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    
    
    public Animation buildFromData( Data data) {
        try {
            assert data != null : "Can’t buildFromData with data == null";
            Stats.onNativeCall();
            long ptr = _nBuildFromData(_ptr, Native.getPtr(data));
            if (ptr == 0)
                throw new IllegalArgumentException("Failed to create Animation from data");
            return new Animation(ptr);
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    public static native long _nGetFinalizer();

    public static native long _nMake(int flags);

    public static native void _nSetFontManager(long ptr, long fontMgrPtr);

    public static native void _nSetLogger(long ptr, long loggerPtr);

    public static native void _nSetResourceProvider(long ptr, long resourceProviderPtr);

    public static native long _nBuildFromString(long ptr, String data);

    public static native long _nBuildFromFile(long ptr, String path);

    public static native long _nBuildFromData(long ptr, long dataPtr);
}