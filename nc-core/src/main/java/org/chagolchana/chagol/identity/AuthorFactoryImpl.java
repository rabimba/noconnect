package org.chagolchana.chagol.identity;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.data.BdfWriter;
import org.chagolchana.chagol.api.data.BdfWriterFactory;
import org.chagolchana.chagol.api.identity.Author;
import org.chagolchana.chagol.api.identity.AuthorFactory;
import org.chagolchana.chagol.api.identity.AuthorId;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.system.Clock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class AuthorFactoryImpl implements AuthorFactory {

	private final CryptoComponent crypto;
	private final BdfWriterFactory bdfWriterFactory;
	private final Clock clock;

	@Inject
	AuthorFactoryImpl(CryptoComponent crypto, BdfWriterFactory bdfWriterFactory,
			Clock clock) {
		this.crypto = crypto;
		this.bdfWriterFactory = bdfWriterFactory;
		this.clock = clock;
	}

	@Override
	public Author createAuthor(String name, byte[] publicKey) {
		return new Author(getId(name, publicKey), name, publicKey);
	}

	@Override
	public LocalAuthor createLocalAuthor(String name, byte[] publicKey,
			byte[] privateKey) {
		return new LocalAuthor(getId(name, publicKey), name, publicKey,
				privateKey, clock.currentTimeMillis());
	}

	private AuthorId getId(String name, byte[] publicKey) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BdfWriter w = bdfWriterFactory.createWriter(out);
		try {
			w.writeListStart();
			w.writeString(name);
			w.writeRaw(publicKey);
			w.writeListEnd();
		} catch (IOException e) {
			// Shouldn't happen with ByteArrayOutputStream
			throw new RuntimeException(e);
		}
		return new AuthorId(crypto.hash(AuthorId.LABEL, out.toByteArray()));
	}
}
