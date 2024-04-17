#include <jni.h>
#include "include/core/SkImageInfo.h"

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_ColorTypeKt__1nIsAlwaysOpaque
  (JNIEnv* env, jclass jclass, jint value) {
    return SkColorTypeIsAlwaysOpaque(static_cast<SkColorType>(value));
}
