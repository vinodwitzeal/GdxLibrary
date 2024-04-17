package library.gdx.rooms.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class NotificationEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public String notificationId;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "message")
    public String message;

    @ColumnInfo(name = "action")
    public String action;
    @ColumnInfo(name = "time")
    public long time;
    @ColumnInfo(name = "read")
    public boolean read;

}
