package com.badlogic.gdx.graphics.g2d.skia.resources;

import com.badlogic.gdx.graphics.g2d.skia.utils.*;

import com.badlogic.gdx.graphics.g2d.skia.*;
import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class FileResourceProvider extends ResourceProvider {
    static {
        Library.staticLoad();
    }


    public FileResourceProvider(long ptr) {
        super(ptr);
    }

    public static FileResourceProvider make(String baseDir) {
        return make(baseDir, false);
    }

    public static FileResourceProvider make(String baseDir, boolean predecode) {
        assert baseDir != null : "Can’t FileResourceProvider::make with baseDir == null";
        Stats.onNativeCall();
        return new FileResourceProvider(_nMake(baseDir, predecode));
    }

    public static native long _nMake(String baseDir, boolean predecode);
}