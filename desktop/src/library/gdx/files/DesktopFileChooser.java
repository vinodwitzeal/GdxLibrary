package library.gdx.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.nfd.NFDPathSet;
import org.lwjgl.util.nfd.NativeFileDialog;

public class DesktopFileChooser implements FileChooser{
    private Lwjgl3Application application;
    public DesktopFileChooser(Lwjgl3Application application){
        this.application=application;
    }
    @Override
    public void open(FileOptions options, FileChooserCallback callback) {
        switch (options.action){
            case OpenFile:
                openFile("Select File",options.directory,options.filters,callback);
                break;
            case OpenMultipleFiles:
                openMultipleFiles("Select File",options.directory,options.filters,callback);
                break;
            case OpenFolder:
                pickDirectory("Select Folder",options.directory,callback);
                break;
            case SaveFiles:
                saveFile("Save File",options.directory,options.filters,callback);
                break;
            default:
                callback.error("Unknown Action");
                break;
        }
    }


    public void pickDirectory(String dialogTitle, FileHandle initialFile, FileChooserCallback callback) {
        String initialPath = prepareInitialPath(initialFile);

        PointerBuffer pathPointer = MemoryUtil.memAllocPointer(1);

        try {
            int status = NativeFileDialog.NFD_PickFolder(initialPath, pathPointer);

            if (status == NativeFileDialog.NFD_CANCEL) {
                callback.canceled();
                return;
            }

            // Unexpected error.
            if (status != NativeFileDialog.NFD_OKAY) {
                String errorText = NativeFileDialog.NFD_GetError();
                throw new RuntimeException("Native file dialog error: " + errorText);
            }

            String selectedFolderPath = pathPointer.getStringUTF8(0);
            NativeFileDialog.nNFD_Free(pathPointer.get(0));

            Array<FileHandle> array = new Array<>();
            array.add(Gdx.files.absolute(selectedFolderPath));

            callback.selected(array);
        } catch (Throwable e) {

            callback.error(e.getMessage());
        } finally {
            MemoryUtil.memFree(pathPointer);
        }
    }

    public void openFile(String dialogTitle,FileHandle initialFile,FileFilter[] fileFilters, FileChooserCallback callback) {
        String initialPath = prepareInitialPath(initialFile);
        String filterList = prepareFilterList(fileFilters);

        PointerBuffer pathPointer = MemoryUtil.memAllocPointer(1);

        try {
            int status = NativeFileDialog.NFD_OpenDialog(filterList, initialPath, pathPointer);

            if (status == NativeFileDialog.NFD_CANCEL) {
                callback.canceled();
                return;
            }

            // Unexpected error.
            if (status != NativeFileDialog.NFD_OKAY) {
                String errorText = NativeFileDialog.NFD_GetError();
                throw new RuntimeException("Native file dialog error: " + errorText);
            }

            String selectedFilePath = pathPointer.getStringUTF8(0);
            NativeFileDialog.nNFD_Free(pathPointer.get(0));

            Array<FileHandle> array = new Array<>();
            array.add(Gdx.files.absolute(selectedFilePath));

            callback.selected(array);
        } catch (Throwable e) {
            callback.error(e.getMessage());
        } finally {
            MemoryUtil.memFree(pathPointer);
        }
    }

    public void openMultipleFiles(String dialogTitle, FileHandle initialFile, FileFilter[] fileFilters, FileChooserCallback callback) {
        String initialPath = prepareInitialPath(initialFile);
        String filterList = prepareFilterList(fileFilters);

        NFDPathSet pathSet = NFDPathSet.create();

        try {
            int status = NativeFileDialog.NFD_OpenDialogMultiple(filterList, initialPath, pathSet);

            if (status == NativeFileDialog.NFD_CANCEL) {
                callback.canceled();
                return;
            }

            // Unexpected error.
            if (status != NativeFileDialog.NFD_OKAY) {
                String errorText = NativeFileDialog.NFD_GetError();
                throw new RuntimeException("Native file dialog error: " + errorText);
            }

            long selectedAmount = NativeFileDialog.NFD_PathSet_GetCount(pathSet);
            // If nothing is selected, consider the selection operation is canceled.
            if (selectedAmount == 0) {
                callback.canceled();
                return;
            }

            Array<FileHandle> array = new Array<>();
            for (long i = 0; i < selectedAmount; i++) {
                String selectedPath = NativeFileDialog.NFD_PathSet_GetPath(pathSet, i);
                array.add(Gdx.files.absolute(selectedPath));
            }

            callback.selected(array);
        } catch (Throwable e) {
            callback.error(e.getMessage());
        } finally {
            NativeFileDialog.NFD_PathSet_Free(pathSet);
        }
    }

    public void saveFile(String dialogTitle, FileHandle initialFile, FileFilter[] fileFilters, FileChooserCallback callback) {
        String initialPath = prepareInitialPath(initialFile);
        String filterList = prepareFilterList(fileFilters);

        PointerBuffer pathPointer = MemoryUtil.memAllocPointer(1);

        try {
            int status = NativeFileDialog.NFD_SaveDialog(filterList, initialPath, pathPointer);

            if (status == NativeFileDialog.NFD_CANCEL) {
                callback.canceled();
                return;
            }

            // Unexpected error.
            if (status != NativeFileDialog.NFD_OKAY) {
                String errorText = NativeFileDialog.NFD_GetError();
                throw new RuntimeException("Native file dialog error: " + errorText);
            }

            String selectedFilePath = pathPointer.getStringUTF8(0);
            NativeFileDialog.nNFD_Free(pathPointer.get(0));

            Array<FileHandle> array = new Array<>();
            array.add(Gdx.files.absolute(selectedFilePath));

            callback.selected(array);
        } catch (Throwable e) {
            callback.error(e.getMessage());
        } finally {
            MemoryUtil.memFree(pathPointer);
        }
    }

    private static String prepareInitialPath(FileHandle fileHandle) {
        if (fileHandle == null)
            return null;

        return fileHandle.file().getAbsolutePath();
    }

    private static String prepareFilterList(FileFilter[] filters) {
        if (filters == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filters.length; i++) {
            FileFilter filter = filters[i];
            if (sb.length() > 0) {
                sb.append(";");
            }
            sb.append(String.join(",", filter.extensions));
        }

        if (sb.length() == 0)
            return null;

        return sb.toString();
    }
}


