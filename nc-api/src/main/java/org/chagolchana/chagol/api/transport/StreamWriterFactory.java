package org.chagolchana.chagol.api.transport;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;

import java.io.OutputStream;

@NotNullByDefault
public interface StreamWriterFactory {

	/**
	 * Creates an {@link OutputStream OutputStream} for writing to a
	 * transport stream
	 */
	OutputStream createStreamWriter(OutputStream out, StreamContext ctx);

	/**
	 * Creates an {@link OutputStream OutputStream} for writing to an
	 * invitation stream.
	 */
	OutputStream createInvitationStreamWriter(OutputStream out,
			SecretKey headerKey);
}
