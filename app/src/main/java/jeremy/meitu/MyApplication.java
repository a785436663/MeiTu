package jeremy.meitu;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this);
		Utils.init(this);
		Timber.plant(new Timber.DebugTree());
	}
}