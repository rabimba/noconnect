package org.chagolchana.chagol.transport;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.crypto.StreamEncrypterFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;
import org.chagolchana.chagol.api.transport.StreamWriterFactory;

import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;

@Immutable
@NotNullByDefault
class StreamWriterFactoryImpl implements StreamWriterFactory {

	private final StreamEncrypterFactory streamEncrypterFactory;

	@Inject
	StreamWriterFactoryImpl(StreamEncrypterFactory streamEncrypterFactory) {
		this.streamEncrypterFactory = streamEncrypterFactory;
	}

	@Override
	public OutputStream createStreamWriter(OutputStream out,
			StreamContext ctx) {
		return new StreamWriterImpl(
				streamEncrypterFactory.createStreamEncrypter(out, ctx));
	}

	@Override
	public OutputStream createInvitationStreamWriter(OutputStream out,
			SecretKey headerKey) {
		return new StreamWriterImpl(
				streamEncrypterFactory.createInvitationStreamEncrypter(out,
						headerKey));
	}
}