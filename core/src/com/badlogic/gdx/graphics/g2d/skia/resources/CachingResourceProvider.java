package com.badlogic.gdx.graphics.g2d.skia.resources;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class CachingResourceProvider extends ResourceProvider {
    static {
        Library.staticLoad();
    }


    public CachingResourceProvider(long ptr) {
        super(ptr);
    }

    public static CachingResourceProvider make(ResourceProvider resourceProvider) {
        assert resourceProvider != null : "Can’t CachingResourceProvider::make with resourceProvider == null";
        Stats.onNativeCall();
        return new CachingResourceProvider(_nMake(Native.getPtr(resourceProvider)));
    }

    public static native long _nMake(long resourceProviderPtr);
}