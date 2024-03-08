package library.gdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidXApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.webrtc.PeerConnection;

import java.util.List;
import java.util.UUID;

import library.gdx.handlers.AndroidHandler;
import library.gdx.handlers.PlatformHandler;
import library.gdx.rooms.DatabaseUtil;
import library.gdx.rooms.daos.NotificationEntityDao;
import library.gdx.rooms.dbs.AppDataBase;
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

		AppDataBase appDataBase=DatabaseUtil.getAppDataBase(getApplicationContext());
		NotificationEntityDao notificationEntityDao=appDataBase.notificationEntityDao();
		AppDataBase.databaseWriteExecutor.execute(new Runnable() {
			@Override
			public void run() {
				List<NotificationEntity> allNotifications=notificationEntityDao.loadAll();
				if (allNotifications.isEmpty()){
					insertNotifications(notificationEntityDao);
				}
			}
		});




		initialize(new SceneManager(){
			@Override
			public PlatformHandler createPlatformHandler() {
				return new AndroidHandler(AndroidLauncher.this);
			}
		}, config);
	}

	private void insertNotifications(NotificationEntityDao entityDao){
		for (int i=0;i<10;i++){
			NotificationEntity notificationEntity=new NotificationEntity();
			notificationEntity.id= UUID.randomUUID().toString();
			notificationEntity.title="Title"+System.currentTimeMillis();
			notificationEntity.message="Message Count"+i+""+System.currentTimeMillis();
			notificationEntity.action="action";
			entityDao.insert(notificationEntity);
		}
	}
}
