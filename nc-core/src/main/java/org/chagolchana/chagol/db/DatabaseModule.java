package org.chagolchana.chagol.db;

import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.db.DatabaseConfig;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.ShutdownManager;
import org.chagolchana.chagol.api.system.Clock;

import java.sql.Connection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

	@Provides
	@Singleton
	Database<Connection> provideDatabase(DatabaseConfig config, Clock clock) {
		return new H2Database(config, clock);
	}

	@Provides
	@Singleton
	DatabaseComponent provideDatabaseComponent(Database<Connection> db,
			EventBus eventBus, ShutdownManager shutdown) {
		return new DatabaseComponentImpl<Connection>(db, Connection.class,
				eventBus, shutdown);
	}
}
