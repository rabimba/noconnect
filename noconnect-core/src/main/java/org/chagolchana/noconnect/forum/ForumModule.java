package org.chagolchana.noconnect.forum;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.forum.ForumFactory;
import org.chagolchana.noconnect.api.forum.ForumManager;
import org.chagolchana.noconnect.api.forum.ForumPostFactory;

import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.chagolchana.noconnect.api.forum.ForumManager.CLIENT_ID;

@Module
public class ForumModule {

	public static class EagerSingletons {
		@Inject
		ForumManager forumManager;
		@Inject
		ForumPostValidator forumPostValidator;
	}

	@Provides
	@Singleton
	ForumManager provideForumManager(ForumManagerImpl forumManager,
			ValidationManager validationManager) {

		validationManager.registerIncomingMessageHook(CLIENT_ID,
				forumManager);

		return forumManager;
	}

	@Provides
	ForumPostFactory provideForumPostFactory(
			ForumPostFactoryImpl forumPostFactory) {
		return forumPostFactory;
	}

	@Provides
	ForumFactory provideForumFactory(GroupFactory groupFactory,
			ClientHelper clientHelper, SecureRandom random) {
		return new ForumFactoryImpl(groupFactory, clientHelper, random);
	}

	@Provides
	@Singleton
	ForumPostValidator provideForumPostValidator(
			ValidationManager validationManager, AuthorFactory authorFactory,
			ClientHelper clientHelper, MetadataEncoder metadataEncoder,
			Clock clock) {
		ForumPostValidator validator = new ForumPostValidator(authorFactory,
				clientHelper, metadataEncoder, clock);
		validationManager.registerMessageValidator(CLIENT_ID, validator);
		return validator;
	}

}
