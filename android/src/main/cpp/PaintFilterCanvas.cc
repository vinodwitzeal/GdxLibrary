#include <iostream>
#include <jni.h>
#include "include/core/SkCanvas.h"
#include "include/core/SkDrawable.h"
#include "include/sksl/SkPaintFilterCanvas.h"
#include "interop.hh"

class SkijaPaintFilterCanvas : public SkPaintFilterCanvas {
public:
    SkijaPaintFilterCanvas(
        SkCanvas* canvas,
        bool unrollDrawable
    ) : SkPaintFilterCanvas(canvas), unrollDrawable(unrollDrawable) {}

    virtual ~SkijaPaintFilterCanvas() {
        skija::PaintFilterCanvas::detach(jobj);
    }

    jobject jobj;

protected:
    bool onFilter(SkPaint& paint) const override {
        return skija::PaintFilterCanvas::onFilter(jobj, paint);
    }

    void onDrawDrawable(SkDrawable* drawable, const SkMatrix* matrix) override {
        if (unrollDrawable) {
            drawable->draw(this, matrix);
        } else {
            SkPaintFilterCanvas::onDrawDrawable(drawable, matrix);
        }
    }

private:
    bool unrollDrawable;
};

extern "C" JNIEXPORT void JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_PaintFilterCanvas_1jvmKt_PaintFilterCanvas_1nInit
  (JNIEnv* env, jclass jclass, jobject jobj, jlong canvasPtr) {
    SkijaPaintFilterCanvas* canvas = reinterpret_cast<SkijaPaintFilterCanvas*>(static_cast<uintptr_t>(canvasPtr));
    canvas->jobj = skija::PaintFilterCanvas::attach(env, jobj);
}

extern "C" JNIEXPORT jlong JNICALL Java_com_badlogic_gdx_graphics_g2d_skia_PaintFilterCanvasKt_PaintFilterCanvas_1nMake
  (JNIEnv* env, jclass jclass, jlong canvasPtr, jboolean unrollDrawable) {
    SkCanvas* canvas = reinterpret_cast<SkCanvas*>(static_cast<uintptr_t>(canvasPtr));
    SkijaPaintFilterCanvas* filterCanvas = new SkijaPaintFilterCanvas(canvas, unrollDrawable);
    return reinterpret_cast<jlong>(filterCanvas);
}