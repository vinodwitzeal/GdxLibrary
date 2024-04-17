#include <jni.h>
#include "third_party/externals/icu/source/common/unicode/uchar.h"

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_icu_UnicodeKt_charDirection
  (JNIEnv* env, jclass jclass, jint codePoint) {
    return u_charDirection(codePoint);
}
