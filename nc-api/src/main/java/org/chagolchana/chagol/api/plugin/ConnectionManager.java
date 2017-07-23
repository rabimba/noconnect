package org.chagolchana.chagol.api.plugin;

import org.chagolchana.chagol.api.contact.ContactId;
import org.chagolchana.chagol.api.plugin.duplex.DuplexTransportConnection;

public interface ConnectionManager {

	void manageIncomingConnection(TransportId t, TransportConnectionReader r);

	void manageIncomingConnection(TransportId t, DuplexTransportConnection d);

	void manageOutgoingConnection(ContactId c, TransportId t,
			TransportConnectionWriter w);

	void manageOutgoingConnection(ContactId c, TransportId t,
			DuplexTransportConnection d);
}
