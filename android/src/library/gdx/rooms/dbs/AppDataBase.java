package library.gdx.rooms.dbs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.entities.NotificationEntity;

@Database(entities = {NotificationEntity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {


    private static AppDataBase instance;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(3);


    public static AppDataBase getInstance(Context applicationContext){
        if (instance==null){
            synchronized (AppDataBase.class) {
                if (instance==null) {
                    instance = Room.databaseBuilder(applicationContext,
                            AppDataBase.class, "bigcash-database").build();
                }
            }
        }
        return instance;
    }
    public abstract NotificationEntityDao notificationEntityDao();
}
