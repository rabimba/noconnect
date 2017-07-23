package org.chagolchana.noconnect.android.activity;

import android.app.Activity;
import android.content.SharedPreferences;

import org.chagolchana.noconnect.android.controller.BriarController;
import org.chagolchana.noconnect.android.controller.BriarControllerImpl;
import org.chagolchana.noconnect.android.controller.ConfigController;
import org.chagolchana.noconnect.android.controller.ConfigControllerImpl;
import org.chagolchana.noconnect.android.controller.DbController;
import org.chagolchana.noconnect.android.controller.DbControllerImpl;
import org.chagolchana.noconnect.android.login.PasswordController;
import org.chagolchana.noconnect.android.login.PasswordControllerImpl;
import org.chagolchana.noconnect.android.login.SetupController;
import org.chagolchana.noconnect.android.login.SetupControllerImpl;
import org.chagolchana.noconnect.android.navdrawer.NavDrawerController;
import org.chagolchana.noconnect.android.navdrawer.NavDrawerControllerImpl;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;
import static org.chagolchana.noconnect.android.BriarService.BriarServiceConnection;

@Module
public class ActivityModule {

	private final BaseActivity activity;

	public ActivityModule(BaseActivity activity) {
		this.activity = activity;
	}

	@ActivityScope
	@Provides
	BaseActivity provideBaseActivity() {
		return activity;
	}

	@ActivityScope
	@Provides
	Activity provideActivity() {
		return activity;
	}

	@ActivityScope
	@Provides
	SetupController provideSetupController(
			SetupControllerImpl setupController) {
		return setupController;
	}

	@ActivityScope
	@Provides
	ConfigController provideConfigController(
			ConfigControllerImpl configController) {
		return configController;
	}

	@ActivityScope
	@Provides
	SharedPreferences provideSharedPreferences(Activity activity) {
		return activity.getSharedPreferences("db", MODE_PRIVATE);
	}

	@ActivityScope
	@Provides
	PasswordController providePasswordController(
			PasswordControllerImpl passwordController) {
		return passwordController;
	}

	@ActivityScope
	@Provides
	protected BriarController provideBriarController(
			BriarControllerImpl briarController) {
		activity.addLifecycleController(briarController);
		return briarController;
	}

	@ActivityScope
	@Provides
	DbController provideDBController(DbControllerImpl dbController) {
		return dbController;
	}

	@ActivityScope
	@Provides
	NavDrawerController provideNavDrawerController(
			NavDrawerControllerImpl navDrawerController) {
		activity.addLifecycleController(navDrawerController);
		return navDrawerController;
	}

	@ActivityScope
	@Provides
	BriarServiceConnection provideBriarServiceConnection() {
		return new BriarServiceConnection();
	}

}
