#include <string>
#include <jni.h>
#include "interop.hh"
#include "include/core/SkString.h"

static void deleteU16String(std::vector<jchar>* instance) {
    delete instance;
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_U16StringKt_U16String_1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteU16String));
}