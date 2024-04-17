#include <jni.h>
#include "interop.hh"
#include "include/core/SkBBHFactory.h"

static void deleteSkBBHFactory(SkBBHFactory* bbh) {
    delete bbh;
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_BBHFactoryKt_RTreeFactory_1nMake
  (JNIEnv* env, jclass jclass) {
    SkRTreeFactory* instance = new SkRTreeFactory();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_BBHFactoryKt_BBHFactory_1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteSkBBHFactory));
}
