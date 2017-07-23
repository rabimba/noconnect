package org.chagolchana.chagol.client;

import org.chagolchana.chagol.api.client.ClientHelper;
import org.chagolchana.chagol.api.client.ContactGroupFactory;
import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfReaderFactory;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.data.MetadataEncoder;
import org.chagolchana.chagol.api.data.MetadataParser;
import org.chagolchana.chagol.api.db.DatabaseComponent;
import org.chagolchana.chagol.api.sync.GroupFactory;
import org.chagolchana.chagol.api.sync.MessageFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ClientModule {

	@Provides
	ClientHelper provideClientHelper(DatabaseComponent db,
			MessageFactory messageFactory, BdfReaderFactory bdfReaderFactory,
			BdfWriterFactory bdfWriterFactory, MetadataParser metadataParser,
			MetadataEncoder metadataEncoder, CryptoComponent cryptoComponent) {
		return new ClientHelperImpl(db, messageFactory, bdfReaderFactory,
				bdfWriterFactory, metadataParser, metadataEncoder,
				cryptoComponent);
	}

	@Provides
	ContactGroupFactory provideContactGroupFactory(GroupFactory groupFactory,
			ClientHelper clientHelper) {
		return new ContactGroupFactoryImpl(groupFactory, clientHelper);
	}

}
