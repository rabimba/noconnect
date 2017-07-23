package org.chagolchana.chagol.reporting;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.reporting.DevConfig;
import org.chagolchana.chagol.api.reporting.DevReporter;

import javax.net.SocketFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ReportingModule {

	@Provides
	DevReporter provideDevReporter(CryptoComponent crypto,
			DevConfig devConfig, SocketFactory torSocketFactory) {
		return new DevReporterImpl(crypto, devConfig, torSocketFactory);
	}
}
