package org.chagolchana.chagol.crypto;

import org.chagolchana.chagol.api.crypto.CryptoComponent;
import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.crypto.StreamEncrypter;
import org.chagolchana.chagol.api.crypto.StreamEncrypterFactory;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;

import java.io.OutputStream;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Provider;

import static org.chagolchana.chagol.api.transport.TransportConstants.STREAM_HEADER_IV_LENGTH;
import static org.chagolchana.chagol.api.transport.TransportConstants.TAG_LENGTH;

@Immutable
@NotNullByDefault
class StreamEncrypterFactoryImpl implements StreamEncrypterFactory {

	private final CryptoComponent crypto;
	private final Provider<AuthenticatedCipher> cipherProvider;

	@Inject
	StreamEncrypterFactoryImpl(CryptoComponent crypto,
			Provider<AuthenticatedCipher> cipherProvider) {
		this.crypto = crypto;
		this.cipherProvider = cipherProvider;
	}

	@Override
	public StreamEncrypter createStreamEncrypter(OutputStream out,
			StreamContext ctx) {
		AuthenticatedCipher cipher = cipherProvider.get();
		long streamNumber = ctx.getStreamNumber();
		byte[] tag = new byte[TAG_LENGTH];
		crypto.encodeTag(tag, ctx.getTagKey(), streamNumber);
		byte[] streamHeaderIv = new byte[STREAM_HEADER_IV_LENGTH];
		crypto.getSecureRandom().nextBytes(streamHeaderIv);
		SecretKey frameKey = crypto.generateSecretKey();
		return new StreamEncrypterImpl(out, cipher, streamNumber, tag,
				streamHeaderIv, ctx.getHeaderKey(), frameKey);
	}

	@Override
	public StreamEncrypter createInvitationStreamEncrypter(OutputStream out,
			SecretKey headerKey) {
		AuthenticatedCipher cipher = cipherProvider.get();
		byte[] streamHeaderIv = new byte[STREAM_HEADER_IV_LENGTH];
		crypto.getSecureRandom().nextBytes(streamHeaderIv);
		SecretKey frameKey = crypto.generateSecretKey();
		return new StreamEncrypterImpl(out, cipher, 0, null, streamHeaderIv,
				headerKey, frameKey);
	}
}
