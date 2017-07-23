package org.chagolchana.chagol.plugin;

import android.app.Application;
import android.content.Context;

import org.chagolchana.chagol.api.event.EventBus;
import org.chagolchana.chagol.api.lifecycle.IoExecutor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.BackoffFactory;
import org.chagolchana.chagol.api.plugin.PluginConfig;
import org.chagolchana.chagol.api.plugin.duplex.DuplexPluginFactory;
import org.chagolchana.chagol.api.plugin.simplex.SimplexPluginFactory;
import org.chagolchana.chagol.api.reporting.DevReporter;
import org.chagolchana.chagol.api.system.AndroidExecutor;
import org.chagolchana.chagol.api.system.LocationUtils;
import org.chagolchana.chagol.plugin.droidtooth.DroidtoothPluginFactory;
import org.chagolchana.chagol.plugin.tcp.AndroidLanTcpPluginFactory;
import org.chagolchana.chagol.plugin.tor.TorPluginFactory;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executor;

import javax.net.SocketFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidPluginModule {

	@Provides
	PluginConfig providePluginConfig(@IoExecutor Executor ioExecutor,
			AndroidExecutor androidExecutor, SecureRandom random,
			SocketFactory torSocketFactory, BackoffFactory backoffFactory,
			Application app, LocationUtils locationUtils, DevReporter reporter,
			EventBus eventBus) {
		Context appContext = app.getApplicationContext();
		DuplexPluginFactory bluetooth = new DroidtoothPluginFactory(ioExecutor,
				androidExecutor, appContext, random, backoffFactory);
		DuplexPluginFactory tor = new TorPluginFactory(ioExecutor, appContext,
				locationUtils, reporter, eventBus, torSocketFactory,
				backoffFactory);
		DuplexPluginFactory lan = new AndroidLanTcpPluginFactory(ioExecutor,
				backoffFactory, appContext);
		final Collection<DuplexPluginFactory> duplex =
				Arrays.asList(bluetooth, tor, lan);
		@NotNullByDefault
		PluginConfig pluginConfig = new PluginConfig() {

			@Override
			public Collection<DuplexPluginFactory> getDuplexFactories() {
				return duplex;
			}

			@Override
			public Collection<SimplexPluginFactory> getSimplexFactories() {
				return Collections.emptyList();
			}
		};
		return pluginConfig;
	}
}
