package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DatabaseExecutor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.api.system.Scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class TransportKeyManagerFactoryImpl implements
		TransportKeyManagerFactory {

	private final DatabaseComponent db;
	private final CryptoComponent crypto;
	private final Executor dbExecutor;
	private final ScheduledExecutorService scheduler;
	private final Clock clock;

	@Inject
	TransportKeyManagerFactoryImpl(DatabaseComponent db, CryptoComponent crypto,
			@DatabaseExecutor Executor dbExecutor,
			@Scheduler ScheduledExecutorService scheduler, Clock clock) {
		this.db = db;
		this.crypto = crypto;
		this.dbExecutor = dbExecutor;
		this.scheduler = scheduler;
		this.clock = clock;
	}

	@Override
	public TransportKeyManager createTransportKeyManager(
			TransportId transportId, long maxLatency) {
		return new TransportKeyManagerImpl(db, crypto, dbExecutor, scheduler,
				clock, transportId, maxLatency);
	}

}
