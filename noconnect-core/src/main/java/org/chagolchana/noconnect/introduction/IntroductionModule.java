package org.chagolchana.noconnect.introduction;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.client.MessageQueueManager;
import org.chagolchana.noconnect.api.introduction.IntroductionManager;
import org.chagolchana.noconnect.api.messaging.ConversationManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.chagolchana.noconnect.api.introduction.IntroductionManager.CLIENT_ID;

@Module
public class IntroductionModule {

	public static class EagerSingletons {
		@Inject
		IntroductionManager introductionManager;
		@Inject
		IntroductionValidator introductionValidator;
	}

	@Provides
	@Singleton
	IntroductionValidator provideValidator(
			MessageQueueManager messageQueueManager,
			MetadataEncoder metadataEncoder, ClientHelper clientHelper,
			Clock clock) {

		IntroductionValidator introductionValidator = new IntroductionValidator(
				clientHelper, metadataEncoder, clock);
		messageQueueManager.registerMessageValidator(CLIENT_ID,
				introductionValidator);

		return introductionValidator;
	}

	@Provides
	@Singleton
	IntroductionManager provideIntroductionManager(
			LifecycleManager lifecycleManager, ContactManager contactManager,
			MessageQueueManager messageQueueManager,
			ConversationManager conversationManager,
			IntroductionManagerImpl introductionManager) {

		lifecycleManager.registerClient(introductionManager);
		contactManager.registerAddContactHook(introductionManager);
		contactManager.registerRemoveContactHook(introductionManager);
		messageQueueManager.registerIncomingMessageHook(CLIENT_ID,
				introductionManager);
		conversationManager.registerConversationClient(introductionManager);

		return introductionManager;
	}
}
