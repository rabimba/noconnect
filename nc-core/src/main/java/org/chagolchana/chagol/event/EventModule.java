package org.chagolchana.chagol.event;

import org.chagolchana.chagol.api.event.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EventModule {

	@Provides
	@Singleton
	EventBus provideEventBus() {
		return new EventBusImpl();
	}
}
