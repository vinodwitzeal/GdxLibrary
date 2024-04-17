package library.gdx.rooms.dbs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.entities.NotificationEntity;

@Database(entities = {NotificationEntity.class},version = 2)
public abstract class AppDataBase extends RoomDatabase {


    private static AppDataBase instance;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(3);


    public static AppDataBase getInstance(Context applicationContext){
        if (instance==null){
            synchronized (AppDataBase.class) {
                if (instance==null) {
                    instance = Room.databaseBuilder(applicationContext,
                            AppDataBase.class, "bigcash-database-2").build();
                }
            }
        }
        return instance;
    }

    public static NotificationEntityDao getNotificationDao(Context applicationContext){
        return getInstance(applicationContext).notificationEntityDao();
    }

    public static void deleteDatabase(Context applicationContext){
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                getInstance(applicationContext).clearAllTables();
            }
        });

    }

    public static void loadAllNotifications(Context applicationContext,DBCallback<List<NotificationEntity>> callback){
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NotificationEntityDao notificationEntityDao=getNotificationDao(applicationContext);
                callback.onCompleted(true,notificationEntityDao.loadAll());
            }
        });
    }

    public static void deleteOldNotifications(Context applicationContext,long oldByTime,DBCallback<String> callback){
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NotificationEntityDao notificationEntityDao=getNotificationDao(applicationContext);
                long beforeTime=System.currentTimeMillis()-oldByTime;
                List<NotificationEntity> notificationEntities=notificationEntityDao.loadNotificationBeforeTime(beforeTime);
                if (!notificationEntities.isEmpty()){
                    notificationEntityDao.delete(notificationEntities);
                }
                callback.onCompleted(true,"");
            }
        });
    }

    public static void addNotifications(Context applicationContext,List<NotificationEntity> notificationEntities,DBCallback<String> callback){
        databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NotificationEntityDao notificationEntityDao=getNotificationDao(applicationContext);
                notificationEntityDao.insertAll(notificationEntities);
                callback.onCompleted(true,"");
            }
        });
    }

    public static void addNotifications(NotificationEntity notificationEntity,DBCallback<String> callback){

    }

    public void deleteNotification(NotificationEntity notificationEntity,DBCallback<String> callback){

    }

    public void updateNotification(NotificationEntity notificationEntity,DBCallback<String> callback){

    }


    public abstract NotificationEntityDao notificationEntityDao();
}
