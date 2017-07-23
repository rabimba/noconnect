package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.test.TestSeedProviderModule;
import org.chagolchana.chagol.transport.TransportModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		TestSeedProviderModule.class,
		CryptoModule.class,
		SyncModule.class,
		TransportModule.class
})
interface SyncIntegrationTestComponent {

	void inject(SyncIntegrationTest testCase);
}
