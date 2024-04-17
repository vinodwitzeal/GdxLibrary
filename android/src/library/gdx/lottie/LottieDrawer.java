package library.gdx.lottie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.ByteBuffer;

public class LottieDrawer {
    protected Context context;
    private Canvas canvas;
    private Bitmap bitmap;

    public LottieDrawer(Context context){
        this.context=context;
    }


    protected Bitmap getBitmapFromViewUsingCanvas(View view) {
        // Create a new Bitmap object with the desired width and height
//        if (bitmap==null){
            int width=view.getWidth();
            int height=view.getHeight();
            bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888,true);
//        }else {
//            bitmap.setWidth(view.getWidth());
//            bitmap.setHeight(view.getHeight());
//        }

//        if (canvas==null){
            canvas=new Canvas(bitmap);
//        }else {
//            canvas.setBitmap(bitmap);
//        }
        canvas.drawColor(Color.TRANSPARENT);
        view.draw(canvas);
        return bitmap;
    }


    protected Pixmap getPixmapFromBitmap(Bitmap bitmap) {
        byte[] pixels = new byte[bitmap.getWidth() * bitmap.getHeight() * 4];
        ByteBuffer buf = ByteBuffer.wrap(pixels);
        bitmap.copyPixelsToBuffer(buf);
        Pixmap pixmap = new Pixmap(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        return pixmap;
    }


    protected Texture getTextureFromPixmap(Pixmap pixmap){
        Texture texture = new Texture(pixmap);
        pixmap.dispose(); // Don't forget to dispose of the Pixmap to free up memory
        return texture;
    }



    protected Texture getTextureFromBitmap(Bitmap bitmap){
        Texture tex = new Texture(bitmap.getWidth(), bitmap.getHeight(), Pixmap.Format.RGBA8888);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex.getTextureObjectHandle());
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        bitmap.recycle();
        return tex;
    }

    protected Texture getTextureFromView(View view){
        try {
            return getTextureFromPixmap(getPixmapFromBitmap(getBitmapFromViewUsingCanvas(view)));
        }catch (Exception ignored){

        }
        return null;

    }
}
