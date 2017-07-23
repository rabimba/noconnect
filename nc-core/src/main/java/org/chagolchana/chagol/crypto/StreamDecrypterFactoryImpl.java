package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.crypto.StreamDecrypter;
import org.chagolchana.chagol.api.crypto.StreamDecrypterFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;

import java.io.InputStream;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;

@Immutable
@NotNullByDefault
class StreamDecrypterFactoryImpl implements StreamDecrypterFactory {

	private final Provider<AuthenticatedCipher> cipherProvider;

	@Inject
	StreamDecrypterFactoryImpl(Provider<AuthenticatedCipher> cipherProvider) {
		this.cipherProvider = cipherProvider;
	}

	@Override
	public StreamDecrypter createStreamDecrypter(InputStream in,
			StreamContext ctx) {
		AuthenticatedCipher cipher = cipherProvider.get();
		return new StreamDecrypterImpl(in, cipher, ctx.getStreamNumber(),
				ctx.getHeaderKey());
	}

	@Override
	public StreamDecrypter createInvitationStreamDecrypter(InputStream in,
			SecretKey headerKey) {
		return new StreamDecrypterImpl(in, cipherProvider.get(), 0, headerKey);
	}
}
