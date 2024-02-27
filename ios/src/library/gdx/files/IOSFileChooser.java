package library.gdx.files;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

public class IOSFileChooser implements FileChooser{
    private IOSApplication application;
    public IOSFileChooser(IOSApplication application) {
        this.application=application;
    }

    @Override
    public void open(FileOptions options, FileChooserCallback callback) {

    }
}
