package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.client.ClientModule;
import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.data.DataModule;
import org.chagolchana.chagol.db.DatabaseModule;
import org.chagolchana.chagol.event.EventModule;
import org.chagolchana.chagol.identity.IdentityModule;
import org.chagolchana.chagol.sync.SyncModule;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.test.TestDatabaseModule;
import org.chagolchana.chagol.test.TestLifecycleModule;
import org.chagolchana.chagol.test.TestSeedProviderModule;
import org.chagolchana.noconnect.client.BriarClientModule;
import org.chagolchana.noconnect.forum.ForumModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		TestDatabaseModule.class,
		TestLifecycleModule.class,
		TestSeedProviderModule.class,
		BriarClientModule.class,
		ClientModule.class,
		CryptoModule.class,
		DataModule.class,
		DatabaseModule.class,
		EventModule.class,
		ForumModule.class,
		IdentityModule.class,
		MessagingModule.class,
		SyncModule.class,
		SystemModule.class
})
interface MessageSizeIntegrationTestComponent {

	void inject(MessageSizeIntegrationTest testCase);

	void inject(SystemModule.EagerSingletons init);
}
