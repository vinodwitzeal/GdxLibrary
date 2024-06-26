#include <iostream>
#include <jni.h>
#include "include/core/SkRegion.h"
#include "interop.hh"

static void deleteRegion(SkRegion* region) {
    // std::cout << "Deleting [SkRegion " << Region << "]" << std::endl;
    delete region;
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nMake(JNIEnv* env, jclass jclass) {
    SkRegion* obj = new SkRegion();
    return reinterpret_cast<jlong>(obj);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nGetFinalizer(JNIEnv* env, jclass jclass) {
    return static_cast<jlong>(reinterpret_cast<uintptr_t>(&deleteRegion));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSet(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* other = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->set(*other);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nIsEmpty(JNIEnv* env, jclass jclass, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nIsRect(JNIEnv* env, jclass jclass, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isRect();
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nIsComplex(JNIEnv* env, jclass jclass, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->isComplex();
}

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nGetBounds(JNIEnv* env, jclass jclass, jlong ptr, jintArray ltrbArray) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkIRect bounds = instance->getBounds();
    jint ltrb[4] { bounds.left(), bounds.top(), bounds.right(), bounds.bottom() };
    env->SetIntArrayRegion(ltrbArray, 0, 4, ltrb);
}

extern "C" JNIEXPORT jint JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nComputeRegionComplexity(JNIEnv* env, jclass jclass, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->computeRegionComplexity();
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nGetBoundaryPath(JNIEnv* env, jclass jclass, jlong ptr, jlong pathPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    return instance->getBoundaryPath(path);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSetEmpty(JNIEnv* env, jclass jclass, jlong ptr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->setEmpty();
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSetRect(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->setRect({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSetRects(JNIEnv* env, jclass jclass, jlong ptr, jintArray coords, jint _count) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    int len = env->GetArrayLength(coords);
    std::vector<SkIRect> rects(len / 4);
    jint* arr = env->GetIntArrayElements(coords, 0);
    for (int i = 0; i < len; i += 4)
        rects[i / 4] = {arr[i], arr[i+1], arr[i+2], arr[i+3]};
    env->ReleaseIntArrayElements(coords, arr, 0);
    return instance->setRects(rects.data(), len / 4);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSetRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->setRegion(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nSetPath(JNIEnv* env, jclass jclass, jlong ptr, jlong pathPtr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkPath* path = reinterpret_cast<SkPath*>(static_cast<uintptr_t>(pathPtr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->setPath(*path, *region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nIntersectsIRect(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->intersects({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nIntersectsRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->intersects(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nContainsIPoint(JNIEnv* env, jclass jclass, jlong ptr, jint x, jint y) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->contains(x, y);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nContainsIRect(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->contains({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nContainsRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->contains(*region);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nQuickContains(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->quickContains({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nQuickRejectIRect(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->quickReject({left, top, right, bottom});
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nQuickRejectRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->contains(*region);
}

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nTranslate(JNIEnv* env, jclass jclass, jlong ptr, jint dx, jint dy) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    instance->translate(dx, dy);
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nOpIRect(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    return instance->op({left, top, right, bottom}, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nOpRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op(*region, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nOpIRectRegion(JNIEnv* env, jclass jclass, jlong ptr, jint left, jint top, jint right, jint bottom, jlong regionPtr, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op({left, top, right, bottom}, *region, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nOpRegionIRect(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtr, jint left, jint top, jint right, jint bottom, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* region = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtr));
    return instance->op(*region, {left, top, right, bottom}, static_cast<SkRegion::Op>(op));
}

extern "C" JNIEXPORT jboolean JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_RegionKt_Region_1nOpRegionRegion(JNIEnv* env, jclass jclass, jlong ptr, jlong regionPtrA, jlong regionPtrB, jint op) {
    SkRegion* instance = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(ptr));
    SkRegion* regionA = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtrA));
    SkRegion* regionB = reinterpret_cast<SkRegion*>(static_cast<uintptr_t>(regionPtrB));
    return instance->op(*regionA, *regionB, static_cast<SkRegion::Op>(op));
}
