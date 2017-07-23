package org.chagolchana.chagol.system;

import org.chagolchana.chagol.api.system.SecureRandomProvider;
import org.chagolchana.chagol.util.OsUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DesktopSecureRandomModule {

	@Provides
	@Singleton
	SecureRandomProvider provideSecureRandomProvider() {
		return OsUtils.isLinux() ? new LinuxSecureRandomProvider() : null;
	}
}
