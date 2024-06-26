#include <cstring>
#include <iostream>
#include <jni.h>
#include "include/core/SkData.h"
#include "include/core/SkSerialProcs.h"
#include "include/core/SkTextBlob.h"
#include "interop.hh"
#include "include/mppinterop.h"
#include "RunRecordClone.hh"
#include "TexBlobIter.hh"

static void unrefTextBlob(SkTextBlob* ptr) {
    ptr->unref();
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_TextBlob_1nGetFinalizer
  (JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&unrefTextBlob));
}

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nBounds
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultRect) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    SkRect bounds = instance->bounds();

    jfloat* floats = env->GetFloatArrayElements(resultRect, 0);
    skikoMpp::skrect::serializeAs4Floats(bounds, floats);
    env->ReleaseFloatArrayElements(resultRect, floats, 0);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_TextBlob_1nGetUniqueId
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    return instance->uniqueID();
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetInterceptsLength
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat lower, jfloat upper, jlong paintPtr) {
      SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
      std::vector<float> bounds {lower, upper};
      SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
      int len = instance->getIntercepts(bounds.data(), nullptr, paint);
      return len;
}
extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetIntercepts
  (JNIEnv* env, jclass jclass, jlong ptr, jfloat lower, jfloat upper, jlong paintPtr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    SkPaint* paint = reinterpret_cast<SkPaint*>(static_cast<uintptr_t>(paintPtr));
    jfloat* floats = env->GetFloatArrayElements(resultArray, 0);
    std::vector<float> bounds {lower, upper};
    instance->getIntercepts(bounds.data(), floats, paint);
    env->ReleaseFloatArrayElements(resultArray, floats, 0);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nMakeFromPosH
  (JNIEnv* env, jclass jclass, jshortArray glyphsArr, jint glyphsLen, jfloatArray xposArr, jfloat ypos, jlong fontPtr) {
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    jfloat* xpos = env->GetFloatArrayElements(xposArr, nullptr);
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));

    SkTextBlob* instance = SkTextBlob::MakeFromPosTextH(glyphs, glyphsLen * sizeof(jshort), xpos, ypos, *font, SkTextEncoding::kGlyphID).release();

    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    env->ReleaseFloatArrayElements(xposArr, xpos, 0);

    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nMakeFromPos
  (JNIEnv* env, jclass jclass, jshortArray glyphsArr, jint glyphsLen, jfloatArray posArr, jlong fontPtr ) {
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    jfloat* pos = env->GetFloatArrayElements(posArr, nullptr);
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));

    SkTextBlob* instance = SkTextBlob::MakeFromPosText(
        glyphs,
        glyphsLen * sizeof(jshort),
        reinterpret_cast<SkPoint*>(pos),
        *font,
        SkTextEncoding::kGlyphID
    ).release();

    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    env->ReleaseFloatArrayElements(posArr, pos, 0);

    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nMakeFromRSXform
  (JNIEnv* env, jclass jclass, jshortArray glyphsArr, jint glyphsLen, jfloatArray xformArr, jlong fontPtr ) {
    jshort* glyphs = env->GetShortArrayElements(glyphsArr, nullptr);
    jfloat* xform = env->GetFloatArrayElements(xformArr, nullptr);
    SkFont* font = reinterpret_cast<SkFont*>(static_cast<uintptr_t>(fontPtr));

    SkTextBlob* instance = SkTextBlob::MakeFromRSXform(glyphs, glyphsLen * sizeof(jshort), reinterpret_cast<SkRSXform*>(xform), *font, SkTextEncoding::kGlyphID).release();

    env->ReleaseShortArrayElements(glyphsArr, glyphs, 0);
    env->ReleaseFloatArrayElements(xformArr, xform, 0);

    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_TextBlob_1nSerializeToData
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    SkData* data = instance->serialize({}).release();
    return reinterpret_cast<jlong>(data);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_TextBlob_1nMakeFromData
  (JNIEnv* env, jclass jclass, jlong dataPtr) {
    SkData* data = reinterpret_cast<SkData*>(static_cast<uintptr_t>(dataPtr));
    SkTextBlob* instance = SkTextBlob::Deserialize(data->data(), data->size(), {}).release();
    return reinterpret_cast<jlong>(instance);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetGlyphsLength
  (JNIEnv* env, jclass jclass, jlong ptr) {
  SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
  return skikoMpp::textblob::getGlyphsLength(instance);
}

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetGlyphs
  (JNIEnv* env, jclass jclass, jlong ptr, jshortArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    jshort * shorts = env->GetShortArrayElements(resultArray, nullptr);
    skikoMpp::textblob::getGlyphs(instance, reinterpret_cast<short*>(shorts));
    env->ReleaseShortArrayElements(resultArray, shorts, 0);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetPositionsLength
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    return skikoMpp::textblob::getPositionsLength(instance);
}

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetPositions
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));

    jfloat* positions = env->GetFloatArrayElements(resultArray, 0);
    skikoMpp::textblob::getPositions(instance, reinterpret_cast<float*>(positions));
    env->ReleaseFloatArrayElements(resultArray, positions, 0);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetClustersLength
  (JNIEnv* env, jclass jclass, jlong ptr) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    return skikoMpp::textblob::getClustersLength(instance);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetClusters
  (JNIEnv* env, jclass jclass, jlong ptr, jintArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));

    jint* clusters = env->GetIntArrayElements(resultArray, 0);
    auto hasValue = skikoMpp::textblob::getClusters(instance, reinterpret_cast<int*>(clusters));
    env->ReleaseIntArrayElements(resultArray, clusters, 0);

    return hasValue;
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetTightBounds
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));

    auto bounds = skikoMpp::textblob::getTightBounds(instance);
    if (!bounds) return false;

    jfloat* floats = env->GetFloatArrayElements(resultArray, 0);
    skikoMpp::skrect::serializeAs4Floats(*bounds, floats);
    env->ReleaseFloatArrayElements(resultArray, floats, 0);

    return true;
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetBlockBounds
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));

    std::unique_ptr<SkRect> bounds = skikoMpp::textblob::getBlockBounds(instance);
    if (!bounds) return false;

    jfloat* floats = env->GetFloatArrayElements(resultArray, 0);
    skikoMpp::skrect::serializeAs4Floats(*bounds, floats);
    env->ReleaseFloatArrayElements(resultArray, floats, 0);
    return true;
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetFirstBaseline
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    jfloat* floats = env->GetFloatArrayElements(resultArray, 0);
    auto hasValue = skikoMpp::textblob::getFirstBaseline(instance, reinterpret_cast<float*>(floats));
    env->ReleaseFloatArrayElements(resultArray, floats, 0);
    return hasValue;
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt__1nGetLastBaseline
  (JNIEnv* env, jclass jclass, jlong ptr, jfloatArray resultArray) {
    SkTextBlob* instance = reinterpret_cast<SkTextBlob*>(static_cast<uintptr_t>(ptr));
    jfloat* floats = env->GetFloatArrayElements(resultArray, 0);
    auto hasValue = skikoMpp::textblob::getLastBaseline(instance, reinterpret_cast<float*>(floats));
    env->ReleaseFloatArrayElements(resultArray, floats, 0);
    return hasValue;
}


extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nCreate(JNIEnv* env, jclass jclass, jlong textBlobPtr) {
    TextBlobIter* result = new TextBlobIter(reinterpret_cast<SkTextBlob*>(textBlobPtr));
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(result));
}

static void deleteTextBlobIter(TextBlobIter* ptr) {
    delete ptr;
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteTextBlobIter));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nFetch(JNIEnv* env, jclass jclass, jlong ptr) {
    TextBlobIter* instance = reinterpret_cast<TextBlobIter*>(static_cast<uintptr_t>(ptr));
    return (jboolean) instance->fetch();
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nHasNext(JNIEnv* env, jclass jclass, jlong ptr) {
    TextBlobIter* instance = reinterpret_cast<TextBlobIter*>(static_cast<uintptr_t>(ptr));
    return (jboolean) instance->hasNext();
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nGetTypeface(JNIEnv* env, jclass jclass, jlong ptr) {
    TextBlobIter* instance = reinterpret_cast<TextBlobIter*>(static_cast<uintptr_t>(ptr));
    return (jlong) instance->getTypeface().release();
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nGetGlyphCount(JNIEnv* env, jclass jclass, jlong ptr) {
    TextBlobIter* instance = reinterpret_cast<TextBlobIter*>(static_cast<uintptr_t>(ptr));
    return (jint) instance->getGlyphCount();
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_TextBlobKt_Iter_1nGetGlyphs
        (JNIEnv* env, jclass jclass, jlong ptr, jshortArray dstArray, jint max) {
    TextBlobIter* instance = reinterpret_cast<TextBlobIter*>(static_cast<uintptr_t>(ptr));
    jshort* dst = env->GetShortArrayElements(dstArray, nullptr);
    int size = instance->writeGlyphs((uint16_t*) dst, max);
    env->ReleaseShortArrayElements(dstArray, dst, 0);
    return (jint) size;
}
