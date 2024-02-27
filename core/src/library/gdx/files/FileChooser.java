package library.gdx.files;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public interface FileChooser {
    void open(FileOptions options,FileChooserCallback callback);
    interface FileChooserCallback{
        void canceled();
        void selected(Array<FileHandle> fileHandles);

        void error(String error);
    }
}
