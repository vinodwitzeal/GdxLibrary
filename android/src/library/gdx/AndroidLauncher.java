package library.gdx;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidXApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.webrtc.PeerConnection;

import library.gdx.handlers.AndroidHandler;
import library.gdx.handlers.PlatformHandler;

public class AndroidLauncher extends AndroidXApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.a=8;
		config.useAccelerometer=false;
		config.useCompass=false;
		config.useImmersiveMode=true;
		initialize(new SceneManager(){
			@Override
			public PlatformHandler createPlatformHandler() {
				return new AndroidHandler(AndroidLauncher.this);
			}
		}, config);
	}
}
