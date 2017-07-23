package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.client.ClientModule;
import org.chagolchana.chagol.contact.ContactModule;
import org.chagolchana.chagol.crypto.CryptoModule;
import org.chagolchana.chagol.data.DataModule;
import org.chagolchana.chagol.db.DatabaseModule;
import org.chagolchana.chagol.event.EventModule;
import org.chagolchana.chagol.identity.IdentityModule;
import org.chagolchana.chagol.lifecycle.LifecycleModule;
import org.chagolchana.chagol.properties.PropertiesModule;
import org.chagolchana.chagol.sync.SyncModule;
import org.chagolchana.chagol.system.SystemModule;
import org.chagolchana.chagol.test.TestDatabaseModule;
import org.chagolchana.chagol.test.TestPluginConfigModule;
import org.chagolchana.chagol.test.TestSeedProviderModule;
import org.chagolchana.chagol.transport.TransportModule;
import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.client.BriarClientModule;
import org.chagolchana.noconnect.forum.ForumModule;
import org.chagolchana.noconnect.messaging.MessagingModule;
import org.chagolchana.noconnect.privategroup.PrivateGroupModule;
import org.chagolchana.noconnect.privategroup.invitation.GroupInvitationModule;
import org.chagolchana.noconnect.sharing.SharingModule;
import org.chagolchana.noconnect.test.BriarIntegrationTestComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		TestDatabaseModule.class,
		TestPluginConfigModule.class,
		TestSeedProviderModule.class,
		BlogModule.class,
		BriarClientModule.class,
		ClientModule.class,
		ContactModule.class,
		CryptoModule.class,
		DataModule.class,
		DatabaseModule.class,
		EventModule.class,
		ForumModule.class,
		GroupInvitationModule.class,
		IdentityModule.class,
		IntroductionModule.class,
		LifecycleModule.class,
		MessagingModule.class,
		PrivateGroupModule.class,
		PropertiesModule.class,
		SharingModule.class,
		SyncModule.class,
		SystemModule.class,
		TransportModule.class
})
interface IntroductionIntegrationTestComponent
		extends BriarIntegrationTestComponent {

	void inject(IntroductionIntegrationTest init);

	MessageSender getMessageSender();

}
