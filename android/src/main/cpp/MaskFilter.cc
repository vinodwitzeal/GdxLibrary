#include <iostream>
#include <jni.h>
#include "include/core/SkMaskFilter.h"
#include "include/core/SkShader.h"
#include "include/effects/SkShaderMaskFilter.h"
#include "include/effects/SkTableMaskFilter.h"
#include "interop.hh"

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt__1nMakeBlur
  (JNIEnv* env, jclass jclass, jint blurStyleInt, jfloat sigma, jboolean respectCTM) {
    SkBlurStyle blurStyle = static_cast<SkBlurStyle>(blurStyleInt);
    SkMaskFilter* ptr = SkMaskFilter::MakeBlur(blurStyle, sigma, respectCTM).release();
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt__1nMakeShader
  (JNIEnv* env, jclass jclass, jlong shaderPtr) {
    SkShader* shader = reinterpret_cast<SkShader*>(static_cast<uintptr_t>(shaderPtr));
    SkMaskFilter* ptr = SkShaderMaskFilter::Make(sk_ref_sp(shader)).release();
    return reinterpret_cast<jlong>(ptr);
}

// extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt__1nMakeEmboss
//   (JNIEnv* env, jclass jclass, jfloat sigma, jfloat x, jfloat y, jfloat z, jint pad, jint ambient, jint specular) {
//     SkEmbossMaskFilter::Light light{ {x, y, z}, pad, ambient, specular };
//     SkMaskFilter* ptr = SkEmbossMaskFilter::Make(sigma, light).release();
//     return reinterpret_cast<jlong>(ptr);
// }

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt_MaskFilter_1nMakeTable
  (JNIEnv* env, jclass jclass, jbyteArray tableArray) {
    jbyte* table = env->GetByteArrayElements(tableArray, 0);
    SkMaskFilter* ptr = SkTableMaskFilter::Create(reinterpret_cast<uint8_t*>(table));
    env->ReleaseByteArrayElements(tableArray, table, 0);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt__1nMakeGamma
  (JNIEnv* env, jclass jclass, jfloat gamma) {
    SkMaskFilter* ptr = SkTableMaskFilter::CreateGamma(gamma);
    return reinterpret_cast<jlong>(ptr);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_MaskFilterKt__1nMakeClip
  (JNIEnv* env, jclass jclass, jbyte min, jbyte max) {
    SkMaskFilter* ptr = SkTableMaskFilter::CreateClip(static_cast<uint8_t>(min), static_cast<uint8_t>(max));
    return reinterpret_cast<jlong>(ptr);
}