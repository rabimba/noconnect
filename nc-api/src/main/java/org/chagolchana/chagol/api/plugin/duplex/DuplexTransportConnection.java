package org.chagolchana.chagol.api.plugin.duplex;

import org.chagolchana.chagol.api.nullsafety.NotNullByDefault;
import org.chagolchana.chagol.api.plugin.TransportConnectionReader;
import org.chagolchana.chagol.api.plugin.TransportConnectionWriter;

/**
 * An interface for reading and writing data over a duplex transport. The
 * connection is not responsible for encrypting/decrypting or authenticating
 * the data.
 */
@NotNullByDefault
public interface DuplexTransportConnection {

	/**
	 * Returns a {@link TransportConnectionReader TransportConnectionReader}
	 * for reading from the connection.
	 */
	TransportConnectionReader getReader();

	/**
	 * Returns a {@link TransportConnectionWriter TransportConnectionWriter}
	 * for writing to the connection.
	 */
	TransportConnectionWriter getWriter();
}
