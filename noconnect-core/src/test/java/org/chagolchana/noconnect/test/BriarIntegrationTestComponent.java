package org.chagolchana.noconnect.test;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.properties.TransportPropertyManager;
import org.chagolchana.chagol.api.sync.SyncSessionFactory;
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
import org.chagolchana.noconnect.api.blog.BlogFactory;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.BlogSharingManager;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.forum.ForumManager;
import org.chagolchana.noconnect.api.forum.ForumSharingManager;
import org.chagolchana.noconnect.api.introduction.IntroductionManager;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupManager;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationManager;
import org.chagolchana.noconnect.blog.BlogModule;
import org.chagolchana.noconnect.client.BriarClientModule;
import org.chagolchana.noconnect.forum.ForumModule;
import org.chagolchana.noconnect.introduction.IntroductionModule;
import org.chagolchana.noconnect.messaging.MessagingModule;
import org.chagolchana.noconnect.privategroup.PrivateGroupModule;
import org.chagolchana.noconnect.privategroup.invitation.GroupInvitationModule;
import org.chagolchana.noconnect.sharing.SharingModule;

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
public interface BriarIntegrationTestComponent {

	void inject(BriarIntegrationTest<BriarIntegrationTestComponent> init);

	void inject(BlogModule.EagerSingletons init);

	void inject(ContactModule.EagerSingletons init);

	void inject(CryptoModule.EagerSingletons init);

	void inject(ForumModule.EagerSingletons init);

	void inject(GroupInvitationModule.EagerSingletons init);

	void inject(IdentityModule.EagerSingletons init);

	void inject(IntroductionModule.EagerSingletons init);

	void inject(LifecycleModule.EagerSingletons init);

	void inject(MessagingModule.EagerSingletons init);

	void inject(PrivateGroupModule.EagerSingletons init);

	void inject(PropertiesModule.EagerSingletons init);

	void inject(SharingModule.EagerSingletons init);

	void inject(SyncModule.EagerSingletons init);

	void inject(SystemModule.EagerSingletons init);

	void inject(TransportModule.EagerSingletons init);

	LifecycleManager getLifecycleManager();

	EventBus getEventBus();

	IdentityManager getIdentityManager();

	ClientHelper getClientHelper();

	ContactManager getContactManager();

	SyncSessionFactory getSyncSessionFactory();

	DatabaseComponent getDatabaseComponent();

	BlogManager getBlogManager();

	BlogSharingManager getBlogSharingManager();

	ForumSharingManager getForumSharingManager();

	ForumManager getForumManager();

	GroupInvitationManager getGroupInvitationManager();

	IntroductionManager getIntroductionManager();

	MessageTracker getMessageTracker();

	PrivateGroupManager getPrivateGroupManager();

	TransportPropertyManager getTransportPropertyManager();

	AuthorFactory getAuthorFactory();

	BlogFactory getBlogFactory();
}
