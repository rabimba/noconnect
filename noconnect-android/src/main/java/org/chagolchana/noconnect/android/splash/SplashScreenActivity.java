package org.chagolchana.noconnect.android.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.VmPolicy;
import android.support.v7.preference.PreferenceManager;
import android.transition.Fade;

import org.chagolchana.chagol.api.system.AndroidExecutor;
import org.chagolchana.noconnect.R;
import org.chagolchana.noconnect.android.activity.ActivityComponent;
import org.chagolchana.noconnect.android.activity.BaseActivity;
import org.chagolchana.noconnect.android.controller.ConfigController;
import org.chagolchana.noconnect.android.login.SetupActivity;
import org.chagolchana.noconnect.android.navdrawer.NavDrawerActivity;

import java.util.logging.Logger;

import javax.inject.Inject;

import static org.chagolchana.noconnect.android.BriarApplication.EXPIRY_DATE;
import static org.chagolchana.noconnect.android.TestingConstants.DEFAULT_LOG_LEVEL;
import static org.chagolchana.noconnect.android.TestingConstants.TESTING;

public class SplashScreenActivity extends BaseActivity {

	private static final Logger LOG =
			Logger.getLogger(SplashScreenActivity.class.getName());

	@Inject
	protected ConfigController configController;
	@Inject
	protected AndroidExecutor androidExecutor;

	public SplashScreenActivity() {
		Logger.getLogger("").setLevel(DEFAULT_LOG_LEVEL);
		enableStrictMode();
	}

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);

		if (Build.VERSION.SDK_INT >= 21) {
			getWindow().setExitTransition(new Fade());
		}

		setPreferencesDefaults();

		setContentView(R.layout.splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startNextActivity();
				supportFinishAfterTransition();
			}
		}, 500);
	}

	@Override
	public void injectActivity(ActivityComponent component) {
		component.inject(this);
	}

	protected void startNextActivity() {
		boolean hasExpired = System.currentTimeMillis() >= EXPIRY_DATE;
		if (hasExpired) {
			LOG.info("Expired");
			//startActivity(new Intent(this, ExpiredActivity.class));
		}
		if (configController.accountExists()) {
			startActivity(new Intent(this, NavDrawerActivity.class));
		} else {
			configController.deleteAccount(this);
			startActivity(new Intent(this, SetupActivity.class));
		}
	}

	private void enableStrictMode() {
		if (TESTING) {
			ThreadPolicy.Builder threadPolicy = new ThreadPolicy.Builder();
			threadPolicy.detectAll();
			threadPolicy.penaltyLog();
			StrictMode.setThreadPolicy(threadPolicy.build());
			VmPolicy.Builder vmPolicy = new VmPolicy.Builder();
			vmPolicy.detectAll();
			vmPolicy.penaltyLog();
			StrictMode.setVmPolicy(vmPolicy.build());
		}
	}

	private void setPreferencesDefaults() {
		androidExecutor.runOnBackgroundThread(new Runnable() {
			@Override
			public void run() {
				PreferenceManager.setDefaultValues(SplashScreenActivity.this,
						R.xml.panic_preferences, false);
			}
		});
	}
}
