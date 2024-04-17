#include <jni.h>
#include "include/core/SkStream.h"
#include "include/core/SkCanvas.h"
#include "include/svg/SkSVGCanvas.h"

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_svg_SVGCanvasKt__1nMake
  (JNIEnv* env, jclass jclass, jfloat left, jfloat top, jfloat right, jfloat bottom, jlong wstreamPtr, jint flags) {
    SkWStream* wstream = reinterpret_cast<SkWStream*>(static_cast<uintptr_t>(wstreamPtr));
    SkRect bounds {left, top, right, bottom};
    SkCanvas* instance = SkSVGCanvas::Make(bounds, wstream, flags).release();
    return reinterpret_cast<jlong>(instance);
}
