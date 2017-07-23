package org.chagolchana.noconnect.android;

import android.app.Application;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.PublicKey;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.db.DatabaseConfig;
import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.LifecycleManager;
import org.chagolchana.chagol.api.nullsafety.MethodsNotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.nullsafety.ParametersNotNullByDefault;
import org.chagolchana.chagol.api.reporting.DevConfig;
import org.chagolchana.chagol.api.ui.UiCallback;
import org.chagolchana.chagol.util.StringUtils;
import org.chagolchana.noconnect.api.android.AndroidNotificationManager;
import org.chagolchana.noconnect.api.android.ReferenceManager;
import org.chagolchana.noconnect.api.android.ScreenFilterMonitor;

import java.io.File;
import java.security.GeneralSecurityException;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;
import static org.chagolchana.chagol.api.reporting.ReportingConstants.DEV_ONION_ADDRESS;
import static org.chagolchana.chagol.api.reporting.ReportingConstants.DEV_PUBLIC_KEY_HEX;

@Module
public class AppModule {

	static class EagerSingletons {
		@Inject
		AndroidNotificationManager androidNotificationManager;
	}

	private final Application application;
	private final UiCallback uiCallback;

	public AppModule(Application application) {
		this.application = application;
		uiCallback = new UiCallback() {

			@Override
			public int showChoice(String[] options, String... message) {
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean showConfirmationMessage(String... message) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void showMessage(String... message) {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Provides
	@Singleton
	Application providesApplication() {
		return application;
	}

	@Provides
	UiCallback provideUICallback() {
		return uiCallback;
	}

	@Provides
	@Singleton
	DatabaseConfig provideDatabaseConfig(Application app) {
		final File dir = app.getApplicationContext().getDir("db", MODE_PRIVATE);
		@MethodsNotNullByDefault
		@ParametersNotNullByDefault
		DatabaseConfig databaseConfig = new DatabaseConfig() {

			private volatile SecretKey key;
			private volatile String nickname;

			@Override
			public boolean databaseExists() {
				if (!dir.isDirectory()) return false;
				File[] files = dir.listFiles();
				return files != null && files.length > 0;
			}

			@Override
			public File getDatabaseDirectory() {
				return dir;
			}

			@Override
			public void setEncryptionKey(SecretKey key) {
				this.key = key;
			}

			@Override
			public void setLocalAuthorName(String nickname) {
				this.nickname = nickname;
			}

			@Override
			@Nullable
			public String getLocalAuthorName() {
				return nickname;
			}

			@Override
			@Nullable
			public SecretKey getEncryptionKey() {
				return key;
			}

			@Override
			public long getMaxSize() {
				return Long.MAX_VALUE;
			}
		};
		return databaseConfig;
	}

	@Provides
	@Singleton
	DevConfig provideDevConfig(final CryptoComponent crypto) {
		@NotNullByDefault
		DevConfig devConfig = new DevConfig() {

			@Override
			public PublicKey getDevPublicKey() {
				try {
					return crypto.getMessageKeyParser().parsePublicKey(
							StringUtils.fromHexString(DEV_PUBLIC_KEY_HEX));
				} catch (GeneralSecurityException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public String getDevOnionAddress() {
				return DEV_ONION_ADDRESS;
			}
		};
		return devConfig;
	}

	@Provides
	@Singleton
	ReferenceManager provideReferenceManager() {
		return new ReferenceManagerImpl();
	}

	@Provides
	@Singleton
	AndroidNotificationManager provideAndroidNotificationManager(
			LifecycleManager lifecycleManager, EventBus eventBus,
			AndroidNotificationManagerImpl notificationManager) {
		lifecycleManager.registerService(notificationManager);
		eventBus.addListener(notificationManager);
		return notificationManager;
	}

	@Provides
	ScreenFilterMonitor provideScreenFilterMonitor(
			ScreenFilterMonitorImpl screenFilterMonitor) {
		return screenFilterMonitor;
	}
}
