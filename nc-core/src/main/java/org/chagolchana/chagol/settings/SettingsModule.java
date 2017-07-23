package org.chagolchana.chagol.settings;

import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.settings.SettingsManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {

	@Provides
	SettingsManager provideSettingsManager(DatabaseComponent db) {
		return new SettingsManagerImpl(db);
	}

}
