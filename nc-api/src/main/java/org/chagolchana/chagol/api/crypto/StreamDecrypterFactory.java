package org.chagolchana.chagol.api.crypto;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;

import java.io.InputStream;

@NotNullByDefault
public interface StreamDecrypterFactory {

	/**
	 * Creates a {@link StreamDecrypter} for decrypting a transport stream.
	 */
	StreamDecrypter createStreamDecrypter(InputStream in, StreamContext ctx);

	/**
	 * Creates a {@link StreamDecrypter} for decrypting an invitation stream.
	 */
	StreamDecrypter createInvitationStreamDecrypter(InputStream in,
			SecretKey headerKey);
}
