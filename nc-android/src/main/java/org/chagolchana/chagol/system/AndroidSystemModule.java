package org.chagolchana.chagol.system;

import android.app.Application;

import org.chagolchana.chagol.api.system.AndroidExecutor;
import org.chagolchana.chagol.api.system.LocationUtils;
import org.chagolchana.chagol.api.system.SecureRandomProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidSystemModule {

	@Provides
	@Singleton
	SecureRandomProvider provideSecureRandomProvider(Application app) {
		return new AndroidSecureRandomProvider(app);
	}

	@Provides
	LocationUtils provideLocationUtils(Application app) {
		return new AndroidLocationUtils(app);
	}

	@Provides
	@Singleton
	AndroidExecutor provideAndroidExecutor(Application app) {
		return new AndroidExecutorImpl(app);
	}
}
