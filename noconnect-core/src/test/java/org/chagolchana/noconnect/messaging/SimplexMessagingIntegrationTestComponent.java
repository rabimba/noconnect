package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.sync.SyncSessionFactory;
import org.chagolchana.chagol.api.transport.KeyManager;
import org.chagolchana.chagol.api.transport.StreamReaderFactory;
import org.chagolchana.chagol.api.transport.StreamWriterFactory;
import org.chagolchana.chagol.client.ClientModule;
import org.chagolchana.chagol.contact.ContactModule;
import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.data.DataModule;
import org.chagolchana.chagol.db.DatabaseModule;
import org.chagolchana.chagol.event.EventModule;
import org.chagolchana.chagol.identity.IdentityModule;
import org.chagolchana.chagol.lifecycle.LifecycleModule;
import org.chagolchana.chagol.sync.SyncModule;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.test.TestDatabaseModule;
import org.chagolchana.chagol.test.TestPluginConfigModule;
import org.chagolchana.chagol.test.TestSeedProviderModule;
import org.chagolchana.chagol.transport.TransportModule;
import org.chagolchana.noconnect.api.messaging.MessagingManager;
import org.chagolchana.noconnect.api.messaging.PrivateMessageFactory;
import org.chagolchana.noconnect.client.BriarClientModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		TestDatabaseModule.class,
		TestPluginConfigModule.class,
		TestSeedProviderModule.class,
		BriarClientModule.class,
		ClientModule.class,
		ContactModule.class,
		CryptoModule.class,
		DataModule.class,
		DatabaseModule.class,
		EventModule.class,
		IdentityModule.class,
		LifecycleModule.class,
		MessagingModule.class,
		SyncModule.class,
		SystemModule.class,
		TransportModule.class
})
interface SimplexMessagingIntegrationTestComponent {

	void inject(MessagingModule.EagerSingletons init);

	void inject(SystemModule.EagerSingletons init);

	LifecycleManager getLifecycleManager();

	IdentityManager getIdentityManager();

	ContactManager getContactManager();

	MessagingManager getMessagingManager();

	KeyManager getKeyManager();

	PrivateMessageFactory getPrivateMessageFactory();

	EventBus getEventBus();

	StreamWriterFactory getStreamWriterFactory();

	StreamReaderFactory getStreamReaderFactory();

	SyncSessionFactory getSyncSessionFactory();
}
