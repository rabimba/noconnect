package org.chagolchana.noconnect.android;

import org.chagolchana.chagol.BrambleAndroidModule;
import org.chagolchana.chagol.BrambleCoreEagerSingletons;
import org.chagolchana.chagol.BrambleCoreModule;
import org.chagolchana.chagol.api.contact.ContactExchangeTask;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.CryptoExecutor;
import org.chagolchana.chagol.api.crypto.PasswordStrengthEstimator;
import org.chagolchana.chagol.api.db.DatabaseConfig;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.invitation.InvitationTaskFactory;
import org.chagolchana.chagol.api.keyagreement.KeyAgreementTaskFactory;
import org.chagolchana.chagol.api.keyagreement.PayloadEncoder;
import org.chagolchana.chagol.api.keyagreement.PayloadParser;
import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.plugin.ConnectionRegistry;
import org.chagolchana.chagol.api.plugin.PluginManager;
import org.chagolchana.chagol.api.settings.SettingsManager;
import org.chagolchana.chagol.api.system.AndroidExecutor;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.BriarCoreEagerSingletons;
import org.chagolchana.noconnect.BriarCoreModule;
import org.chagolchana.noconnect.android.reporting.BriarReportSender;
import org.chagolchana.noconnect.api.android.AndroidNotificationManager;
import org.chagolchana.noconnect.api.android.ReferenceManager;
import org.chagolchana.noconnect.api.android.ScreenFilterMonitor;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.BlogPostFactory;
import org.chagolchana.noconnect.api.blog.BlogSharingManager;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.feed.FeedManager;
import org.chagolchana.noconnect.api.forum.ForumManager;
import org.chagolchana.noconnect.api.forum.ForumSharingManager;
import org.chagolchana.noconnect.api.introduction.IntroductionManager;
import org.chagolchana.noconnect.api.messaging.ConversationManager;
import org.chagolchana.noconnect.api.messaging.MessagingManager;
import org.chagolchana.noconnect.api.messaging.PrivateMessageFactory;
import org.chagolchana.noconnect.api.privategroup.GroupMessageFactory;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupFactory;
import org.chagolchana.noconnect.api.privategroup.PrivateGroupManager;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationFactory;
import org.chagolchana.noconnect.api.privategroup.invitation.GroupInvitationManager;
import org.thoughtcrime.securesms.components.emoji.EmojiProvider;
import org.thoughtcrime.securesms.components.emoji.RecentEmojiPageModel;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
		BrambleCoreModule.class,
		BriarCoreModule.class,
		BrambleAndroidModule.class,
		AppModule.class
})
public interface AndroidComponent
		extends BrambleCoreEagerSingletons, BriarCoreEagerSingletons {

	// Exposed objects
	@CryptoExecutor
	Executor cryptoExecutor();

	PasswordStrengthEstimator passwordStrengthIndicator();

	CryptoComponent cryptoComponent();

	DatabaseConfig databaseConfig();

	ReferenceManager referenceMangager();

	@DatabaseExecutor
	Executor databaseExecutor();

	MessageTracker messageTracker();

	LifecycleManager lifecycleManager();

	IdentityManager identityManager();

	PluginManager pluginManager();

	EventBus eventBus();

	InvitationTaskFactory invitationTaskFactory();

	AndroidNotificationManager androidNotificationManager();

	ScreenFilterMonitor screenFilterMonitor();

	ConnectionRegistry connectionRegistry();

	ContactManager contactManager();

	ConversationManager conversationManager();

	MessagingManager messagingManager();

	PrivateMessageFactory privateMessageFactory();

	PrivateGroupManager privateGroupManager();

	GroupInvitationFactory groupInvitationFactory();

	GroupInvitationManager groupInvitationManager();

	PrivateGroupFactory privateGroupFactory();

	GroupMessageFactory groupMessageFactory();

	ForumManager forumManager();

	ForumSharingManager forumSharingManager();

	BlogSharingManager blogSharingManager();

	BlogManager blogManager();

	BlogPostFactory blogPostFactory();

	SettingsManager settingsManager();

	ContactExchangeTask contactExchangeTask();

	KeyAgreementTaskFactory keyAgreementTaskFactory();

	PayloadEncoder payloadEncoder();

	PayloadParser payloadParser();

	IntroductionManager introductionManager();

	AndroidExecutor androidExecutor();

	FeedManager feedManager();

	Clock clock();

	@IoExecutor
	Executor ioExecutor();

	void inject(BriarService briarService);

	void inject(BriarReportSender briarReportSender);

	void inject(EmojiProvider emojiProvider);

	void inject(RecentEmojiPageModel recentEmojiPageModel);

	// Eager singleton load
	void inject(AppModule.EagerSingletons init);
}
