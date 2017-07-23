package org.chagolchana.chagol.api.crypto;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.transport.StreamContext;

import java.io.OutputStream;

@NotNullByDefault
public interface StreamEncrypterFactory {

	/**
	 * Creates a {@link StreamEncrypter} for encrypting a transport stream.
	 */
	StreamEncrypter createStreamEncrypter(OutputStream out, StreamContext ctx);

	/**
	 * Creates a {@link StreamEncrypter} for encrypting an invitation stream.
	 */
	StreamEncrypter createInvitationStreamEncrypter(OutputStream out,
			SecretKey headerKey);
}
