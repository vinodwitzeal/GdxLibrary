package library.gdx.files;

import com.badlogic.gdx.files.FileHandle;

public class FileOptions {
    public static FileFilter[] none= new FileFilter[0];
    public FileAction action=FileAction.OpenFile;
    public FileHandle directory;
    public FileFilter[] filters=none;

}
