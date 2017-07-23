package org.chagolchana.chagol.keyagreement;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.keyagreement.KeyAgreementTask;
import org.chagolchana.chagol.api.keyagreement.KeyAgreementTaskFactory;
import org.chagolchana.chagol.api.keyagreement.PayloadEncoder;
import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.PluginManager;
import org.chagolchana.chagol.api.system.Clock;

import java.util.concurrent.Executor;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class KeyAgreementTaskFactoryImpl implements KeyAgreementTaskFactory {

	private final Clock clock;
	private final CryptoComponent crypto;
	private final EventBus eventBus;
	private final Executor ioExecutor;
	private final PayloadEncoder payloadEncoder;
	private final PluginManager pluginManager;

	@Inject
	KeyAgreementTaskFactoryImpl(Clock clock, CryptoComponent crypto,
			EventBus eventBus, @IoExecutor Executor ioExecutor,
			PayloadEncoder payloadEncoder, PluginManager pluginManager) {
		this.clock = clock;
		this.crypto = crypto;
		this.eventBus = eventBus;
		this.ioExecutor = ioExecutor;
		this.payloadEncoder = payloadEncoder;
		this.pluginManager = pluginManager;
	}

	@Override
	public KeyAgreementTask createTask() {
		return new KeyAgreementTaskImpl(clock, crypto, eventBus, payloadEncoder,
				pluginManager, ioExecutor);
	}
}
