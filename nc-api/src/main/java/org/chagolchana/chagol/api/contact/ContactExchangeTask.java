package org.chagolchana.chagol.api.contact;

import org.chagolchana.chagol.api.crypto.SecretKey;
import org.chagolchana.chagol.api.identity.LocalAuthor;
import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportId;
import org.chagolchana.chagol.api.plugin.duplex.DuplexTransportConnection;

/**
 * A task for conducting a contact information exchange with a remote peer.
 */
@NotNullByDefault
public interface ContactExchangeTask {

	/**
	 * Exchanges contact information with a remote peer.
	 */
	void startExchange(ContactExchangeListener listener,
			LocalAuthor localAuthor, SecretKey masterSecret,
			DuplexTransportConnection conn, TransportId transportId,
			boolean alice);
}
