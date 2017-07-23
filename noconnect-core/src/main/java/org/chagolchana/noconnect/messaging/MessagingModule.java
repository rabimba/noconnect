package org.chagolchana.noconnect.messaging;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.messaging.ConversationManager;
import org.chagolchana.noconnect.api.messaging.MessagingManager;
import org.chagolchana.noconnect.api.messaging.PrivateMessageFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.chagolchana.noconnect.messaging.MessagingManagerImpl.CLIENT_ID;

@Module
public class MessagingModule {

	public static class EagerSingletons {
		@Inject
		MessagingManager messagingManager;
		@Inject
		ConversationManager conversationManager;
		@Inject
		PrivateMessageValidator privateMessageValidator;
	}

	@Provides
	PrivateMessageFactory providePrivateMessageFactory(
			ClientHelper clientHelper) {
		return new PrivateMessageFactoryImpl(clientHelper);
	}

	@Provides
	@Singleton
	PrivateMessageValidator getValidator(ValidationManager validationManager,
			ClientHelper clientHelper, MetadataEncoder metadataEncoder,
			Clock clock) {
		PrivateMessageValidator validator = new PrivateMessageValidator(
				clientHelper, metadataEncoder, clock);
		validationManager.registerMessageValidator(CLIENT_ID, validator);
		return validator;
	}

	@Provides
	@Singleton
	MessagingManager getMessagingManager(LifecycleManager lifecycleManager,
			ContactManager contactManager, ValidationManager validationManager,
			ConversationManager conversationManager,
			MessagingManagerImpl messagingManager) {
		lifecycleManager.registerClient(messagingManager);
		contactManager.registerAddContactHook(messagingManager);
		contactManager.registerRemoveContactHook(messagingManager);
		validationManager
				.registerIncomingMessageHook(CLIENT_ID, messagingManager);
		conversationManager.registerConversationClient(messagingManager);
		return messagingManager;
	}

	@Provides
	@Singleton
	ConversationManager getConversationManager(
			ConversationManagerImpl conversationManager) {
		return conversationManager;
	}

}
