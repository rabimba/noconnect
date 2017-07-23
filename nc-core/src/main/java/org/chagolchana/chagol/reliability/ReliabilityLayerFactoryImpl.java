package org.chagolchana.chagol.reliability;

import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.reliability.ReliabilityLayer;
import org.chagolchana.chagol.api.reliability.ReliabilityLayerFactory;
import org.chagolchana.chagol.api.reliability.WriteHandler;
import org.chagolchana.chagol.api.system.Clock;
import org.chagolchana.chagol.system.SystemClock;

import java.util.concurrent.Executor;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class ReliabilityLayerFactoryImpl implements ReliabilityLayerFactory {

	private final Executor ioExecutor;
	private final Clock clock;

	@Inject
	ReliabilityLayerFactoryImpl(@IoExecutor Executor ioExecutor) {
		this.ioExecutor = ioExecutor;
		clock = new SystemClock();
	}

	@Override
	public ReliabilityLayer createReliabilityLayer(WriteHandler writeHandler) {
		return new ReliabilityLayerImpl(ioExecutor, clock, writeHandler);
	}
}
