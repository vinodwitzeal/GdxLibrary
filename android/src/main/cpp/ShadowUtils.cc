#include <jni.h>
#include "include/utils/SkShadowUtils.h"
#include "include/core/SkPoint3.h"
#include "interop.hh"

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_ShadowUtilsKt__1nDrawShadow
  (JNIEnv* env, jclass jclass, jlong canvasPtr, jlong pathPtr, jfloat zPlaneX, jfloat zPlaneY, jfloat zPlaneZ,
        jfloat lightPosX, jfloat lightPosY, jfloat lightPosZ, jfloat lightRadius, jint ambientColor, jint spotColor, jint flags) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkShadowUtils::DrawShadow(canvas, *path, SkPoint3::Make(zPlaneX, zPlaneY, zPlaneZ), SkPoint3::Make(lightPosX, lightPosY, lightPosZ), lightRadius, ambientColor, spotColor, flags);
}

extern "C" JNIEXPORT int JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_ShadowUtilsKt__1nComputeTonalAmbientColor
  (JNIEnv* env, jclass jclass, jint ambientColor, jint spotColor) {
    SkColor outAmbientColor, outSpotColor;
    SkShadowUtils::ComputeTonalColors(ambientColor, spotColor, &outAmbientColor, &outSpotColor);
    return outAmbientColor;
}

extern "C" JNIEXPORT int JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_ShadowUtilsKt__1nComputeTonalSpotColor
  (JNIEnv* env, jclass jclass, jint ambientColor, jint spotColor) {
    SkColor outAmbientColor, outSpotColor;
    SkShadowUtils::ComputeTonalColors(ambientColor, spotColor, &outAmbientColor, &outSpotColor);
    return outSpotColor;
}