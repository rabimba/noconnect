package org.chagolchana.chagol.lifecycle;

import org.chagolchana.chagol.api.lifecycle.ShutdownManager;
import org.chagolchana.chagol.util.OsUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DesktopLifecycleModule extends LifecycleModule {

	@Provides
	@Singleton
	ShutdownManager provideDesktopShutdownManager() {
		if (OsUtils.isWindows()) return new WindowsShutdownManagerImpl();
		else return new ShutdownManagerImpl();
	}
}
