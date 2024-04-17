package library.gdx;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidXApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import library.gdx.handlers.AndroidHandler;
import library.gdx.handlers.PlatformHandler;
import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.dbs.AppDataBase;
import library.gdx.rooms.dbs.DBCallback;
import library.gdx.rooms.entities.NotificationEntity;

public class AndroidLauncher extends AndroidXApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.a=8;
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useImmersiveMode=true;
		AppDataBase.deleteOldNotifications(getApplicationContext(), TimeUnit.DAYS.toMillis(7), new DBCallback<String>() {
			@Override
			public void onCompleted(boolean success, String response) {
				Log.e("Notifications","Deleted");
				AppDataBase.addNotifications(getApplicationContext(), getNotifications(), new DBCallback<String>() {
					@Override
					public void onCompleted(boolean success, String response) {

					}
				});
			}
		});

		initialize(new SceneManager(){
			@Override
			public PlatformHandler createPlatformHandler() {
				return new AndroidHandler(AndroidLauncher.this);
			}
		}, config);
	}

	private List<NotificationEntity> getNotifications(){
		List<NotificationEntity> notificationEntities=new ArrayList<>();
		for (int i=0;i<1000;i++){
			NotificationEntity notificationEntity=new NotificationEntity();
			notificationEntity.notificationId= UUID.randomUUID().toString();
			notificationEntity.title="Title"+System.currentTimeMillis();
			notificationEntity.message="Message Count"+i+""+System.currentTimeMillis();
			notificationEntity.action="action";
			notificationEntity.time=System.currentTimeMillis();
			notificationEntities.add(notificationEntity);
		}
		return notificationEntities;
	}
}
