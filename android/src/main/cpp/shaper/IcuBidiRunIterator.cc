#include <jni.h>
#include "../interop.hh"
#include "modules/skshaper/include/SkShaper.h"

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_shaper_IcuBidiRunIteratorKt__1nMake
  (JNIEnv* env, jclass jclass, jlong textPtr, jint bidiLevel) {
    SkString* text = reinterpret_cast<SkString*>(static_cast<uintptr_t>(textPtr));
    std::unique_ptr<SkShaper::BiDiRunIterator> instance(SkShaper::MakeIcuBiDiRunIterator(text->c_str(), text->size(), bidiLevel & 0xFF));
    return reinterpret_cast<jlong>(instance.release());
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_shaper_IcuBidiRunIteratorKt__1nGetCurrentLevel
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkShaper::BiDiRunIterator* instance = reinterpret_cast<SkShaper::BiDiRunIterator*>(static_cast<uintptr_t>(ptr));
    return instance->currentLevel();
}
