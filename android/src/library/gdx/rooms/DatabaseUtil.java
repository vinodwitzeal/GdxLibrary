package library.gdx.rooms;

import android.content.Context;

import androidx.room.Room;

import library.gdx.rooms.dbs.AppDataBase;

public class DatabaseUtil {
    private static AppDataBase appDataBase;

    public static AppDataBase getAppDataBase(Context applicationContext){
        if (appDataBase==null){
            appDataBase= Room.databaseBuilder(applicationContext,
                    AppDataBase.class, "bigcash-database").build();
        }
        return appDataBase;
    }
}
