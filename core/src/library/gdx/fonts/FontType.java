package library.gdx.fonts;

public enum FontType {
    LIGHT("light"),
    LIGHT_ITALIC("light_italic"),
    REGULAR("regular"),
    ITALIC("italic"),
    MEDIUM("medium"),
    MEDIUM_ITALIC("medium_italic"),
    BOLD("bold"),
    BOLD_ITALIC("bold_italic"),
    BLACK("black"),
    BLACK_ITALIC("black_italic");

    private String name;

    FontType(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
