package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.crypto.StreamDecrypterFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;
import org.chagolchana.chagol.api.transport.StreamReaderFactory;

import java.io.InputStream;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class StreamReaderFactoryImpl implements StreamReaderFactory {

	private final StreamDecrypterFactory streamDecrypterFactory;

	@Inject
	StreamReaderFactoryImpl(StreamDecrypterFactory streamDecrypterFactory) {
		this.streamDecrypterFactory = streamDecrypterFactory;
	}

	@Override
	public InputStream createStreamReader(InputStream in, StreamContext ctx) {
		return new StreamReaderImpl(
				streamDecrypterFactory.createStreamDecrypter(in, ctx));
	}

	@Override
	public InputStream createInvitationStreamReader(InputStream in,
			SecretKey headerKey) {
		return new StreamReaderImpl(
				streamDecrypterFactory.createInvitationStreamDecrypter(in,
						headerKey));
	}
}
