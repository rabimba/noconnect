package org.chagolchana.chagol.identity;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.data.ObjectReader;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.identity.IdentityManager;
import org.chagolchana.chagol.api.system.Clock;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdentityModule {

	public static class EagerSingletons {
		@Inject
		IdentityManager identityManager;
	}

	@Provides
	AuthorFactory provideAuthorFactory(CryptoComponent crypto,
			BdfWriterFactory bdfWriterFactory, Clock clock) {
		return new AuthorFactoryImpl(crypto, bdfWriterFactory, clock);
	}

	@Provides
	@Singleton
	IdentityManager provideIdentityModule(DatabaseComponent db) {
		return new IdentityManagerImpl(db);
	}

	@Provides
	ObjectReader<Author> provideAuthorReader(AuthorFactory authorFactory) {
		return new AuthorReader(authorFactory);
	}
}
