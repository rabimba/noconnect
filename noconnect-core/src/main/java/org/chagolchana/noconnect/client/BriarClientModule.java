package org.chagolchana.noconnect.client;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.noconnect.api.client.MessageQueueManager;
import org.chagolchana.noconnect.api.client.MessageTracker;
import org.chagolchana.noconnect.api.client.QueueMessageFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BriarClientModule {

	@Provides
	@Singleton
	MessageQueueManager provideMessageQueueManager(DatabaseComponent db,
			ClientHelper clientHelper, QueueMessageFactory queueMessageFactory,
			ValidationManager validationManager) {
		return new MessageQueueManagerImpl(db, clientHelper,
				queueMessageFactory, validationManager);
	}

	@Provides
	QueueMessageFactory provideQueueMessageFactory(CryptoComponent crypto) {
		return new QueueMessageFactoryImpl(crypto);
	}

	@Provides
	MessageTracker provideMessageTracker(MessageTrackerImpl messageTracker) {
		return messageTracker;
	}
}
