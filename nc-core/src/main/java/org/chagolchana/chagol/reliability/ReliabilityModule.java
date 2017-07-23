package org.chagolchana.chagol.reliability;

import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.reliability.ReliabilityLayerFactory;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class ReliabilityModule {

	@Provides
	ReliabilityLayerFactory provideReliabilityFactoryByExector(
			@IoExecutor Executor ioExecutor) {
		return new ReliabilityLayerFactoryImpl(ioExecutor);
	}

	@Provides
	ReliabilityLayerFactory provideReliabilityFactory(
			ReliabilityLayerFactoryImpl reliabilityLayerFactory) {
		return reliabilityLayerFactory;
	}

}
