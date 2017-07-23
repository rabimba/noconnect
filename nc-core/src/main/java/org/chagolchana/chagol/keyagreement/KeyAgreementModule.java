package org.chagolchana.chagol.keyagreement;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfReaderFactory;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.keyagreement.KeyAgreementTaskFactory;
import org.chagolchana.chagol.api.keyagreement.PayloadEncoder;
import org.chagolchana.chagol.api.keyagreement.PayloadParser;
import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.plugin.PluginManager;
import org.chagolchana.chagol.api.system.Clock;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class KeyAgreementModule {

	@Provides
	@Singleton
	KeyAgreementTaskFactory provideKeyAgreementTaskFactory(Clock clock,
			CryptoComponent crypto, EventBus eventBus,
			@IoExecutor Executor ioExecutor, PayloadEncoder payloadEncoder,
			PluginManager pluginManager) {
		return new KeyAgreementTaskFactoryImpl(clock, crypto, eventBus,
				ioExecutor, payloadEncoder, pluginManager);
	}

	@Provides
	PayloadEncoder providePayloadEncoder(BdfWriterFactory bdfWriterFactory) {
		return new PayloadEncoderImpl(bdfWriterFactory);
	}

	@Provides
	PayloadParser providePayloadParser(BdfReaderFactory bdfReaderFactory) {
		return new PayloadParserImpl(bdfReaderFactory);
	}
}
