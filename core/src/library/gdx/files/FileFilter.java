package library.gdx.files;

public class FileFilter {
    public final String description;
    public final String[] extensions;

    public FileFilter(String filterName, String... extensions) {
        this.description = filterName;
        this.extensions = extensions;
    }

    public static FileFilter[] createSingle(String filterName, String... extensions) {
        return new FileFilter[]{new FileFilter(filterName, extensions)};
    }
}