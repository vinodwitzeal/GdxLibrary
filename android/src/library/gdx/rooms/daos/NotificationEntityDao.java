package library.gdx.rooms.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import library.gdx.rooms.entities.NotificationEntity;

@Dao
public interface NotificationEntityDao {
    @Query("SELECT * FROM notificationentity")
    List<NotificationEntity> loadAll();

    @Query("SELECT * FROM notificationentity WHERE id IN (:ids)")
    List<NotificationEntity> loadAllByIds(int[] ids);

    @Query("SELECT * FROM notificationentity WHERE id is (:id)")
    NotificationEntity loadById(int id);
    @Insert
    void insert(NotificationEntity entity);

    @Insert
    void insertAll(NotificationEntity...entities);

    @Update
    void update(NotificationEntity entity);

    @Delete
    void delete(NotificationEntity entity);

    @Query("DELETE FROM notificationentity")
    void deleteAll();
}
