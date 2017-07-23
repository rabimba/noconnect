package org.chagolchana.chagol.plugin;

import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.lifecycle.ShutdownManager;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.BackoffFactory;
import org.chagolchana.chagol.api.plugin.PluginConfig;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginFactory;
import org.chagolchana.chagol.api.plugin.simplex.SimplexPluginFactory;
import org.chagolchana.chagol.api.reliability.ReliabilityLayerFactory;
import org.chagolchana.chagol.plugin.bluetooth.BluetoothPluginFactory;
import org.chagolchana.chagol.plugin.file.RemovableDrivePluginFactory;
import org.chagolchana.chagol.plugin.modem.ModemPluginFactory;
import org.chagolchana.chagol.plugin.tcp.LanTcpPluginFactory;
import org.chagolchana.chagol.plugin.tcp.WanTcpPluginFactory;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class DesktopPluginModule extends PluginModule {

	@Provides
	PluginConfig getPluginConfig(@IoExecutor Executor ioExecutor,
			SecureRandom random, BackoffFactory backoffFactory,
			ReliabilityLayerFactory reliabilityFactory,
			ShutdownManager shutdownManager) {
		DuplexPluginFactory bluetooth = new BluetoothPluginFactory(ioExecutor,
				random, backoffFactory);
		DuplexPluginFactory modem = new ModemPluginFactory(ioExecutor,
				reliabilityFactory);
		DuplexPluginFactory lan = new LanTcpPluginFactory(ioExecutor,
				backoffFactory);
		DuplexPluginFactory wan = new WanTcpPluginFactory(ioExecutor,
				backoffFactory, shutdownManager);
		SimplexPluginFactory removable =
				new RemovableDrivePluginFactory(ioExecutor);
		final Collection<SimplexPluginFactory> simplex =
				Collections.singletonList(removable);
		final Collection<DuplexPluginFactory> duplex =
				Arrays.asList(bluetooth, modem, lan, wan);
		@NotNullByDefault
		PluginConfig pluginConfig = new PluginConfig() {

			@Override
			public Collection<DuplexPluginFactory> getDuplexFactories() {
				return duplex;
			}

			@Override
			public Collection<SimplexPluginFactory> getSimplexFactories() {
				return simplex;
			}
		};
		return pluginConfig;
	}
}
