#include <iostream>
#include <jni.h>
#include "interop.hh"
#include "include/core/SkTypeface.h"
#include "include/core/SkFontMgr.h"

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt_FontStyleSet_1nMakeEmpty
  (JNIEnv* env, jclass jclass) {
    SkFontStyleSet* instance = SkFontStyleSet::CreateEmpty().release();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt__1nCount
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    return instance->count();
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt__1nGetStyle
  (JNIEnv* env, jclass jclass, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkFontStyle fontStyle;
    instance->getStyle(index, &fontStyle, nullptr);
    return fontStyle.weight() + (fontStyle.width() << 16) + (fontStyle.slant() << 24);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt__1nGetStyleName
  (JNIEnv* env, jclass jclass, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkString style;
    instance->getStyle(index, nullptr, &style);
    return reinterpret_cast<jlong>(new SkString(style));
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt__1nGetTypeface
  (JNIEnv* env, jclass jclass, jlong ptr, jint index) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = instance->createTypeface(index).release();
    return reinterpret_cast<jlong>(typeface);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_FontStyleSetKt__1nMatchStyle
  (JNIEnv* env, jclass jclass, jlong ptr, jint fontStyle) {
    SkFontStyleSet* instance = reinterpret_cast<SkFontStyleSet*>(static_cast<uintptr_t>(ptr));
    SkTypeface* typeface = instance->matchStyle(skija::FontStyle::fromJava(fontStyle)).release();
    return reinterpret_cast<jlong>(typeface);
}