package org.chagolchana.chagol.plugin.tcp;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.Backoff;
import org.chagolchana.chagol.api.plugin.BackoffFactory;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPlugin;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginCallback;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginFactory;

import java.util.concurrent.Executor;

import javax.annotation.concurrent.Immutable;

import static org.chagolchana.chagol.api.plugin.LanTcpConstants.ID;

@Immutable
@NotNullByDefault
public class LanTcpPluginFactory implements DuplexPluginFactory {

	private static final int MAX_LATENCY = 30 * 1000; // 30 seconds
	private static final int MAX_IDLE_TIME = 30 * 1000; // 30 seconds
	private static final int MIN_POLLING_INTERVAL = 60 * 1000; // 1 minute
	private static final int MAX_POLLING_INTERVAL = 10 * 60 * 1000; // 10 mins
	private static final double BACKOFF_BASE = 1.2;

	private final Executor ioExecutor;
	private final BackoffFactory backoffFactory;

	public LanTcpPluginFactory(Executor ioExecutor,
			BackoffFactory backoffFactory) {
		this.ioExecutor = ioExecutor;
		this.backoffFactory = backoffFactory;
	}

	@Override
	public TransportId getId() {
		return ID;
	}

	@Override
	public int getMaxLatency() {
		return MAX_LATENCY;
	}

	@Override
	public DuplexPlugin createPlugin(DuplexPluginCallback callback) {
		Backoff backoff = backoffFactory.createBackoff(MIN_POLLING_INTERVAL,
				MAX_POLLING_INTERVAL, BACKOFF_BASE);
		return new LanTcpPlugin(ioExecutor, backoff, callback, MAX_LATENCY,
				MAX_IDLE_TIME);
	}
}
