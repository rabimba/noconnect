package org.chagolchana.noconnect.feed;

import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
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
import org.chagolchana.chagol.test.TestSocksModule;
import org.chagolchana.chagol.transport.TransportModule;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.feed.FeedManager;
import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.client.BriarClientModule;
import org.chagolchana.noconnect.test.TestDnsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		TestDatabaseModule.class,
		TestPluginConfigModule.class,
		TestSeedProviderModule.class,
		TestSocksModule.class,
		TestDnsModule.class,
		LifecycleModule.class,
		BriarClientModule.class,
		ClientModule.class,
		ContactModule.class,
		CryptoModule.class,
		BlogModule.class,
		FeedModule.class,
		DataModule.class,
		DatabaseModule.class,
		EventModule.class,
		IdentityModule.class,
		SyncModule.class,
		SystemModule.class,
		TransportModule.class
})
interface FeedManagerIntegrationTestComponent {

	void inject(FeedManagerIntegrationTest testCase);

	void inject(FeedModule.EagerSingletons init);

	void inject(BlogModule.EagerSingletons init);

	void inject(ContactModule.EagerSingletons init);

	void inject(CryptoModule.EagerSingletons init);

	void inject(IdentityModule.EagerSingletons init);

	void inject(LifecycleModule.EagerSingletons init);

	void inject(SyncModule.EagerSingletons init);

	void inject(SystemModule.EagerSingletons init);

	void inject(TransportModule.EagerSingletons init);

	LifecycleManager getLifecycleManager();

	FeedManager getFeedManager();

	BlogManager getBlogManager();

}
