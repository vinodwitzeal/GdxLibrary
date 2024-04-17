package com.badlogic.gdx.graphics.g2d.skia;


import com.badlogic.gdx.graphics.g2d.skia.impl.*;

public class BackendRenderTarget extends Managed {
    static {
        Library.staticLoad();
    }

    
   //("_, _, _, _, _, _ -> new")
    public static BackendRenderTarget makeGL(int width, int height, int sampleCnt, int stencilBits, int fbId, int fbFormat) {
        Stats.onNativeCall();
        return new BackendRenderTarget(_nMakeGL(width, height, sampleCnt, stencilBits, fbId, fbFormat));
    }

    
   //("_, _, _ -> new")
//    public static BackendRenderTarget makeMetal(int width, int height, long texturePtr) {
//        Stats.onNativeCall();
//        return new BackendRenderTarget(_nMakeMetal(width, height, texturePtr));
//    }

//    /**
//     * <p>Creates Direct3D backend render target object from D3D12 texture resource.</p>
//     * <p>For more information refer to skia GrBackendRenderTarget class.</p>
//     *
//     * @param width      width of the render target in pixels
//     * @param height     height of the render target in pixels
//     * @param texturePtr pointer to ID3D12Resource texture resource object; must be not zero
//     * @param format     pixel data DXGI_FORMAT fromat of the texturePtr resource
//     * @param sampleCnt  samples count for texture resource
//     * @param levelCnt   sampling quality level for texture resource
//     */
    
   //("_, _, _, _, _, _ -> new")
//    public static BackendRenderTarget makeDirect3D(int width, int height, long texturePtr, int format, int sampleCnt, int levelCnt) {
//        Stats.onNativeCall();
//        return new BackendRenderTarget(_nMakeDirect3D(width, height, texturePtr, format, sampleCnt, levelCnt));
//    }


    public BackendRenderTarget(long ptr) {
        super(ptr, _FinalizerHolder.PTR);
    }


    public static class _FinalizerHolder {
        public static final long PTR = _nGetFinalizer();
    }

    public static native long _nGetFinalizer();

    public static native long _nMakeGL(int width, int height, int sampleCnt, int stencilBits, int fbId, int fbFormat);

//    public static native long _nMakeMetal(int width, int height, long texturePtr);
//
//    public static native long _nMakeDirect3D(int width, int height, long texturePtr, int format, int sampleCnt, int levelCnt);

}