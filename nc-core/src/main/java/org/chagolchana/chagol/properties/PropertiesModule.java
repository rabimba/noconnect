package org.chagolchana.chagol.properties;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.contact.ContactManager;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.properties.TransportPropertyManager;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.chagol.api.system.Clock;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.chagolchana.chagol.api.properties.TransportPropertyManager.CLIENT_ID;

@Module
public class PropertiesModule {

	public static class EagerSingletons {
		@Inject
		TransportPropertyValidator transportPropertyValidator;
		@Inject
		TransportPropertyManager transportPropertyManager;
	}

	@Provides
	@Singleton
	TransportPropertyValidator getValidator(ValidationManager validationManager,
			ClientHelper clientHelper, MetadataEncoder metadataEncoder,
			Clock clock) {
		TransportPropertyValidator validator = new TransportPropertyValidator(
				clientHelper, metadataEncoder, clock);
		validationManager.registerMessageValidator(CLIENT_ID, validator);
		return validator;
	}

	@Provides
	@Singleton
	TransportPropertyManager getTransportPropertyManager(
			LifecycleManager lifecycleManager, ContactManager contactManager,
			TransportPropertyManagerImpl transportPropertyManager) {
		lifecycleManager.registerClient(transportPropertyManager);
		contactManager.registerAddContactHook(transportPropertyManager);
		contactManager.registerRemoveContactHook(transportPropertyManager);
		return transportPropertyManager;
	}
}
