package org.chagolchana.chagol.lifecycle;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.lifecycle.ShutdownManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class LifecycleModule {

	public static class EagerSingletons {
		@Inject
		@IoExecutor
		Executor executor;
	}

	private final ExecutorService ioExecutor;

	public LifecycleModule() {
		// The thread pool is unbounded, so use direct handoff
		BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
		// Discard tasks that are submitted during shutdown
		RejectedExecutionHandler policy =
				new ThreadPoolExecutor.DiscardPolicy();
		// Create threads as required and keep them in the pool for 60 seconds
		ioExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
				60, SECONDS, queue, policy);
	}

	@Provides
	@Singleton
	ShutdownManager provideShutdownManager() {
		return new ShutdownManagerImpl();
	}

	@Provides
	@Singleton
	LifecycleManager provideLifecycleManager(DatabaseComponent db,
			EventBus eventBus, CryptoComponent crypto,
			AuthorFactory authorFactory, IdentityManager identityManager) {
		return new LifecycleManagerImpl(db, eventBus, crypto, authorFactory,
				identityManager);
	}

	@Provides
	@Singleton
	@IoExecutor
	Executor provideIoExecutor(LifecycleManager lifecycleManager) {
		lifecycleManager.registerForShutdown(ioExecutor);
		return ioExecutor;
	}
}
