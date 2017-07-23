package org.chagolchana.chagol;

import org.chagolchana.chagol.client.ClientModule;
import org.chagolchana.chagol.contact.ContactModule;
import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.data.DataModule;
import org.chagolchana.chagol.db.DatabaseExecutorModule;
import org.chagolchana.chagol.db.DatabaseModule;
import org.chagolchana.chagol.event.EventModule;
import org.chagolchana.chagol.identity.IdentityModule;
import org.chagolchana.chagol.invitation.InvitationModule;
import org.chagolchana.chagol.keyagreement.KeyAgreementModule;
import org.chagolchana.chagol.lifecycle.LifecycleModule;
import org.chagolchana.chagol.plugin.PluginModule;
import org.chagolchana.chagol.properties.PropertiesModule;
import org.chagolchana.chagol.reliability.ReliabilityModule;
import org.chagolchana.chagol.reporting.ReportingModule;
import org.chagolchana.chagol.settings.SettingsModule;
import org.chagolchana.chagol.socks.SocksModule;
import org.chagolchana.chagol.sync.SyncModule;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.transport.TransportModule;

import dagger.Module;

@Module(includes = {
		ClientModule.class,
		ContactModule.class,
		CryptoModule.class,
		DataModule.class,
		DatabaseModule.class,
		DatabaseExecutorModule.class,
		EventModule.class,
		IdentityModule.class,
		InvitationModule.class,
		KeyAgreementModule.class,
		LifecycleModule.class,
		PluginModule.class,
		PropertiesModule.class,
		ReliabilityModule.class,
		ReportingModule.class,
		SettingsModule.class,
		SocksModule.class,
		SyncModule.class,
		SystemModule.class,
		TransportModule.class
})
public class BrambleCoreModule {

	public static void initEagerSingletons(BrambleCoreEagerSingletons c) {
		c.inject(new ContactModule.EagerSingletons());
		c.inject(new CryptoModule.EagerSingletons());
		c.inject(new DatabaseExecutorModule.EagerSingletons());
		c.inject(new IdentityModule.EagerSingletons());
		c.inject(new LifecycleModule.EagerSingletons());
		c.inject(new PluginModule.EagerSingletons());
		c.inject(new SyncModule.EagerSingletons());
		c.inject(new SystemModule.EagerSingletons());
		c.inject(new TransportModule.EagerSingletons());
	}
}
