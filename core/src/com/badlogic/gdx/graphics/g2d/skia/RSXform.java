package com.badlogic.gdx.graphics.g2d.skia;


/**
 * <p>A compressed form of a rotation+scale matrix.</p>
 *
 * <pre>[ fSCos     -fSSin    fTx ]
 *  [ fSSin      fSCos    fTy ]
 *  [     0          0      1 ]</pre>
 */

public class RSXform {

    public final float _scos, _ssin, _tx, _ty;

    public RSXform(float _scos, float _ssin, float _tx, float _ty) {
        this._scos = _scos;
        this._ssin = _ssin;
        this._tx = _tx;
        this._ty = _ty;
    }

    /**
     * Initialize a new xform based on the scale, rotation (in radians), final tx,ty location
     * and anchor-point ax,ay within the src quad.
     * <p>
     * Note: the anchor point is not normalized (e.g. 0...1) but is in pixels of the src image.
     */
    public static RSXform makeFromRadians(float scale, float radians, float tx, float ty, float ax, float ay) {
        float s = (float) Math.sin(radians) * scale;
        float c = (float) Math.cos(radians) * scale;
        return new RSXform(c, s, tx + -c * ax + s * ay, ty + -s * ax - c * ay);
    }
}
