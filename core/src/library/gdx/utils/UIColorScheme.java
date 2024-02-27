package library.gdx.utils;

import com.badlogic.gdx.graphics.Color;

public class UIColorScheme {
    public static UIColorScheme light=new LightColorScheme();
    public static UIColorScheme dark=new DarkColorScheme();


    public Color primary,onPrimary,primaryContainer,onPrimaryContainer;
    public Color secondary,onSecondary,secondaryContainer,onSecondaryContainer;
    public Color tertiary,onTertiary,tertiaryContainer,onTertiaryContainer;
    public Color error,onError,errorContainer,onErrorContainer;
    public Color background,onBackground;
    public Color surface,onSurface,surfaceVariant,onSurfaceVariant;
    public Color outline;
    public Color onInverseSurface,inverseSurface,inversePrimary;
    public Color shadow,surfaceTint,outlineVariant;
    public Color scrim;

    public UIColorScheme(){
        super();
    }



    private static class LightUIColorScheme extends UIColorScheme {
        public LightUIColorScheme() {
            super();
            primary = new Color(0xB4252EFF);
            onPrimary = new Color(0xFFFFFFFF);
            primaryContainer = new Color(0xFFDAD7FF);
            onPrimaryContainer = new Color(0x410005FF);
            secondary = new Color(0x0261A3FF);
            onSecondary = new Color(0xFFFFFFFF);
            secondaryContainer = new Color(0xD1E4FFFF);
            onSecondaryContainer = new Color(0x001D36FF);
            tertiary = new Color(0x006E11FF);
            onTertiary = new Color(0xFFFFFFFF);
            tertiaryContainer = new Color(0x92FA86FF);
            onTertiaryContainer = new Color(0x002202FF);
            error = new Color(0xBA1A1AFF);
            errorContainer = new Color(0xFFDAD6FF);
            onError = new Color(0xFFFFFFFF);
            onErrorContainer = new Color(0x410002FF);
            background = new Color(0xFFFBFFFF);
            onBackground = new Color(0x2C1700FF);
            surface = new Color(0xFFFBFFFF);
            onSurface = new Color(0x2C1700FF);
            surfaceVariant = new Color(0xF4DDDBFF);
            onSurfaceVariant = new Color(0x534342FF);
            outline = new Color(0x857372FF);
            onInverseSurface = new Color(0xFFEEE0FF);
            inverseSurface = new Color(0x482900FF);
            inversePrimary = new Color(0xFFB3AFFF);
            shadow = new Color(0x000000FF);
            surfaceTint = new Color(0xB4252EFF);
            outlineVariant = new Color(0xD8C1C0FF);
            scrim = new Color(0x000000FF);
        }
    }

    private static class DarkColorScheme extends UIColorScheme {
        public DarkColorScheme() {
            super();
            primary = new Color(0xFFB3AFFF);
            onPrimary = new Color(0x68000EFF);
            primaryContainer = new Color(0x920419FF);
            onPrimaryContainer = new Color(0xFFDAD7FF);
            secondary = new Color(0x9ECAFFFF);
            onSecondary = new Color(0x003258FF);
            secondaryContainer = new Color(0x00497DFF);
            onSecondaryContainer = new Color(0xD1E4FFFF);
            tertiary = new Color(0x77DD6DFF);
            onTertiary = new Color(0x003A05FF);
            tertiaryContainer = new Color(0x00530AFF);
            onTertiaryContainer = new Color(0x92FA86FF);
            error = new Color(0xFFB4ABFF);
            errorContainer = new Color(0x93000AFF);
            onError = new Color(0x690005FF);
            onErrorContainer = new Color(0xFFDAD6FF);
            background = new Color(0x2C1700FF);
            onBackground = new Color(0xFFDCBBFF);
            surface = new Color(0x2C1700FF);
            onSurface = new Color(0xFFDCBBFF);
            surfaceVariant = new Color(0x534342FF);
            onSurfaceVariant = new Color(0xD8C1C0FF);
            outline = new Color(0xA08C8BFF);
            onInverseSurface = new Color(0x2C1700FF);
            inverseSurface = new Color(0xFFFFDCBB);
            inversePrimary = new Color(0xFFB4252E);
            shadow = new Color(0x000000FF);
            surfaceTint = new Color(0xFFB3AFFF);
            outlineVariant = new Color(0x534342FF);
            scrim = new Color(0x000000FF);
        }
    }

    private static class LightColorScheme extends UIColorScheme {
        public LightColorScheme() {
            super();
            primary = new Color(0xB4252EFF);
            onPrimary = new Color(0xFFFFFFFF);
            primaryContainer = new Color(0xFFDAD7FF);
            onPrimaryContainer = new Color(0x410005FF);
            secondary = new Color(0x0261A3FF);
            onSecondary = new Color(0xFFFFFFFF);
            secondaryContainer = new Color(0xD1E4FFFF);
            onSecondaryContainer = new Color(0x001D36FF);
            tertiary = new Color(0x006E11FF);
            onTertiary = new Color(0xFFFFFFFF);
            tertiaryContainer = new Color(0x92FA86FF);
            onTertiaryContainer = new Color(0x002202FF);
            error = new Color(0xBA1A1AFF);
            errorContainer = new Color(0xFFDAD6FF);
            onError = new Color(0xFFFFFFFF);
            onErrorContainer = new Color(0x410002FF);
            background = new Color(0xFFFBFFFF);
            onBackground = new Color(0x2C1700FF);
            surface = new Color(0xFFFBFFFF);
            onSurface = new Color(0x2C1700FF);
            surfaceVariant = new Color(0xF4DDDBFF);
            onSurfaceVariant = new Color(0x534342FF);
            outline = new Color(0x857372FF);
            onInverseSurface = new Color(0xFFEEE0FF);
            inverseSurface = new Color(0x482900FF);
            inversePrimary = new Color(0xFFB3AFFF);
            shadow = new Color(0x000000FF);
            surfaceTint = new Color(0xB4252EFF);
            outlineVariant = new Color(0xD8C1C0FF);
            scrim = new Color(0x000000FF);
        }
    }
}

