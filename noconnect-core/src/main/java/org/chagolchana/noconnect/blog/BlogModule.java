package org.chagolchana.noconnect.blog;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.chagol.api.sync.MessageFactory;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.noconnect.api.blog.BlogFactory;
import org.chagolchana.noconnect.api.blog.BlogManager;
import org.chagolchana.noconnect.api.blog.BlogPostFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.chagolchana.noconnect.blog.BlogManagerImpl.CLIENT_ID;

@Module
public class BlogModule {

	public static class EagerSingletons {
		@Inject
		BlogPostValidator blogPostValidator;
		@Inject
		BlogManager blogManager;
	}

	@Provides
	@Singleton
	BlogManager provideBlogManager(BlogManagerImpl blogManager,
			LifecycleManager lifecycleManager, ContactManager contactManager,
			ValidationManager validationManager) {

		lifecycleManager.registerClient(blogManager);
		contactManager.registerAddContactHook(blogManager);
		contactManager.registerRemoveContactHook(blogManager);
		validationManager.registerIncomingMessageHook(CLIENT_ID, blogManager);
		return blogManager;
	}

	@Provides
	BlogPostFactory provideBlogPostFactory(ClientHelper clientHelper,
			Clock clock) {
		return new BlogPostFactoryImpl(clientHelper, clock);
	}

	@Provides
	BlogFactory provideBlogFactory(GroupFactory groupFactory,
			AuthorFactory authorFactory, ClientHelper clientHelper) {
		return new BlogFactoryImpl(groupFactory, authorFactory, clientHelper);
	}

	@Provides
	@Singleton
	BlogPostValidator provideBlogPostValidator(
			ValidationManager validationManager, GroupFactory groupFactory,
			MessageFactory messageFactory, BlogFactory blogFactory,
			ClientHelper clientHelper, MetadataEncoder metadataEncoder,
			Clock clock) {

		BlogPostValidator validator = new BlogPostValidator(groupFactory,
				messageFactory, blogFactory, clientHelper, metadataEncoder,
				clock);
		validationManager.registerMessageValidator(CLIENT_ID, validator);

		return validator;
	}

}
