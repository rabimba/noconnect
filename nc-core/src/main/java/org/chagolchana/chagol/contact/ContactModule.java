package org.chagolchana.chagol.contact;

import org.chagolchana.chagol.api.contact.ContactExchangeTask;
import org.chagolchana.chagol.api.contact.ContactManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactModule {

	public static class EagerSingletons {
		@Inject
		ContactManager contactManager;
	}

	@Provides
	@Singleton
	ContactManager getContactManager(ContactManagerImpl contactManager) {
		return contactManager;
	}

	@Provides
	ContactExchangeTask provideContactExchangeTask(
			ContactExchangeTaskImpl contactExchangeTask) {
		return contactExchangeTask;
	}
}
