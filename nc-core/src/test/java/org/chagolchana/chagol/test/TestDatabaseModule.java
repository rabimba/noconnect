package org.chagolchana.chagol.test;

import org.chagolchana.chagol.api.db.DatabaseConfig;
import org.chagolchana.chagol.api.db.DatabaseExecutor;

import java.io.File;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestDatabaseModule {

	private final DatabaseConfig config;

	public TestDatabaseModule() {
		this(new File("."));
	}

	public TestDatabaseModule(File dir) {
		config = new TestDatabaseConfig(dir, Long.MAX_VALUE);
	}

	@Provides
	DatabaseConfig provideDatabaseConfig() {
		return config;
	}

	@Provides
	@Singleton
	@DatabaseExecutor
	Executor provideDatabaseExecutor() {
		return new ImmediateExecutor();
	}
}
