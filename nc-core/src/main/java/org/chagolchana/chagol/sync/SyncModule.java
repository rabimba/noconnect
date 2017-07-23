package org.chagolchana.chagol.sync;

import org.chagolchana.chagol.PoliteExecutor;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.CryptoExecutor;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.chagol.api.sync.MessageFactory;
import org.chagolchana.chagol.api.sync.RecordReaderFactory;
import org.chagolchana.chagol.api.sync.RecordWriterFactory;
import org.chagolchana.chagol.api.sync.SyncSessionFactory;
import org.chagolchana.chagol.api.sync.ValidationManager;
import org.chagolchana.chagol.api.system.Clock;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SyncModule {

	public static class EagerSingletons {
		@Inject
		ValidationManager validationManager;
	}

	/**
	 * The maximum number of validation tasks to delegate to the crypto
	 * executor concurrently.
	 * <p>
	 * The number of available processors can change during the lifetime of the
	 * JVM, so this is just a reasonable guess.
	 */
	private static final int MAX_CONCURRENT_VALIDATION_TASKS =
			Math.max(1, Runtime.getRuntime().availableProcessors() - 1);

	@Provides
	GroupFactory provideGroupFactory(CryptoComponent crypto) {
		return new GroupFactoryImpl(crypto);
	}

	@Provides
	MessageFactory provideMessageFactory(CryptoComponent crypto) {
		return new MessageFactoryImpl(crypto);
	}

	@Provides
	RecordReaderFactory provideRecordReaderFactory(
			RecordReaderFactoryImpl recordReaderFactory) {
		return recordReaderFactory;
	}

	@Provides
	RecordWriterFactory provideRecordWriterFactory() {
		return new RecordWriterFactoryImpl();
	}

	@Provides
	@Singleton
	SyncSessionFactory provideSyncSessionFactory(DatabaseComponent db,
			@DatabaseExecutor Executor dbExecutor, EventBus eventBus,
			Clock clock, RecordReaderFactory recordReaderFactory,
			RecordWriterFactory recordWriterFactory) {
		return new SyncSessionFactoryImpl(db, dbExecutor, eventBus, clock,
				recordReaderFactory, recordWriterFactory);
	}

	@Provides
	@Singleton
	ValidationManager provideValidationManager(
			LifecycleManager lifecycleManager, EventBus eventBus,
			ValidationManagerImpl validationManager) {
		lifecycleManager.registerService(validationManager);
		eventBus.addListener(validationManager);
		return validationManager;
	}

	@Provides
	@Singleton
	@ValidationExecutor
	Executor provideValidationExecutor(
			@CryptoExecutor Executor cryptoExecutor) {
		return new PoliteExecutor("ValidationExecutor", cryptoExecutor,
				MAX_CONCURRENT_VALIDATION_TASKS);
	}
}
