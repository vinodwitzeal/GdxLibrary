package library.gdx.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameUtils {

    public static float getDensity(float width,float height){
        if (Gdx.app.getType()== Application.ApplicationType.Desktop){
            return 2.0f;
        }else {
            return round(Math.min(width,height)/360.0f);
        }
    }

    public static float round(float input){
        return Math.round(input*100.0f)/100.0f;
    }

    public static boolean isNotNullAndEmpty(String value){
        return !isNullOrEmpty(value);
    }

    public static boolean isNullOrEmpty(String value){
        return value==null || value.isEmpty();
    }

}
