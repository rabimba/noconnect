package org.chagolchana.chagol.plugin.modem;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPlugin;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginCallback;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginFactory;
import org.chagolchana.chagol.api.reliability.ReliabilityLayerFactory;
import org.chagolchana.chagol.util.StringUtils;

import java.util.concurrent.Executor;

import javax.annotation.concurrent.Immutable;

@Immutable
@NotNullByDefault
public class ModemPluginFactory implements DuplexPluginFactory {

	private static final int MAX_LATENCY = 30 * 1000; // 30 seconds

	private final ModemFactory modemFactory;
	private final SerialPortList serialPortList;

	public ModemPluginFactory(Executor ioExecutor,
			ReliabilityLayerFactory reliabilityFactory) {
		modemFactory = new ModemFactoryImpl(ioExecutor, reliabilityFactory);
		serialPortList = new SerialPortListImpl();
	}

	@Override
	public TransportId getId() {
		return ModemPlugin.ID;
	}

	@Override
	public int getMaxLatency() {
		return MAX_LATENCY;
	}

	@Override
	public DuplexPlugin createPlugin(DuplexPluginCallback callback) {
		// This plugin is not enabled by default
		String enabled = callback.getSettings().get("enabled");
		if (StringUtils.isNullOrEmpty(enabled)) return null;
		return new ModemPlugin(modemFactory, serialPortList, callback,
				MAX_LATENCY);
	}
}
