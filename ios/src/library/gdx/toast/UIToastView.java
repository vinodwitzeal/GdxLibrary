package library.gdx.toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSUIViewController;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import org.robovm.apple.coreanimation.CALayer;
import org.robovm.apple.coregraphics.CGPoint;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSInvocation;
import org.robovm.apple.foundation.NSRunLoop;
import org.robovm.apple.foundation.NSRunLoopMode;
import org.robovm.apple.foundation.NSTimer;
import org.robovm.apple.uikit.NSLineBreakMode;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIColor;
import org.robovm.apple.uikit.UIDevice;
import org.robovm.apple.uikit.UIEdgeInsets;
import org.robovm.apple.uikit.UIImage;
import org.robovm.apple.uikit.UIImageView;
import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIView;
import org.robovm.apple.uikit.UIViewAnimationOptions;
import org.robovm.apple.uikit.UIViewAutoresizing;
import org.robovm.apple.uikit.UIViewContentMode;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.objc.block.VoidBooleanBlock;

import java.util.ArrayList;
import java.util.List;

public class UIToastView extends UIView{
    private UIView parentView;
    private UIToastView(UIView parentView,String title, String message, UIImage image, CSToastStyle style){
        super();
        this.parentView=parentView;
        if (message==null && title==null && image==null){
            throw new RuntimeException("Invalid Arguments");
        }
        if (style==null){
            style=CSToastStyle.getDefaultStyle();
        }

        UILabel messageLabel=null;
        UILabel titleLabel=null;
        UIImageView imageView=null;
        setAutoresizingMask(new UIViewAutoresizing(UIViewAutoresizing.FlexibleLeftMargin.value()|UIViewAutoresizing.FlexibleRightMargin.value()|UIViewAutoresizing.FlexibleTopMargin.value()|UIViewAutoresizing.FlexibleBottomMargin.value()));
        CALayer layer=getLayer();
        layer.setCornerRadius(style.cornerRadius);

        if (style.displayShadow) {
            layer.setShadowColor(style.shadowColor.getCGColor());
            layer.setShadowOpacity(style.shadowOpacity);
            layer.setShadowRadius(style.shadowRadius);
            layer.setShadowOffset(style.shadowOffset);
        }

        setBackgroundColor(style.backgroundColor);

        if(image != null) {
            imageView=new UIImageView(image);
            imageView.setContentMode(UIViewContentMode.ScaleAspectFit);
            imageView.setFrame(new CGRect(style.horizontalPadding, style.verticalPadding, style.imageSize.getWidth(), style.imageSize.getHeight()));
        }

        CGRect imageRect =  new CGRect(0,0,0,0);

        if(imageView != null) {
            imageRect.getOrigin().setX(style.horizontalPadding);
            imageRect.getOrigin().setY(style.verticalPadding);
            imageRect.getSize().setWidth(imageView.getBounds().getSize().getWidth());
            imageRect.getSize().setHeight(imageView.getBounds().getSize().getHeight());
        }

        if (title != null) {
            titleLabel =new UILabel();
            titleLabel.setNumberOfLines(style.titleNumberOfLines);
            titleLabel.setFont(style.titleFont);
            titleLabel.setTextAlignment(style.titleAlignment);
            titleLabel.setLineBreakMode(NSLineBreakMode.TruncatingTail);
            titleLabel.setTextColor(style.titleColor);
            titleLabel.setBackgroundColor(UIColor.clear());
            titleLabel.setAlpha(1.0);
            titleLabel.setText(title);
            // size the title label according to the length of the text
            CGSize maxSizeTitle = new CGSize((parentView.getBounds().getWidth() * style.maxWidthPercentage) - imageRect.getSize().getWidth(), parentView.getBounds().getHeight() * style.maxHeightPercentage);
            CGSize expectedSizeTitle =titleLabel.getSizeThatFits(maxSizeTitle);
            // UILabel can return a size larger than the max size when the number of lines is 1
            expectedSizeTitle = new CGSize(Math.min(maxSizeTitle.getWidth(), expectedSizeTitle.getHeight()), Math.min(maxSizeTitle.getHeight(), expectedSizeTitle.getHeight()));
            titleLabel.setFrame(new CGRect(0.0, 0.0, expectedSizeTitle.getWidth(), expectedSizeTitle.getHeight()));
        }

        if (message != null) {

            messageLabel =new UILabel();
            messageLabel.setNumberOfLines(style.messageNumberOfLines);
            messageLabel.setFont(style.messageFont);
            messageLabel.setTextAlignment(style.messageAlignment);
            messageLabel.setLineBreakMode(NSLineBreakMode.TruncatingTail);
            messageLabel.setTextColor(style.messageColor);
            messageLabel.setBackgroundColor(UIColor.clear());
            messageLabel.setAlpha(1.0);
            messageLabel.setText(message);

            // size the Message label according to the length of the text
            CGSize maxSizeMessage = new CGSize((parentView.getBounds().getWidth() * style.maxWidthPercentage) - imageRect.getSize().getWidth(), parentView.getBounds().getHeight() * style.maxHeightPercentage);
            Gdx.app.error("Max Message Size","Width=>"+maxSizeMessage.getWidth()+",Height=>"+maxSizeMessage.getHeight());

            CGSize expectedSizeMessage =messageLabel.getSizeThatFits(maxSizeMessage);
            Gdx.app.error("Expected Message Size","Width=>"+expectedSizeMessage.getWidth()+",Height=>"+expectedSizeMessage.getHeight());
            // UILabel can return a size larger than the max size when the number of lines is 1
            expectedSizeMessage = new CGSize(Math.min(maxSizeMessage.getWidth(), expectedSizeMessage.getWidth()), Math.min(maxSizeMessage.getHeight(), expectedSizeMessage.getHeight()));
            messageLabel.setFrame(new CGRect(0.0, 0.0, expectedSizeMessage.getWidth(), expectedSizeMessage.getHeight()));
            Gdx.app.error("Message Size","Width=>"+messageLabel.getBounds().getWidth()+",Height=>"+messageLabel.getBounds().getHeight());
        }

        CGRect titleRect =  new CGRect(0,0,0,0);

        if(titleLabel != null) {
            titleRect.setX(imageRect.getX() + imageRect.getWidth() + style.horizontalPadding);
            titleRect.setY(style.verticalPadding);
            titleRect.setWidth(titleLabel.getBounds().getWidth());
            titleRect.setHeight(titleLabel.getBounds().getHeight());
        }

        CGRect messageRect = new CGRect(0,0,0,0);

        if(messageLabel != null) {
            messageRect.setX(imageRect.getX() + imageRect.getWidth() + style.horizontalPadding);
            messageRect.setY(titleRect.getY()+titleRect.getHeight()+style.verticalPadding);
            messageRect.setWidth(messageLabel.getBounds().getWidth());
            messageRect.setHeight(messageLabel.getBounds().getHeight());
            Gdx.app.error("Message Rect Size","Width=>"+messageRect.getWidth()+",Height=>"+messageRect.getHeight());
        }


        double longerWidth = Math.max(titleRect.getSize().getWidth(), messageRect.getWidth());
        double longerX = Math.max(titleRect.getX(), messageRect.getX());

        // Wrapper width uses the longerWidth or the image width, whatever is larger. Same logic applies to the wrapper height.
        double wrapperWidth = Math.max((imageRect.getSize().getWidth() + (style.horizontalPadding * 2.0)), (longerX + longerWidth + style.horizontalPadding));
        double wrapperHeight = Math.max((messageRect.getY() + messageRect.getHeight() + style.verticalPadding), (imageRect.getSize().getHeight() + (style.verticalPadding * 2.0)));

        Gdx.app.error("Wrapper","Width=>"+wrapperWidth+",Height=>"+wrapperHeight);
        setFrame(new CGRect(0,0,wrapperWidth,wrapperHeight));

        if(titleLabel != null) {
            titleLabel.setFrame(titleRect);
            addSubview(titleLabel);
        }

        if(messageLabel != null) {
            messageLabel.setFrame(messageRect);
            addSubview(messageLabel);
        }

        if(imageView != null) {
            addSubview(imageView);
        }
    }

    private static List<UIToastView> toastQueue=new ArrayList<>();

    public static void showToast(UIView parentView,String title,String message,UIImage image){
        UIToastView toast=new UIToastView(parentView,title,message,image,CSToastStyle.getDefaultStyle());

        toast.setCenter(getToastPosition(toast));
        toast.setAlpha(0.0);
        queueToast(toast);
    }

    private static void queueToast(UIToastView toast){
        if (toastQueue.size()==0){
            toastQueue.add(toast);
            show(toast);
        }else {
            toastQueue.add(toast);
        }
    }

    private static void show(UIToastView toast){
        toast.parentView.addSubview(toast);
        UIViewAnimationOptions options=new UIViewAnimationOptions(UIViewAnimationOptions.CurveEaseOut.value() | UIViewAnimationOptions.AllowUserInteraction.value());

        Gdx.app.error("Animation Start", TimeUtils.millis()+"");
        UIView.animate(0.1, 0.0, options, new Runnable() {
            @Override
            public void run() {
                toast.setAlpha(1.0);
            }
        }, new VoidBooleanBlock() {
            @Override
            public void invoke(boolean v) {
                NSTimer timer=new NSTimer(3.0, new VoidBlock1<NSTimer>() {
                    @Override
                    public void invoke(NSTimer timer) {
                        hideToast(timer,toast);
                    }
                }, toast, false);
                NSRunLoop.getMain().addTimer(NSRunLoopMode.Common,timer);
            }
        });
    }


    private static void hideToast(NSTimer timer,UIToastView toast){
        timer.invalidate();
        UIViewAnimationOptions options=new UIViewAnimationOptions(UIViewAnimationOptions.CurveEaseIn.value()|UIViewAnimationOptions.BeginFromCurrentState.value());
        UIView.animate(0.2, 0.0, options, new Runnable() {
            @Override
            public void run() {
                toast.setAlpha(0.0);
            }
        }, new VoidBooleanBlock() {
            @Override
            public void invoke(boolean completed) {
                toast.removeFromSuperview();
                toastQueue.remove(toast);
                if (toastQueue.size()>0){
                    UIToastView nextToast=toastQueue.get(0);
                    show(nextToast);
                }
            }
        });
    }

    private static CGPoint getToastPosition(UIToastView toastView){
        UIView parentView=toastView.parentView;
        CSToastStyle style=CSToastStyle.getDefaultStyle();
        double bottomPadding=style.verticalPadding;
        return new CGPoint(parentView.getBounds().getWidth()/2.0,(parentView.getBounds().getSize().getHeight()-(toastView.getFrame().getSize().getHeight()/2.0))-bottomPadding);

    }

}
