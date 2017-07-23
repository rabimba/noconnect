package org.chagolchana.chagol.test;

import org.chagolchana.chagol.api.system.SecureRandomProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestSeedProviderModule {

	@Provides
	@Singleton
	SecureRandomProvider provideSeedProvider() {
		return new TestSecureRandomProvider();
	}
}
