package com.badlogic.gdx.graphics.g2d.skia.resources;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class DataURIResourceProviderProxy extends ResourceProvider {
    static {
        Library.staticLoad();
    }


    public DataURIResourceProviderProxy(long ptr) {
        super(ptr);
    }

    public static DataURIResourceProviderProxy make(ResourceProvider resourceProvider) {
        return make(resourceProvider, false);
    }

    public static DataURIResourceProviderProxy make(ResourceProvider resourceProvider, boolean predecode) {
        assert resourceProvider != null : "Can’t DataURIResourceProviderProxy::make with resourceProvider == null";
        Stats.onNativeCall();
        return new DataURIResourceProviderProxy(_nMake(Native.getPtr(resourceProvider), predecode));
    }

    public static native long _nMake(long resourceProviderPtr, boolean predecode);
}