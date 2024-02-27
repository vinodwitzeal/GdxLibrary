package library.gdx.toast;

import com.badlogic.gdx.math.Vector2;

import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.uikit.NSTextAlignment;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIFont;

public class CSToastStyle {
    public UIColor backgroundColor;
    public UIColor titleColor;
    public UIColor messageColor;
    public double maxWidthPercentage;
    public double maxHeightPercentage;
    public double horizontalPadding;
    public double verticalPadding;
    public double cornerRadius;
    public UIFont titleFont;
    public UIFont messageFont;
    public NSTextAlignment titleAlignment;
    public NSTextAlignment messageAlignment;
    public int titleNumberOfLines;

    public int messageNumberOfLines;
    public boolean displayShadow;
    public UIColor shadowColor;
    public float shadowOpacity;
    public double shadowRadius;
    public CGSize shadowOffset;
    public CGSize imageSize;
    public CGSize activitySize;
    public double fadeDuration;

    public CSToastStyle(){

    }

    public static CSToastStyle getDefaultStyle(){
        CSToastStyle style=new CSToastStyle();
        style.backgroundColor =UIColor.black().addAlpha(0.8);
        style.titleColor =UIColor.white();
        style.messageColor =UIColor.white();
        style.maxWidthPercentage = 0.8;
        style.maxHeightPercentage = 0.8;
        style.horizontalPadding = 10.0;
        style.verticalPadding = 10.0;
        style.cornerRadius = 10.0;
        style.titleFont = UIFont.getBoldSystemFont(16.0);
        style.messageFont =UIFont.getSystemFont(16.0);
        style.titleAlignment = NSTextAlignment.Left;
        style.messageAlignment = NSTextAlignment.Left;
        style.titleNumberOfLines = 1;
        style.messageNumberOfLines = 1;
        style.displayShadow = false;
        style.shadowOpacity = 0.8f;
        style.shadowRadius = 6.0;
        style.shadowOffset = new CGSize(4.0,4.0);
        style.imageSize =new CGSize(80.0, 80.0);
        style.activitySize = new CGSize(100.0, 100.0);
        style.fadeDuration = 0.2;
        return style;
    }
}
